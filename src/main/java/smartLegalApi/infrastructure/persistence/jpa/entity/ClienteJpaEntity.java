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
 * Entidade JPA para persistência de Cliente
 */
@Entity
@Table(name = "clientes", indexes = {
    @Index(name = "idx_cliente_cpf_cnpj", columnList = "cpf_cnpj"),
    @Index(name = "idx_cliente_email", columnList = "email"),
    @Index(name = "idx_cliente_ativo", columnList = "ativo")
})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClienteJpaEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 100, name = "nome_completo")
    private String nomeCompleto;
    
    @Column(unique = true, nullable = false, length = 18, name = "cpf_cnpj")
    private String cpfCnpj;
    
    @Column(length = 100)
    private String email;
    
    @Column(length = 20)
    private String telefone;
    
    @Column(length = 255)
    private String endereco;
    
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

