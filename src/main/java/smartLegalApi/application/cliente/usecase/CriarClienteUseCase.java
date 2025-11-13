package smartLegalApi.application.cliente.usecase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import smartLegalApi.domain.cliente.entity.Cliente;
import smartLegalApi.domain.cliente.exception.CpfCnpjJaCadastradoException;
import smartLegalApi.domain.cliente.repository.ClienteRepository;
import smartLegalApi.domain.cliente.valueobject.CpfCnpj;
import smartLegalApi.domain.cliente.valueobject.Endereco;
import smartLegalApi.domain.shared.valueobject.Email;
import smartLegalApi.domain.shared.valueobject.Telefone;

/**
 * Caso de Uso: Criar Novo Cliente
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CriarClienteUseCase {
    
    private final ClienteRepository clienteRepository;
    
    @Transactional
    public Cliente executar(String nomeCompleto, String cpfCnpjStr, String emailStr, String telefoneStr, String enderecoStr) {
        log.info("Iniciando criação de cliente: {}", nomeCompleto);
        
        // Criar Value Objects
        CpfCnpj cpfCnpj = new CpfCnpj(cpfCnpjStr);
        Email email = emailStr != null && !emailStr.isBlank() ? new Email(emailStr) : null;
        Telefone telefone = telefoneStr != null && !telefoneStr.isBlank() ? new Telefone(telefoneStr) : null;
        Endereco endereco = enderecoStr != null && !enderecoStr.isBlank() ? new Endereco(enderecoStr) : null;
        
        // Validar unicidade
        if (clienteRepository.existsByCpfCnpj(cpfCnpj.toString())) {
            throw new CpfCnpjJaCadastradoException(cpfCnpj.formatado());
        }
        
        // Criar entidade
        Cliente cliente = new Cliente(nomeCompleto, cpfCnpj, email, telefone, endereco);
        
        // Salvar
        Cliente clienteSalvo = clienteRepository.save(cliente);
        
        log.info("Cliente criado com sucesso. ID: {}, CPF/CNPJ: {}", clienteSalvo.getId(), clienteSalvo.getCpfCnpj().formatado());
        
        return clienteSalvo;
    }
}

