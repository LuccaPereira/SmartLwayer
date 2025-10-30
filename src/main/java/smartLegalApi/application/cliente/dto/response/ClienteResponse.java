package smartLegalApi.application.cliente.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO de resposta para cliente
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Dados do cliente")
public class ClienteResponse {
    
    @Schema(description = "ID do cliente", example = "1")
    private Long id;
    
    @Schema(description = "Nome completo do cliente", example = "Maria Santos Silva")
    private String nomeCompleto;
    
    @Schema(description = "CPF/CNPJ formatado", example = "123.456.789-01")
    private String cpfCnpj;
    
    @Schema(description = "Tipo de pessoa", example = "Pessoa Física")
    private String tipoPessoa;
    
    @Schema(description = "Email do cliente", example = "maria.santos@example.com")
    private String email;
    
    @Schema(description = "Telefone formatado", example = "(11) 97777-7777")
    private String telefone;
    
    @Schema(description = "Endereço completo", example = "Rua das Flores, 123 - Centro - São Paulo/SP")
    private String endereco;
    
    @Schema(description = "Data de cadastro", example = "2024-01-15T10:30:00")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime dataCadastro;
    
    @Schema(description = "Data da última atualização", example = "2024-01-15T10:30:00")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime dataAtualizacao;
    
    @Schema(description = "Status do cliente", example = "true")
    private Boolean ativo;
}

