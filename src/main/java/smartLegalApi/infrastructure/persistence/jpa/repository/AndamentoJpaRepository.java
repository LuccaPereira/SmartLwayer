package smartLegalApi.infrastructure.persistence.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import smartLegalApi.infrastructure.persistence.jpa.entity.AndamentoJpaEntity;

import java.util.List;

/**
 * Repositório JPA para Andamento
 */
@Repository
public interface AndamentoJpaRepository extends JpaRepository<AndamentoJpaEntity, Long> {
    
    List<AndamentoJpaEntity> findByIdProcesso(Long idProcesso);
    
    @Query("SELECT a FROM AndamentoJpaEntity a WHERE a.idProcesso = :idProcesso ORDER BY a.dataAndamento DESC")
    List<AndamentoJpaEntity> findByProcessoOrdenadoPorData(Long idProcesso);
}

