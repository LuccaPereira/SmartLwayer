package smartLegalApi.domain.processo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import smartLegalApi.domain.shared.exception.BusinessRuleException;

import java.time.LocalDateTime;

/**
 * Entidade de Domínio: Documento
 * Representa um documento anexado ao processo
 */
@Getter
@Builder
@AllArgsConstructor
public class Documento {
    
    private Long id;
    private Long idProcesso;
    private String nomeDocumento;
    private String caminhoArquivo;
    private String tipoArquivo;
    private Long tamanhoBytes;
    private String hashArquivo;  // SHA-256 para verificação de integridade
    private LocalDateTime dataUpload;
    
    /**
     * Método estático para criar novo documento
     */
    public static Documento criar(Long idProcesso, String nomeDocumento, String caminhoArquivo, 
                                  String tipoArquivo, Long tamanhoBytes, String hashArquivo) {
        if (idProcesso == null) {
            throw new BusinessRuleException("ID do processo é obrigatório");
        }
        
        if (nomeDocumento == null || nomeDocumento.isBlank()) {
            throw new BusinessRuleException("Nome do documento é obrigatório");
        }
        
        if (caminhoArquivo == null || caminhoArquivo.isBlank()) {
            throw new BusinessRuleException("Caminho do arquivo é obrigatório");
        }
        
        return Documento.builder()
            .idProcesso(idProcesso)
            .nomeDocumento(nomeDocumento)
            .caminhoArquivo(caminhoArquivo)
            .tipoArquivo(tipoArquivo)
            .tamanhoBytes(tamanhoBytes)
            .hashArquivo(hashArquivo)
            .dataUpload(LocalDateTime.now())
            .build();
    }
}
