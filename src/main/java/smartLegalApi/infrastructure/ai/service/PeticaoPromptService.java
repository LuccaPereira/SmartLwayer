package smartLegalApi.infrastructure.ai.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import smartLegalApi.domain.peticao.valueobject.TipoPeticao;

/**
 * Serviço para construção de prompts específicos para petições jurídicas
 */
@Service
@Slf4j
public class PeticaoPromptService {
    
    /**
     * Constrói um prompt detalhado para geração de petição
     */
    public String construirPrompt(TipoPeticao tipo, String contexto, String detalhesAdicionais) {
        log.debug("Construindo prompt para tipo: {}", tipo);
        
        StringBuilder prompt = new StringBuilder();
        
        // Instrução principal
        prompt.append("Você é um assistente jurídico especializado em direito brasileiro. ");
        prompt.append("Sua tarefa é redigir uma ").append(tipo.getDescricao()).append(" de forma profissional, ");
        prompt.append("seguindo as normas do Código de Processo Civil e as melhores práticas jurídicas.\n\n");
        
        // Instruções específicas por tipo
        prompt.append(getInstrucoesEspecificasTipo(tipo));
        prompt.append("\n\n");
        
        // Contexto do caso
        if (contexto != null && !contexto.isBlank()) {
            prompt.append("CONTEXTO DO CASO:\n");
            prompt.append(contexto);
            prompt.append("\n\n");
        }
        
        // Detalhes adicionais
        if (detalhesAdicionais != null && !detalhesAdicionais.isBlank()) {
            prompt.append("DETALHES ADICIONAIS:\n");
            prompt.append(detalhesAdicionais);
            prompt.append("\n\n");
        }
        
        // Instruções de formatação
        prompt.append("INSTRUÇÕES DE FORMATAÇÃO:\n");
        prompt.append("- Utilize linguagem técnica e formal\n");
        prompt.append("- Cite artigos de lei quando aplicável\n");
        prompt.append("- Estruture o texto em tópicos claros\n");
        prompt.append("- Inclua cabeçalho com identificação do tribunal\n");
        prompt.append("- Finalize com pedido e requerimentos\n");
        prompt.append("- Use parágrafos e espaçamento adequados\n\n");
        
        prompt.append("Agora, redija a petição completa:");
        
        return prompt.toString();
    }
    
    /**
     * Retorna instruções específicas para cada tipo de petição
     */
    private String getInstrucoesEspecificasTipo(TipoPeticao tipo) {
        return switch (tipo) {
            case INICIAL -> """
                INSTRUÇÕES ESPECÍFICAS PARA PETIÇÃO INICIAL:
                1. Inclua endereçamento ao juízo competente
                2. Qualificação completa das partes (autor e réu)
                3. Exposição dos fatos de forma clara e cronológica
                4. Fundamentação jurídica com base legal
                5. Dos pedidos: principal e subsidiário
                6. Valor da causa
                7. Pedido de citação do réu
                8. Protestos por provas
                9. Local, data e assinatura
                """;
                
            case CONTESTACAO -> """
                INSTRUÇÕES ESPECÍFICAS PARA CONTESTAÇÃO:
                1. Preliminares (se houver)
                2. Impugnação específica aos fatos alegados
                3. Apresentação da versão dos fatos
                4. Fundamentação jurídica da defesa
                5. Dos pedidos de improcedência
                6. Protestos por provas
                7. Juntada de documentos (se houver)
                """;
                
            case RECURSO -> """
                INSTRUÇÕES ESPECÍFICAS PARA RECURSO:
                1. Identificação da decisão recorrida
                2. Razões do inconformismo
                3. Dos fatos
                4. Do direito
                5. Dos pedidos de reforma ou anulação
                6. Demonstração dos requisitos de admissibilidade
                7. Pedido de efeito suspensivo (se necessário)
                """;
                
            case MANDADO_SEGURANCA -> """
                INSTRUÇÕES ESPECÍFICAS PARA MANDADO DE SEGURANÇA:
                1. Autoridade coatora
                2. Ato impugnado
                3. Direito líquido e certo
                4. Ilegalidade ou abuso de poder
                5. Inexistência de outro recurso
                6. Pedido de liminar (se cabível)
                7. Pedido de notificação da autoridade
                """;
                
            case HABEAS_CORPUS -> """
                INSTRUÇÕES ESPECÍFICAS PARA HABEAS CORPUS:
                1. Identificação do paciente
                2. Autoridade coatora
                3. Constrangimento ilegal
                4. Fundamentação urgente
                5. Pedido de liminar
                6. Pedido de informações à autoridade
                """;
                
            default -> """
                INSTRUÇÕES GERAIS:
                1. Estruture a petição de forma lógica
                2. Apresente os fatos relevantes
                3. Fundamente juridicamente
                4. Formule pedidos claros
                5. Inclua protestos e requerimentos
                """;
        };
    }
    
    /**
     * Constrói um prompt simplificado
     */
    public String construirPromptSimplificado(String requisicao) {
        return "Você é um assistente jurídico especializado em direito brasileiro. " +
               "Redija uma petição jurídica profissional com base na seguinte requisição:\n\n" +
               requisicao + "\n\n" +
               "Utilize linguagem técnica, cite legislação aplicável e estruture adequadamente.";
    }
}

