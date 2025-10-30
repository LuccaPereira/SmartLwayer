package smartLegalApi.application.cliente.usecase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import smartLegalApi.domain.cliente.entity.Cliente;
import smartLegalApi.domain.cliente.exception.ClienteNaoEncontradoException;
import smartLegalApi.domain.cliente.repository.ClienteRepository;
import smartLegalApi.domain.cliente.valueobject.Endereco;
import smartLegalApi.domain.shared.valueobject.Email;
import smartLegalApi.domain.shared.valueobject.Telefone;

/**
 * Caso de Uso: Atualizar Dados do Cliente
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AtualizarClienteUseCase {
    
    private final ClienteRepository clienteRepository;
    
    @Transactional
    @CacheEvict(value = "clientes", allEntries = true)
    public Cliente executar(Long id, String nomeCompleto, String emailStr, String telefoneStr, String enderecoStr) {
        log.info("Atualizando cliente ID: {}", id);
        
        // Buscar cliente
        Cliente cliente = clienteRepository.findById(id)
            .orElseThrow(() -> new ClienteNaoEncontradoException(id));
        
        // Criar value objects
        Email email = emailStr != null && !emailStr.isBlank() ? new Email(emailStr) : null;
        Telefone telefone = telefoneStr != null && !telefoneStr.isBlank() ? new Telefone(telefoneStr) : null;
        Endereco endereco = enderecoStr != null && !enderecoStr.isBlank() ? new Endereco(enderecoStr) : null;
        
        // Atualizar
        cliente.atualizar(nomeCompleto, email, telefone, endereco);
        
        // Salvar
        Cliente clienteAtualizado = clienteRepository.update(cliente);
        
        log.info("Cliente atualizado com sucesso. ID: {}", clienteAtualizado.getId());
        
        return clienteAtualizado;
    }
}

