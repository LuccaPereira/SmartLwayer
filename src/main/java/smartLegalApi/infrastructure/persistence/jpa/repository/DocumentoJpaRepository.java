package smartLegalApi.infrastructure.persistence.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import smartLegalApi.infrastructure.persistence.jpa.entity.DocumentoJpaEntity;

import java.util.List;

/**
 * Repositório JPA para Documento
 */
@Repository
public interface DocumentoJpaRepository extends JpaRepository<DocumentoJpaEntity, Long> {
    
    List<DocumentoJpaEntity> findByIdProcesso(Long idProcesso);
    
    List<DocumentoJpaEntity> findByIdProcessoAndTipoArquivo(Long idProcesso, String tipoArquivo);
}

