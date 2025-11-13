package smartLegalApi.application.peticao.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import smartLegalApi.domain.peticao.valueobject.StatusPeticao;
import smartLegalApi.domain.peticao.valueobject.TipoPeticao;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Resposta com dados da petição")
public class PeticaoResponse {
    
    @Schema(description = "ID da petição", example = "1")
    private Long id;
    
    @Schema(description = "ID do processo", example = "1")
    private Long idProcesso;
    
    @Schema(description = "ID do advogado responsável", example = "1")
    private Long idAdvogado;
    
    @Schema(description = "Tipo da petição", example = "INICIAL")
    private TipoPeticao tipo;
    
    @Schema(description = "Título da petição", example = "Ação de Cobrança - Petição Inicial")
    private String titulo;
    
    @Schema(description = "Conteúdo da petição")
    private String conteudo;
    
    @Schema(description = "Conteúdo gerado pela IA")
    private String conteudoGeradoIA;
    
    @Schema(description = "Prompt utilizado na geração com IA")
    private String promptUtilizado;
    
    @Schema(description = "Status da petição", example = "REVISAO")
    private StatusPeticao status;
    
    @Schema(description = "Caminho do documento gerado", example = "./uploads/peticoes/peticao_1_20240115.docx")
    private String caminhoDocumento;
    
    @Schema(description = "Data de criação", example = "2024-01-15T10:30:00")
    private LocalDateTime dataCriacao;
    
    @Schema(description = "Data da última atualização", example = "2024-01-15T14:45:00")
    private LocalDateTime dataAtualizacao;
    
    @Schema(description = "Data do protocolo", example = "2024-01-16T09:00:00")
    private LocalDateTime dataProtocolo;
}

