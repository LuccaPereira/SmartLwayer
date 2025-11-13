package smartLegalApi.application.peticao.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request para atualizar conteúdo da petição")
public class AtualizarConteudoRequest {
    
    @NotBlank(message = "Conteúdo é obrigatório")
    @Schema(description = "Novo conteúdo da petição")
    private String conteudo;
}

