package smartLegalApi.infrastructure.persistence.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import smartLegalApi.domain.cliente.entity.Cliente;
import smartLegalApi.domain.cliente.repository.ClienteRepository;
import smartLegalApi.infrastructure.persistence.jpa.repository.ClienteJpaRepository;
import smartLegalApi.infrastructure.persistence.mapper.ClienteJpaMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Adapter que implementa a interface do repositório de domínio
 * usando Spring Data JPA
 */
@Component
@RequiredArgsConstructor
public class ClienteRepositoryAdapter implements ClienteRepository {
    
    private final ClienteJpaRepository jpaRepository;
    private final ClienteJpaMapper mapper;
    
    @Override
    public Cliente save(Cliente cliente) {
        var jpaEntity = mapper.toJpaEntity(cliente);
        var savedEntity = jpaRepository.save(jpaEntity);
        return mapper.toDomain(savedEntity);
    }
    
    @Override
    public Cliente update(Cliente cliente) {
        var jpaEntity = mapper.toJpaEntity(cliente);
        var updatedEntity = jpaRepository.save(jpaEntity);
        return mapper.toDomain(updatedEntity);
    }
    
    @Override
    public Optional<Cliente> findById(Long id) {
        return jpaRepository.findById(id)
            .map(mapper::toDomain);
    }
    
    @Override
    public Optional<Cliente> findByCpfCnpj(String cpfCnpj) {
        return jpaRepository.findByCpfCnpj(cpfCnpj)
            .map(mapper::toDomain);
    }
    
    @Override
    public Optional<Cliente> findByEmail(String email) {
        return jpaRepository.findByEmail(email)
            .map(mapper::toDomain);
    }
    
    @Override
    public List<Cliente> findAllAtivos() {
        return jpaRepository.findAllAtivosOrdenados()
            .stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }
    
    @Override
    public List<Cliente> findAll() {
        return jpaRepository.findAll()
            .stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }
    
    @Override
    public boolean existsByCpfCnpj(String cpfCnpj) {
        return jpaRepository.existsByCpfCnpj(cpfCnpj);
    }
    
    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }
}

