package smartLegalApi.infrastructure.persistence.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import smartLegalApi.infrastructure.persistence.jpa.entity.ClienteJpaEntity;

import java.util.List;
import java.util.Optional;

/**
 * Repositório Spring Data JPA para Cliente
 */
@Repository
public interface ClienteJpaRepository extends JpaRepository<ClienteJpaEntity, Long> {
    
    Optional<ClienteJpaEntity> findByCpfCnpj(String cpfCnpj);
    
    Optional<ClienteJpaEntity> findByEmail(String email);
    
    List<ClienteJpaEntity> findByAtivoTrue();
    
    boolean existsByCpfCnpj(String cpfCnpj);
    
    @Query("SELECT c FROM ClienteJpaEntity c WHERE c.ativo = true ORDER BY c.nomeCompleto ASC")
    List<ClienteJpaEntity> findAllAtivosOrdenados();
}

