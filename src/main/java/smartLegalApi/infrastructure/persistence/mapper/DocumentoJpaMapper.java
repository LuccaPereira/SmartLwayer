package smartLegalApi.infrastructure.persistence.mapper;

import org.springframework.stereotype.Component;
import smartLegalApi.domain.processo.entity.Documento;
import smartLegalApi.infrastructure.persistence.jpa.entity.DocumentoJpaEntity;

/**
 * Mapper entre Documento de domínio e JPA
 */
@Component
public class DocumentoJpaMapper {
    
    public DocumentoJpaEntity toJpaEntity(Documento documento) {
        if (documento == null) return null;
        
        return DocumentoJpaEntity.builder()
            .id(documento.getId())
            .idProcesso(documento.getIdProcesso())
            .nomeDocumento(documento.getNomeDocumento())
            .caminhoArquivo(documento.getCaminhoArquivo())
            .tipoArquivo(documento.getTipoArquivo())
            .tamanhoBytes(documento.getTamanhoBytes())
            .hashArquivo(documento.getHashArquivo())
            .dataUpload(documento.getDataUpload())
            .build();
    }
    
    public Documento toDomain(DocumentoJpaEntity jpaEntity) {
        if (jpaEntity == null) return null;
        
        return Documento.builder()
            .id(jpaEntity.getId())
            .idProcesso(jpaEntity.getIdProcesso())
            .nomeDocumento(jpaEntity.getNomeDocumento())
            .caminhoArquivo(jpaEntity.getCaminhoArquivo())
            .tipoArquivo(jpaEntity.getTipoArquivo())
            .tamanhoBytes(jpaEntity.getTamanhoBytes())
            .hashArquivo(jpaEntity.getHashArquivo())
            .dataUpload(jpaEntity.getDataUpload())
            .build();
    }
}

