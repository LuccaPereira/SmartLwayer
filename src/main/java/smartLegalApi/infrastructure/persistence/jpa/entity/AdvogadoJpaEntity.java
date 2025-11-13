package smartLegalApi.infrastructure.persistence.jpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

/**
 * Entidade JPA para persistência de Advogado
 * Separada da entidade de domínio para manter independência da infraestrutura
 */
@Entity
@Table(name = "advogado", indexes = {
    @Index(name = "idx_advogado_oab", columnList = "oab"),
    @Index(name = "idx_advogado_email", columnList = "email"),
    @Index(name = "idx_advogado_ativo", columnList = "ativo")
})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdvogadoJpaEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false, length = 15)
    private String oab;
    
    @Column(nullable = false, length = 100)
    private String nome;
    
    @Column(nullable = false, length = 11)
    private String cpf;
    
    @Column(unique = true, nullable = false, length = 100)
    private String email;
    
    @Column(length = 15)
    private String telefone;
    
    @Column(nullable = false, length = 255, name = "senha")
    private String senhaHash;
    
    @CreationTimestamp
    @Column(nullable = false, updatable = false, name = "data_cadastro")
    private LocalDateTime dataCadastro;
    
    @UpdateTimestamp
    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;
    
    @Column(nullable = false)
    @Builder.Default
    private Boolean ativo = true;
}

