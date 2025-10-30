package smartLegalApi.infrastructure.persistence.mapper;

import org.springframework.stereotype.Component;
import smartLegalApi.domain.advogado.entity.Advogado;
import smartLegalApi.domain.shared.valueobject.CPF;
import smartLegalApi.domain.shared.valueobject.Email;
import smartLegalApi.domain.shared.valueobject.OAB;
import smartLegalApi.domain.shared.valueobject.Telefone;
import smartLegalApi.infrastructure.persistence.jpa.entity.AdvogadoJpaEntity;

/**
 * Mapper entre entidade de domínio e entidade JPA
 */
@Component
public class AdvogadoJpaMapper {
    
    /**
     * Converte entidade de domínio para entidade JPA
     */
    public AdvogadoJpaEntity toJpaEntity(Advogado advogado) {
        if (advogado == null) {
            return null;
        }
        
        return AdvogadoJpaEntity.builder()
            .id(advogado.getId())
            .oab(advogado.getOab().toString())
            .nome(advogado.getNome())
            .cpf(advogado.getCpf().toString())
            .email(advogado.getEmail().toString())
            .telefone(advogado.getTelefone() != null ? advogado.getTelefone().toString() : null)
            .senhaHash(advogado.getSenhaHash())
            .dataCadastro(advogado.getDataCadastro())
            .dataAtualizacao(advogado.getDataAtualizacao())
            .ativo(advogado.isAtivo())
            .build();
    }
    
    /**
     * Converte entidade JPA para entidade de domínio
     */
    public Advogado toDomain(AdvogadoJpaEntity jpaEntity) {
        if (jpaEntity == null) {
            return null;
        }
        
        return Advogado.builder()
            .id(jpaEntity.getId())
            .oab(new OAB(jpaEntity.getOab()))
            .nome(jpaEntity.getNome())
            .cpf(new CPF(jpaEntity.getCpf()))
            .email(new Email(jpaEntity.getEmail()))
            .telefone(jpaEntity.getTelefone() != null ? new Telefone(jpaEntity.getTelefone()) : null)
            .senhaHash(jpaEntity.getSenhaHash())
            .dataCadastro(jpaEntity.getDataCadastro())
            .dataAtualizacao(jpaEntity.getDataAtualizacao())
            .ativo(jpaEntity.getAtivo())
            .build();
    }
}

