package smartLegalApi.demo.Repository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;
import smartLegalApi.demo.Model.Cliente;

import java.util.List;

@Repository
public class ClienteRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public Cliente save(Cliente cliente){
        entityManager.persist(cliente);
        return cliente;
    }

    public Cliente findById(Long id){
        return entityManager.find(Cliente.class, id);
    }

    public List<Cliente> findAll(){
        return entityManager.createQuery("FROM Cliente", Cliente.class).getResultList();
    }

    @Transactional
    public Cliente update(Cliente cliente){
        return entityManager.merge(cliente);
    }

    @Transactional
    public void deleteById(Long id){
        Cliente cliente = findById(id);
        if (cliente != null){
            entityManager.remove(cliente);
        }
    }
}
