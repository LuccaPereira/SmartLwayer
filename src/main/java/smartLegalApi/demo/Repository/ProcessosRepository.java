package smartLegalApi.demo.Repository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;
import smartLegalApi.demo.Model.Processos;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProcessosRepository extends JpaRepository<Processos, Long>{

    Optional<Processos> findByNumeroProcesso(String numeroProcesso);
    
}
