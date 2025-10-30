package smartLegalApi.infrastructure.persistence.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import smartLegalApi.domain.peticao.valueobject.StatusPeticao;
import smartLegalApi.domain.peticao.valueobject.TipoPeticao;
import smartLegalApi.infrastructure.persistence.jpa.entity.PeticaoJpaEntity;

import java.util.List;

/**
 * Repositório JPA para Petição
 */
@Repository
public interface PeticaoJpaRepository extends JpaRepository<PeticaoJpaEntity, Long> {
    
    List<PeticaoJpaEntity> findByIdProcesso(Long idProcesso);
    
    List<PeticaoJpaEntity> findByIdAdvogado(Long idAdvogado);
    
    List<PeticaoJpaEntity> findByIdAdvogadoAndStatus(Long idAdvogado, StatusPeticao status);
    
    List<PeticaoJpaEntity> findByIdProcessoAndTipo(Long idProcesso, TipoPeticao tipo);
    
    @Query("SELECT p FROM PeticaoJpaEntity p WHERE p.idAdvogado = :idAdvogado ORDER BY p.dataCriacao DESC")
    List<PeticaoJpaEntity> findByAdvogadoOrdenadoPorData(Long idAdvogado);
    
    @Query("SELECT p FROM PeticaoJpaEntity p WHERE p.idProcesso = :idProcesso ORDER BY p.dataCriacao DESC")
    List<PeticaoJpaEntity> findByProcessoOrdenadoPorData(Long idProcesso);
}

