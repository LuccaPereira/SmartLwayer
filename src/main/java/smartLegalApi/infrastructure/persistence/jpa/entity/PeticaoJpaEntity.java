package smartLegalApi.infrastructure.persistence.jpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import smartLegalApi.domain.peticao.valueobject.StatusPeticao;
import smartLegalApi.domain.peticao.valueobject.TipoPeticao;

import java.time.LocalDateTime;

/**
 * Entidade JPA para Petição
 */
@Entity
@Table(name = "peticoes", indexes = {
    @Index(name = "idx_peticao_processo", columnList = "id_processo"),
    @Index(name = "idx_peticao_advogado", columnList = "id_advogado"),
    @Index(name = "idx_peticao_status", columnList = "status"),
    @Index(name = "idx_peticao_tipo", columnList = "tipo")
})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PeticaoJpaEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "id_processo", nullable = false)
    private Long idProcesso;
    
    @Column(name = "id_advogado", nullable = false)
    private Long idAdvogado;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private TipoPeticao tipo;
    
    @Column(nullable = false, length = 200)
    private String titulo;
    
    @Column(columnDefinition = "TEXT")
    private String conteudo;
    
    @Column(name = "conteudo_gerado_ia", columnDefinition = "TEXT")
    private String conteudoGeradoIA;
    
    @Column(name = "prompt_utilizado", columnDefinition = "TEXT")
    private String promptUtilizado;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    @Builder.Default
    private StatusPeticao status = StatusPeticao.RASCUNHO;
    
    @Column(name = "caminho_documento", length = 255)
    private String caminhoDocumento;
    
    @CreationTimestamp
    @Column(name = "data_criacao", nullable = false, updatable = false)
    private LocalDateTime dataCriacao;
    
    @UpdateTimestamp
    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;
    
    @Column(name = "data_protocolo")
    private LocalDateTime dataProtocolo;
}

