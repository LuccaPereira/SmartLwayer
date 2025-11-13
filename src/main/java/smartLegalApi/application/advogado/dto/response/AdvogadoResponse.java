package smartLegalApi.application.advogado.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO de resposta para advogado
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Dados do advogado")
public class AdvogadoResponse {
    
    @Schema(description = "ID do advogado", example = "1")
    private Long id;
    
    @Schema(description = "Nome completo do advogado", example = "Dr. João Silva")
    private String nome;
    
    @Schema(description = "Número da OAB", example = "SP123456")
    private String oab;
    
    @Schema(description = "CPF do advogado (formato: 000.000.000-00)", example = "123.456.789-01")
    private String cpf;
    
    @Schema(description = "Email do advogado", example = "joao.silva@example.com")
    private String email;
    
    @Schema(description = "Telefone do advogado (formato: (00) 00000-0000)", example = "(11) 98888-8888")
    private String telefone;
    
    @Schema(description = "Data de cadastro", example = "2024-01-15T10:30:00")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime dataCadastro;
    
    @Schema(description = "Data da última atualização", example = "2024-01-15T10:30:00")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime dataAtualizacao;
    
    @Schema(description = "Status do advogado", example = "true")
    private Boolean ativo;
}

