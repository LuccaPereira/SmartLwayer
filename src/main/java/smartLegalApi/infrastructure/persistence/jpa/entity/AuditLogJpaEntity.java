package smartLegalApi.infrastructure.persistence.jpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import smartLegalApi.domain.audit.valueobject.TipoOperacao;

import java.time.LocalDateTime;

/**
 * Entidade JPA para AuditLog
 */
@Entity
@Table(name = "audit_logs", indexes = {
    @Index(name = "idx_audit_usuario", columnList = "id_usuario"),
    @Index(name = "idx_audit_entidade", columnList = "entidade, id_entidade"),
    @Index(name = "idx_audit_tipo", columnList = "tipo_operacao"),
    @Index(name = "idx_audit_data", columnList = "data_hora")
})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuditLogJpaEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "id_usuario")
    private Long idUsuario;
    
    @Column(name = "nome_usuario", length = 100)
    private String nomeUsuario;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_operacao", nullable = false, length = 30)
    private TipoOperacao tipoOperacao;
    
    @Column(nullable = false, length = 50)
    private String entidade;
    
    @Column(name = "id_entidade")
    private Long idEntidade;
    
    @Column(columnDefinition = "TEXT")
    private String detalhes;
    
    @Column(name = "ip_address", length = 45)
    private String ipAddress;
    
    @Column(name = "user_agent", length = 255)
    private String userAgent;
    
    @CreationTimestamp
    @Column(name = "data_hora", nullable = false)
    private LocalDateTime dataHora;
    
    @Column(name = "dados_antigos", columnDefinition = "TEXT")
    private String dadosAntigos;
    
    @Column(name = "dados_novos", columnDefinition = "TEXT")
    private String dadosNovos;
}

