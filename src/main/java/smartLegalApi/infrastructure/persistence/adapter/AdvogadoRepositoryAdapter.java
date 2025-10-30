package smartLegalApi.infrastructure.persistence.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import smartLegalApi.domain.advogado.entity.Advogado;
import smartLegalApi.domain.advogado.repository.AdvogadoRepository;
import smartLegalApi.infrastructure.persistence.jpa.repository.AdvogadoJpaRepository;
import smartLegalApi.infrastructure.persistence.mapper.AdvogadoJpaMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Adapter que implementa a interface do repositório de domínio
 * usando Spring Data JPA
 */
@Component
@RequiredArgsConstructor
public class AdvogadoRepositoryAdapter implements AdvogadoRepository {
    
    private final AdvogadoJpaRepository jpaRepository;
    private final AdvogadoJpaMapper mapper;
    
    @Override
    public Advogado save(Advogado advogado) {
        var jpaEntity = mapper.toJpaEntity(advogado);
        var savedEntity = jpaRepository.save(jpaEntity);
        return mapper.toDomain(savedEntity);
    }
    
    @Override
    public Advogado update(Advogado advogado) {
        var jpaEntity = mapper.toJpaEntity(advogado);
        var updatedEntity = jpaRepository.save(jpaEntity);
        return mapper.toDomain(updatedEntity);
    }
    
    @Override
    public Optional<Advogado> findById(Long id) {
        return jpaRepository.findById(id)
            .map(mapper::toDomain);
    }
    
    @Override
    public Optional<Advogado> findByOAB(String oab) {
        return jpaRepository.findByOab(oab)
            .map(mapper::toDomain);
    }
    
    @Override
    public Optional<Advogado> findByEmail(String email) {
        return jpaRepository.findByEmail(email)
            .map(mapper::toDomain);
    }
    
    @Override
    public Optional<Advogado> findByCPF(String cpf) {
        return jpaRepository.findByCpf(cpf)
            .map(mapper::toDomain);
    }
    
    @Override
    public List<Advogado> findAllAtivos() {
        return jpaRepository.findAllAtivosOrdenados()
            .stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }
    
    @Override
    public List<Advogado> findAll() {
        return jpaRepository.findAll()
            .stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }
    
    @Override
    public boolean existsByOAB(String oab) {
        return jpaRepository.existsByOab(oab);
    }
    
    @Override
    public boolean existsByEmail(String email) {
        return jpaRepository.existsByEmail(email);
    }
    
    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }
}

