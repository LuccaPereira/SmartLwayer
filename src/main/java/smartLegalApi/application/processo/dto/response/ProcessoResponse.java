package smartLegalApi.application.processo.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import smartLegalApi.domain.processo.valueobject.StatusProcesso;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Resposta com dados do processo")
public class ProcessoResponse {
    
    @Schema(description = "ID do processo", example = "1")
    private Long id;
    
    @Schema(description = "Número do processo", example = "1234567-89.2024.8.21.0001")
    private String numeroProcesso;
    
    @Schema(description = "Título do processo", example = "Ação de Cobrança")
    private String titulo;
    
    @Schema(description = "Descrição do processo")
    private String descricao;
    
    @Schema(description = "Status do processo", example = "ATIVO")
    private StatusProcesso status;
    
    @Schema(description = "Data de abertura", example = "2024-01-15")
    private LocalDate dataAbertura;
    
    @Schema(description = "Data de encerramento", example = "2024-06-30")
    private LocalDate dataEncerramento;
    
    @Schema(description = "ID do advogado responsável", example = "1")
    private Long idAdvogado;
    
    @Schema(description = "ID do cliente", example = "1")
    private Long idCliente;
    
    @Schema(description = "Data de cadastro", example = "2024-01-15T10:30:00")
    private LocalDateTime dataCadastro;
    
    @Schema(description = "Data da última atualização", example = "2024-01-20T14:45:00")
    private LocalDateTime dataAtualizacao;
}

