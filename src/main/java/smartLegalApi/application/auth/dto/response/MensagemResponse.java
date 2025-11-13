package smartLegalApi.application.auth.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO de Response: Mensagem simples
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Resposta com mensagem")
public class MensagemResponse {
    
    @Schema(description = "Mensagem de retorno", example = "Operação realizada com sucesso")
    private String mensagem;
    
    @Schema(description = "Dados adicionais (opcional)")
    private Object dados;
    
    public MensagemResponse(String mensagem) {
        this.mensagem = mensagem;
    }
}

