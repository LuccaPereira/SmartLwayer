package smartLegalApi.demo.Repository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;
import smartLegalApi.demo.Model.Advogado;
import java.util.List;
import java.util.Optional;

import java.util.List;

@Repository
public class AdvogadoRepository {
    
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    //@Transactional indica que o método deve ser executado dentro de uma transação, ou seja se falhar ele desfaz todas as operações feitas dentro dela
    public Advogado save(Advogado advogado){
        //.persist() salva o objeto no banco de dados
        entityManager.persist(advogado);
        return advogado;
    }

    public Advogado findById(Long id){
        //.find() busca um objeto no banco de dados pelo seu ID
        return entityManager.find(Advogado.class, id);
    }

    public Advogado findByEmail(String email){
        return entityManager.createQuery("FROM Advogado WHERE email = email", Advogado.class).getSingleResult();
    }
    
    public List<Advogado> findAll(){
        //Cria uma consulta JPQL para buscar todos os advogados
        //Se refere as classes e não as tabelas do banco de dados
        return entityManager.createQuery("FROM Advogado", Advogado.class).getResultList();
    }

    @Transactional
    public Advogado update(Advogado advogado){
        //.merge() atualiza o objeto no banco de dados
        return entityManager.merge(advogado);
    }

    @Transactional
    public void deleteById(Long id){
        Advogado advogado = findById(id);
        if (advogado != null){
            entityManager.remove(advogado);
        }
    }

    public Optional<Advogado> findByOab(String oab){
        List<Advogado> advogadosOab =  entityManager.createQuery("SELECT a FROM Advogado a WHERE a.oab = :oab", Advogado.class).setParameter("oab", oab).getResultList();

        if(advogadosOab.isEmpty()){
            return Optional.empty();
        } else {
            return Optional.of(advogadosOab.get(0));
        }
    }
    
}
