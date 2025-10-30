package smartLegalApi.application.processo.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Resposta com dados do documento")
public class DocumentoResponse {
    
    @Schema(description = "ID do documento", example = "1")
    private Long id;
    
    @Schema(description = "ID do processo", example = "1")
    private Long idProcesso;
    
    @Schema(description = "Nome do documento", example = "Contrato.pdf")
    private String nomeDocumento;
    
    @Schema(description = "Caminho do arquivo no servidor", example = "/uploads/docs/contrato_123.pdf")
    private String caminhoArquivo;
    
    @Schema(description = "Tipo do arquivo", example = "application/pdf")
    private String tipoArquivo;
    
    @Schema(description = "Tamanho em bytes", example = "524288")
    private Long tamanhoBytes;
    
    @Schema(description = "Hash SHA-256 do arquivo", example = "abc123def456...")
    private String hashArquivo;
    
    @Schema(description = "Data do upload", example = "2024-01-15T15:00:00")
    private LocalDateTime dataUpload;
}

