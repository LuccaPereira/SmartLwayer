package smartLegalApi.infrastructure.persistence.mapper;

import org.springframework.stereotype.Component;
import smartLegalApi.domain.peticao.entity.Peticao;
import smartLegalApi.infrastructure.persistence.jpa.entity.PeticaoJpaEntity;

/**
 * Mapper entre Petição de domínio e JPA
 */
@Component
public class PeticaoJpaMapper {
    
    public PeticaoJpaEntity toJpaEntity(Peticao peticao) {
        if (peticao == null) return null;
        
        return PeticaoJpaEntity.builder()
            .id(peticao.getId())
            .idProcesso(peticao.getIdProcesso())
            .idAdvogado(peticao.getIdAdvogado())
            .tipo(peticao.getTipo())
            .titulo(peticao.getTitulo())
            .conteudo(peticao.getConteudo())
            .conteudoGeradoIA(peticao.getConteudoGeradoIA())
            .promptUtilizado(peticao.getPromptUtilizado())
            .status(peticao.getStatus())
            .caminhoDocumento(peticao.getCaminhoDocumento())
            .dataCriacao(peticao.getDataCriacao())
            .dataAtualizacao(peticao.getDataAtualizacao())
            .dataProtocolo(peticao.getDataProtocolo())
            .build();
    }
    
    public Peticao toDomain(PeticaoJpaEntity jpaEntity) {
        if (jpaEntity == null) return null;
        
        return Peticao.builder()
            .id(jpaEntity.getId())
            .idProcesso(jpaEntity.getIdProcesso())
            .idAdvogado(jpaEntity.getIdAdvogado())
            .tipo(jpaEntity.getTipo())
            .titulo(jpaEntity.getTitulo())
            .conteudo(jpaEntity.getConteudo())
            .conteudoGeradoIA(jpaEntity.getConteudoGeradoIA())
            .promptUtilizado(jpaEntity.getPromptUtilizado())
            .status(jpaEntity.getStatus())
            .caminhoDocumento(jpaEntity.getCaminhoDocumento())
            .dataCriacao(jpaEntity.getDataCriacao())
            .dataAtualizacao(jpaEntity.getDataAtualizacao())
            .dataProtocolo(jpaEntity.getDataProtocolo())
            .build();
    }
}

