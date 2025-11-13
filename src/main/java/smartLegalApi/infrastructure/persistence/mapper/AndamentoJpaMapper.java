package smartLegalApi.infrastructure.persistence.mapper;

import org.springframework.stereotype.Component;
import smartLegalApi.domain.processo.entity.Andamento;
import smartLegalApi.infrastructure.persistence.jpa.entity.AndamentoJpaEntity;

/**
 * Mapper entre Andamento de domínio e JPA
 */
@Component
public class AndamentoJpaMapper {
    
    public AndamentoJpaEntity toJpaEntity(Andamento andamento) {
        if (andamento == null) return null;
        
        return AndamentoJpaEntity.builder()
            .id(andamento.getId())
            .idProcesso(andamento.getIdProcesso())
            .dataAndamento(andamento.getDataAndamento())
            .descricao(andamento.getDescricao())
            .tipo(andamento.getTipo())
            .dataCadastro(andamento.getDataCadastro())
            .build();
    }
    
    public Andamento toDomain(AndamentoJpaEntity jpaEntity) {
        if (jpaEntity == null) return null;
        
        return Andamento.builder()
            .id(jpaEntity.getId())
            .idProcesso(jpaEntity.getIdProcesso())
            .dataAndamento(jpaEntity.getDataAndamento())
            .descricao(jpaEntity.getDescricao())
            .tipo(jpaEntity.getTipo())
            .dataCadastro(jpaEntity.getDataCadastro())
            .build();
    }
}

