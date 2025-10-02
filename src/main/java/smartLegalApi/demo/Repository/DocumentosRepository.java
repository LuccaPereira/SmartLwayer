package smartLegalApi.demo.Repository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;
import smartLegalApi.demo.Model.Documentos;
import java.util.List;

@Repository
public class DocumentosRepository {
    
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public Documentos save(Documentos documentos){
        entityManager.persist(documentos);
        return documentos;
    }

    public Documentos findById(Long id){
        return entityManager.find(Documentos.class, id);
    }

    public List<Documentos> findAll(){
        return entityManager.createQuery("FROM Documentos", Documentos.class).getResultList();
    }

    @Transactional
    public Documentos update(Documentos documentos){
        return entityManager.merge(documentos);
    }

    @Transactional
    public void deleteById(Long id){
        Documentos documentos = findById(id);
        if (documentos != null){
            entityManager.remove(documentos);
        }
    } 
}
