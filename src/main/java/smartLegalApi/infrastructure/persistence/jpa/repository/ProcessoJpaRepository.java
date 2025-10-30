package smartLegalApi.infrastructure.persistence.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import smartLegalApi.domain.processo.valueobject.StatusProcesso;
import smartLegalApi.infrastructure.persistence.jpa.entity.ProcessoJpaEntity;

import java.util.List;
import java.util.Optional;

/**
 * Repositório JPA para Processo
 */
@Repository
public interface ProcessoJpaRepository extends JpaRepository<ProcessoJpaEntity, Long> {
    
    Optional<ProcessoJpaEntity> findByNumeroProcesso(String numeroProcesso);
    
    List<ProcessoJpaEntity> findByIdAdvogado(Long idAdvogado);
    
    List<ProcessoJpaEntity> findByIdCliente(Long idCliente);
    
    List<ProcessoJpaEntity> findByStatus(StatusProcesso status);
    
    List<ProcessoJpaEntity> findByIdAdvogadoAndStatus(Long idAdvogado, StatusProcesso status);
    
    boolean existsByNumeroProcesso(String numeroProcesso);
    
    @Query("SELECT p FROM ProcessoJpaEntity p WHERE p.idAdvogado = :idAdvogado ORDER BY p.dataAbertura DESC")
    List<ProcessoJpaEntity> findByAdvogadoOrdenadoPorData(Long idAdvogado);
}

