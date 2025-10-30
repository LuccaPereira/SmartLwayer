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
@Schema(description = "Request para gerar petição com IA")
public class GerarComIARequest {
    
    @NotBlank(message = "Contexto é obrigatório")
    @Schema(description = "Contexto do caso", example = "Cliente contratou empresa para prestação de serviços de consultoria...")
    private String contexto;
    
    @Schema(description = "Detalhes adicionais", example = "Incluir fundamentos do CDC e jurisprudência recente")
    private String detalhesAdicionais;
}

