package smartLegalApi.application.auth.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import smartLegalApi.presentation.validation.annotation.ValidCPF;

/**
 * DTO de Request: Registro de novo advogado
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Dados para registro de novo advogado")
public class RegistroRequest {
    
    @NotBlank(message = "Número da OAB é obrigatório")
    @Pattern(regexp = "^\\d{6}$", message = "Número da OAB deve ter exatamente 6 dígitos")
    @Schema(description = "Número da OAB (apenas 6 dígitos)", example = "123456")
    private String oabNumero;
    
    @NotBlank(message = "UF da OAB é obrigatória")
    @Pattern(regexp = "^[A-Z]{2}$", message = "UF deve ter 2 letras maiúsculas")
    @Schema(description = "UF da OAB", example = "SP")
    private String oabUf;
    
    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 3, max = 100, message = "Nome deve ter entre 3 e 100 caracteres")
    @Schema(description = "Nome completo do advogado", example = "João da Silva")
    private String nome;
    
    @NotBlank(message = "CPF é obrigatório")
    @ValidCPF
    @Schema(description = "CPF do advogado", example = "12345678900")
    private String cpf;
    
    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email inválido")
    @Schema(description = "Email do advogado", example = "joao@example.com")
    private String email;
    
    @Pattern(regexp = "^\\d{10,11}$", message = "Telefone deve ter 10 ou 11 dígitos")
    @Schema(description = "Telefone do advogado (apenas números)", example = "11987654321")
    private String telefone;
    
    @NotBlank(message = "Senha é obrigatória")
    @Size(min = 8, message = "Senha deve ter no mínimo 8 caracteres")
    @Schema(description = "Senha (mínimo 8 caracteres, letras e números)", example = "Senha123")
    private String senha;
    
    @NotBlank(message = "Confirmação de senha é obrigatória")
    @Schema(description = "Confirmação da senha", example = "Senha123")
    private String confirmarSenha;
}

