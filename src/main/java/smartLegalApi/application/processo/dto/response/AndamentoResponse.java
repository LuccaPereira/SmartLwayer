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
@Schema(description = "Resposta com dados do andamento")
public class AndamentoResponse {
    
    @Schema(description = "ID do andamento", example = "1")
    private Long id;
    
    @Schema(description = "ID do processo", example = "1")
    private Long idProcesso;
    
    @Schema(description = "Data e hora do andamento", example = "2024-01-15T14:30:00")
    private LocalDateTime dataAndamento;
    
    @Schema(description = "Descrição do andamento", example = "Juntada de documentos")
    private String descricao;
    
    @Schema(description = "Tipo do andamento", example = "AUDIENCIA")
    private String tipo;
    
    @Schema(description = "Data de cadastro", example = "2024-01-15T14:35:00")
    private LocalDateTime dataCadastro;
}

