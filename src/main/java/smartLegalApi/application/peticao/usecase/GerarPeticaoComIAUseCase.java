package smartLegalApi.application.peticao.usecase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import smartLegalApi.domain.peticao.entity.Peticao;
import smartLegalApi.domain.peticao.exception.PeticaoNaoEncontradaException;
import smartLegalApi.domain.peticao.repository.PeticaoRepository;
import smartLegalApi.infrastructure.ai.service.GeminiService;
import smartLegalApi.infrastructure.ai.service.PeticaoPromptService;

/**
 * Caso de uso: Gerar Petição com IA
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class GerarPeticaoComIAUseCase {
    
    private final PeticaoRepository peticaoRepository;
    private final GeminiService geminiService;
    private final PeticaoPromptService promptService;
    
    @Transactional
    public Peticao executar(Long idPeticao, String contexto, String detalhesAdicionais) {
        log.info("Gerando petição com IA. ID: {}", idPeticao);
        
        Peticao peticao = peticaoRepository.findById(idPeticao)
            .orElseThrow(() -> new PeticaoNaoEncontradaException(idPeticao));
        
        // Constrói o prompt especializado
        String prompt = promptService.construirPrompt(
            peticao.getTipo(),
            contexto,
            detalhesAdicionais
        );
        
        // Inicia geração
        peticao.iniciarGeracao(prompt);
        peticaoRepository.update(peticao);
        
        try {
            // Chama a API do Gemini
            String conteudoGerado = geminiService.gerarConteudo(prompt);
            
            // Finaliza geração
            peticao.finalizarGeracao(conteudoGerado);
            
        } catch (Exception e) {
            log.error("Erro ao gerar petição com IA", e);
            // Volta para rascunho em caso de erro
            peticao.voltarParaRevisao();
            throw e;
        } finally {
            peticaoRepository.update(peticao);
        }
        
        log.info("Petição gerada com sucesso. ID: {}", idPeticao);
        return peticao;
    }
}

