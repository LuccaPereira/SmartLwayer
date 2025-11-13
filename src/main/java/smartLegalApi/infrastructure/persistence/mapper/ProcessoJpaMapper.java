package smartLegalApi.infrastructure.persistence.mapper;

import org.springframework.stereotype.Component;
import smartLegalApi.domain.processo.entity.Processo;
import smartLegalApi.domain.processo.valueobject.NumeroProcesso;
import smartLegalApi.infrastructure.persistence.jpa.entity.ProcessoJpaEntity;

/**
 * Mapper entre Processo de domínio e JPA
 */
@Component
public class ProcessoJpaMapper {
    
    public ProcessoJpaEntity toJpaEntity(Processo processo) {
        if (processo == null) return null;
        
        return ProcessoJpaEntity.builder()
            .id(processo.getId())
            .numeroProcesso(processo.getNumeroProcesso().toString())
            .titulo(processo.getTitulo())
            .descricao(processo.getDescricao())
            .status(processo.getStatus())
            .dataAbertura(processo.getDataAbertura())
            .dataEncerramento(processo.getDataEncerramento())
            .idAdvogado(processo.getIdAdvogado())
            .idCliente(processo.getIdCliente())
            .dataCadastro(processo.getDataCadastro())
            .dataAtualizacao(processo.getDataAtualizacao())
            .build();
    }
    
    public Processo toDomain(ProcessoJpaEntity jpaEntity) {
        if (jpaEntity == null) return null;
        
        return Processo.builder()
            .id(jpaEntity.getId())
            .numeroProcesso(new NumeroProcesso(jpaEntity.getNumeroProcesso()))
            .titulo(jpaEntity.getTitulo())
            .descricao(jpaEntity.getDescricao())
            .status(jpaEntity.getStatus())
            .dataAbertura(jpaEntity.getDataAbertura())
            .dataEncerramento(jpaEntity.getDataEncerramento())
            .idAdvogado(jpaEntity.getIdAdvogado())
            .idCliente(jpaEntity.getIdCliente())
            .dataCadastro(jpaEntity.getDataCadastro())
            .dataAtualizacao(jpaEntity.getDataAtualizacao())
            .build();
    }
}

