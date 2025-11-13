package smartLegalApi.domain.processo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import smartLegalApi.domain.shared.exception.BusinessRuleException;

import java.time.LocalDateTime;

/**
 * Entidade de Domínio: Andamento
 * Representa uma movimentação/atualização no processo
 */
@Getter
@Builder
@AllArgsConstructor
public class Andamento {
    
    private Long id;
    private Long idProcesso;
    private LocalDateTime dataAndamento;
    private String descricao;
    private String tipo;  // Ex: AUDIENCIA, SENTENCA, DESPACHO, etc
    private LocalDateTime dataCadastro;
    
    /**
     * Método estático para criar novo andamento
     */
    public static Andamento criar(Long idProcesso, LocalDateTime dataAndamento, String descricao, String tipo) {
        if (idProcesso == null) {
            throw new BusinessRuleException("ID do processo é obrigatório");
        }
        
        if (dataAndamento == null) {
            throw new BusinessRuleException("Data do andamento é obrigatória");
        }
        
        if (descricao == null || descricao.isBlank()) {
            throw new BusinessRuleException("Descrição do andamento é obrigatória");
        }
        
        return Andamento.builder()
            .idProcesso(idProcesso)
            .dataAndamento(dataAndamento)
            .descricao(descricao)
            .tipo(tipo)
            .dataCadastro(LocalDateTime.now())
            .build();
    }
    
    /**
     * Atualiza o andamento
     */
    public void atualizar(LocalDateTime dataAndamento, String descricao, String tipo) {
        if (dataAndamento != null) {
            this.dataAndamento = dataAndamento;
        }
        
        if (descricao != null && !descricao.isBlank()) {
            this.descricao = descricao;
        }
        
        if (tipo != null) {
            this.tipo = tipo;
        }
    }
}
