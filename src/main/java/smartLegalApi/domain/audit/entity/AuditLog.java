package smartLegalApi.domain.audit.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import smartLegalApi.domain.audit.valueobject.TipoOperacao;

import java.time.LocalDateTime;

/**
 * Entidade de domínio: Log de Auditoria
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuditLog {
    
    private Long id;
    private Long idUsuario;
    private String nomeUsuario;
    private TipoOperacao tipoOperacao;
    private String entidade;
    private Long idEntidade;
    private String detalhes;
    private String ipAddress;
    private String userAgent;
    private LocalDateTime dataHora;
    private String dadosAntigos;
    private String dadosNovos;
    
    /**
     * Cria um novo log de auditoria
     */
    public static AuditLog criar(
        Long idUsuario,
        String nomeUsuario,
        TipoOperacao tipoOperacao,
        String entidade,
        Long idEntidade,
        String detalhes
    ) {
        return AuditLog.builder()
            .idUsuario(idUsuario)
            .nomeUsuario(nomeUsuario)
            .tipoOperacao(tipoOperacao)
            .entidade(entidade)
            .idEntidade(idEntidade)
            .detalhes(detalhes)
            .dataHora(LocalDateTime.now())
            .build();
    }
    
    /**
     * Adiciona informações de contexto HTTP
     */
    public void adicionarContextoHttp(String ipAddress, String userAgent) {
        this.ipAddress = ipAddress;
        this.userAgent = userAgent;
    }
    
    /**
     * Adiciona dados do before/after
     */
    public void adicionarDadosAlteracao(String dadosAntigos, String dadosNovos) {
        this.dadosAntigos = dadosAntigos;
        this.dadosNovos = dadosNovos;
    }
}

