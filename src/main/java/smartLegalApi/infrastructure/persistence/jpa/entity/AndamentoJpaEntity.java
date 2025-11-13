package smartLegalApi.infrastructure.persistence.jpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

/**
 * Entidade JPA para Andamento
 */
@Entity
@Table(name = "andamentos", indexes = {
    @Index(name = "idx_andamento_processo", columnList = "id_processo"),
    @Index(name = "idx_andamento_data", columnList = "data_andamento")
})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AndamentoJpaEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "id_processo", nullable = false)
    private Long idProcesso;
    
    @Column(name = "data_andamento", nullable = false)
    private LocalDateTime dataAndamento;
    
    @Column(nullable = false, columnDefinition = "TEXT")
    private String descricao;
    
    @Column(length = 50)
    private String tipo;
    
    @CreationTimestamp
    @Column(name = "data_cadastro", nullable = false, updatable = false)
    private LocalDateTime dataCadastro;
}

