package smartLegalApi.application.processo.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request para anexar documento")
public class AnexarDocumentoRequest {
    
    @NotBlank(message = "Nome do documento é obrigatório")
    @Schema(description = "Nome do documento", example = "Contrato.pdf")
    private String nomeDocumento;
    
    @NotBlank(message = "Caminho do arquivo é obrigatório")
    @Schema(description = "Caminho do arquivo no servidor", example = "/uploads/docs/contrato_123.pdf")
    private String caminhoArquivo;
    
    @Schema(description = "Tipo do arquivo", example = "application/pdf")
    private String tipoArquivo;
    
    @NotNull(message = "Tamanho do arquivo é obrigatório")
    @Schema(description = "Tamanho em bytes", example = "524288")
    private Long tamanhoBytes;
    
    @Schema(description = "Hash SHA-256 do arquivo", example = "abc123def456...")
    private String hashArquivo;
}

