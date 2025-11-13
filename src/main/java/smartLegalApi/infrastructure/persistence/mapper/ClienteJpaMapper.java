package smartLegalApi.infrastructure.persistence.mapper;

import org.springframework.stereotype.Component;
import smartLegalApi.domain.cliente.entity.Cliente;
import smartLegalApi.domain.cliente.valueobject.CpfCnpj;
import smartLegalApi.domain.cliente.valueobject.Endereco;
import smartLegalApi.domain.shared.valueobject.Email;
import smartLegalApi.domain.shared.valueobject.Telefone;
import smartLegalApi.infrastructure.persistence.jpa.entity.ClienteJpaEntity;

/**
 * Mapper entre entidade de domínio Cliente e entidade JPA
 */
@Component
public class ClienteJpaMapper {
    
    /**
     * Converte entidade de domínio para entidade JPA
     */
    public ClienteJpaEntity toJpaEntity(Cliente cliente) {
        if (cliente == null) {
            return null;
        }
        
        return ClienteJpaEntity.builder()
            .id(cliente.getId())
            .nomeCompleto(cliente.getNomeCompleto())
            .cpfCnpj(cliente.getCpfCnpj().toString())
            .email(cliente.getEmail() != null ? cliente.getEmail().toString() : null)
            .telefone(cliente.getTelefone() != null ? cliente.getTelefone().toString() : null)
            .endereco(cliente.getEndereco() != null ? cliente.getEndereco().toString() : null)
            .dataCadastro(cliente.getDataCadastro())
            .dataAtualizacao(cliente.getDataAtualizacao())
            .ativo(cliente.isAtivo())
            .build();
    }
    
    /**
     * Converte entidade JPA para entidade de domínio
     */
    public Cliente toDomain(ClienteJpaEntity jpaEntity) {
        if (jpaEntity == null) {
            return null;
        }
        
        return Cliente.builder()
            .id(jpaEntity.getId())
            .nomeCompleto(jpaEntity.getNomeCompleto())
            .cpfCnpj(new CpfCnpj(jpaEntity.getCpfCnpj()))
            .email(jpaEntity.getEmail() != null ? new Email(jpaEntity.getEmail()) : null)
            .telefone(jpaEntity.getTelefone() != null ? new Telefone(jpaEntity.getTelefone()) : null)
            .endereco(jpaEntity.getEndereco() != null ? new Endereco(jpaEntity.getEndereco()) : null)
            .dataCadastro(jpaEntity.getDataCadastro())
            .dataAtualizacao(jpaEntity.getDataAtualizacao())
            .ativo(jpaEntity.getAtivo())
            .build();
    }
}

