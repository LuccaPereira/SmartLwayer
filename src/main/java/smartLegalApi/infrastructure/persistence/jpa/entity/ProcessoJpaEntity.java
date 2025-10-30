package smartLegalApi.infrastructure.persistence.jpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import smartLegalApi.domain.processo.valueobject.StatusProcesso;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Entidade JPA para Processo
 */
@Entity
@Table(name = "processos", indexes = {
    @Index(name = "idx_processo_numero", columnList = "numero_processo"),
    @Index(name = "idx_processo_advogado", columnList = "id_advogado"),
    @Index(name = "idx_processo_cliente", columnList = "id_cliente"),
    @Index(name = "idx_processo_status", columnList = "status"),
    @Index(name = "idx_processo_data_abertura", columnList = "data_abertura")
})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProcessoJpaEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "numero_processo", unique = true, nullable = false, length = 30)
    private String numeroProcesso;
    
    @Column(nullable = false, length = 150)
    private String titulo;
    
    @Column(columnDefinition = "TEXT")
    private String descricao;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private StatusProcesso status = StatusProcesso.ATIVO;
    
    @Column(name = "data_abertura", nullable = false)
    private LocalDate dataAbertura;
    
    @Column(name = "data_encerramento")
    private LocalDate dataEncerramento;
    
    @Column(name = "id_advogado", nullable = false)
    private Long idAdvogado;
    
    @Column(name = "id_cliente", nullable = false)
    private Long idCliente;
    
    @CreationTimestamp
    @Column(name = "data_cadastro", nullable = false, updatable = false)
    private LocalDateTime dataCadastro;
    
    @UpdateTimestamp
    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;
}

