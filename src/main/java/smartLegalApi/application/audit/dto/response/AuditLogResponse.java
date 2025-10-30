package smartLegalApi.application.audit.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import smartLegalApi.domain.audit.valueobject.TipoOperacao;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Resposta com dados do log de auditoria")
public class AuditLogResponse {
    
    @Schema(description = "ID do log", example = "1")
    private Long id;
    
    @Schema(description = "ID do usuário", example = "1")
    private Long idUsuario;
    
    @Schema(description = "Nome do usuário", example = "João Silva")
    private String nomeUsuario;
    
    @Schema(description = "Tipo de operação", example = "CREATE")
    private TipoOperacao tipoOperacao;
    
    @Schema(description = "Nome da entidade", example = "Processo")
    private String entidade;
    
    @Schema(description = "ID da entidade", example = "1")
    private Long idEntidade;
    
    @Schema(description = "Detalhes da operação", example = "Criação em Processo")
    private String detalhes;
    
    @Schema(description = "Endereço IP", example = "192.168.1.100")
    private String ipAddress;
    
    @Schema(description = "User Agent do navegador")
    private String userAgent;
    
    @Schema(description = "Data e hora da operação", example = "2024-01-15T10:30:00")
    private LocalDateTime dataHora;
    
    @Schema(description = "Dados antes da alteração (JSON)")
    private String dadosAntigos;
    
    @Schema(description = "Dados após a alteração (JSON)")
    private String dadosNovos;
}

