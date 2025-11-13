package smartLegalApi.infrastructure.persistence.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import smartLegalApi.infrastructure.persistence.jpa.entity.AdvogadoJpaEntity;

import java.util.List;
import java.util.Optional;

/**
 * Repositório Spring Data JPA para Advogado
 */
@Repository
public interface AdvogadoJpaRepository extends JpaRepository<AdvogadoJpaEntity, Long> {
    
    Optional<AdvogadoJpaEntity> findByOab(String oab);
    
    Optional<AdvogadoJpaEntity> findByEmail(String email);
    
    Optional<AdvogadoJpaEntity> findByCpf(String cpf);
    
    List<AdvogadoJpaEntity> findByAtivoTrue();
    
    boolean existsByOab(String oab);
    
    boolean existsByEmail(String email);
    
    @Query("SELECT a FROM AdvogadoJpaEntity a WHERE a.ativo = true ORDER BY a.nome ASC")
    List<AdvogadoJpaEntity> findAllAtivosOrdenados();
}

