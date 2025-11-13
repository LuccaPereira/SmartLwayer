package smartLegalApi.infrastructure.ai.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.stereotype.Service;
import smartLegalApi.domain.peticao.exception.ErroGeracaoIAException;
import smartLegalApi.infrastructure.ai.config.GeminiProperties;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Serviço de integração com Google Gemini API
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class GeminiService {
    
    private final GeminiProperties geminiProperties;
    private final ObjectMapper objectMapper = new ObjectMapper();
    
    private final OkHttpClient httpClient = new OkHttpClient.Builder()
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .build();
    
    /**
     * Gera conteúdo usando o Gemini
     */
    public String gerarConteudo(String prompt) {
        log.info("Gerando conteúdo com Gemini API");
        
        try {
            String url = String.format(
                "%s/%s:generateContent?key=%s",
                geminiProperties.getApiUrl(),
                geminiProperties.getModel(),
                geminiProperties.getApiKey()
            );
            
            // Monta o corpo da requisição
            Map<String, Object> requestBody = buildRequestBody(prompt);
            String jsonBody = objectMapper.writeValueAsString(requestBody);
            
            log.debug("Request URL: {}", url.replace(geminiProperties.getApiKey(), "***"));
            log.debug("Request Body: {}", jsonBody);
            
            // Cria a requisição HTTP
            Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(
                    jsonBody,
                    MediaType.parse("application/json; charset=utf-8")
                ))
                .addHeader("Content-Type", "application/json")
                .build();
            
            // Executa a requisição
            try (Response response = httpClient.newCall(request).execute()) {
                if (!response.isSuccessful()) {
                    String errorBody = response.body() != null ? response.body().string() : "Sem detalhes";
                    log.error("Erro na API Gemini. Status: {}, Body: {}", response.code(), errorBody);
                    throw new ErroGeracaoIAException("Erro ao chamar API Gemini: " + response.code());
                }
                
                String responseBody = response.body().string();
                log.debug("Response Body: {}", responseBody);
                
                // Extrai o texto gerado
                String generatedText = extractGeneratedText(responseBody);
                log.info("Conteúdo gerado com sucesso. Tamanho: {} caracteres", generatedText.length());
                
                return generatedText;
            }
            
        } catch (IOException e) {
            log.error("Erro de I/O ao chamar API Gemini", e);
            throw new ErroGeracaoIAException("Erro de comunicação com API Gemini", e);
        } catch (Exception e) {
            log.error("Erro inesperado ao gerar conteúdo", e);
            throw new ErroGeracaoIAException("Erro ao gerar conteúdo com IA: " + e.getMessage(), e);
        }
    }
    
    /**
     * Monta o corpo da requisição para a API Gemini
     */
    private Map<String, Object> buildRequestBody(String prompt) {
        Map<String, Object> requestBody = new HashMap<>();
        
        // Conteúdo
        Map<String, Object> part = new HashMap<>();
        part.put("text", prompt);
        
        Map<String, Object> content = new HashMap<>();
        content.put("parts", new Object[]{part});
        
        requestBody.put("contents", new Object[]{content});
        
        // Configurações de geração
        Map<String, Object> generationConfig = new HashMap<>();
        generationConfig.put("temperature", geminiProperties.getTemperature());
        generationConfig.put("maxOutputTokens", geminiProperties.getMaxTokens());
        
        requestBody.put("generationConfig", generationConfig);
        
        return requestBody;
    }
    
    /**
     * Extrai o texto gerado da resposta JSON
     */
    private String extractGeneratedText(String responseBody) {
        try {
            JsonNode root = objectMapper.readTree(responseBody);
            
            // Navega na estrutura JSON: candidates[0].content.parts[0].text
            JsonNode candidates = root.get("candidates");
            if (candidates == null || !candidates.isArray() || candidates.isEmpty()) {
                throw new ErroGeracaoIAException("Resposta da API não contém candidatos");
            }
            
            JsonNode firstCandidate = candidates.get(0);
            JsonNode content = firstCandidate.get("content");
            if (content == null) {
                throw new ErroGeracaoIAException("Resposta da API não contém conteúdo");
            }
            
            JsonNode parts = content.get("parts");
            if (parts == null || !parts.isArray() || parts.isEmpty()) {
                throw new ErroGeracaoIAException("Resposta da API não contém partes");
            }
            
            JsonNode firstPart = parts.get(0);
            JsonNode text = firstPart.get("text");
            if (text == null) {
                throw new ErroGeracaoIAException("Resposta da API não contém texto");
            }
            
            return text.asText();
            
        } catch (Exception e) {
            log.error("Erro ao extrair texto da resposta: {}", responseBody, e);
            throw new ErroGeracaoIAException("Erro ao processar resposta da API", e);
        }
    }
}

