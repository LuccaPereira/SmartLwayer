package smartLegalApi.application.cliente.usecase;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import smartLegalApi.domain.cliente.entity.Cliente;
import smartLegalApi.domain.cliente.exception.ClienteNaoEncontradoException;
import smartLegalApi.domain.cliente.exception.CpfCnpjJaCadastradoException;
import smartLegalApi.domain.cliente.repository.ClienteRepository;
import smartLegalApi.domain.cliente.valueobject.CpfCnpj;
import smartLegalApi.domain.cliente.valueobject.Endereco;
import smartLegalApi.domain.shared.valueobject.Email;
import smartLegalApi.domain.shared.valueobject.Telefone;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Testes unitários para TODOS os Use Cases do módulo Cliente
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("Cliente Use Cases - Testes Completos")
class ClienteUseCasesTest {
    
    @Mock
    private ClienteRepository clienteRepository;
    
    private Cliente clienteMock;
    
    @BeforeEach
    void setUp() {
        clienteMock = Cliente.builder()
            .id(1L)
            .nomeCompleto("Maria da Silva")
            .cpfCnpj(new CpfCnpj("11144477735"))
            .email(new Email("maria@example.com"))
            .telefone(new Telefone("11987654321"))
            .endereco(new Endereco("Rua Teste, 123"))
            .ativo(true)
            .build();
    }
    
    // =================================================================
    // 1. CRIAR CLIENTE USE CASE
    // =================================================================
    
    @Test
    @DisplayName("[CriarCliente] Deve criar cliente com CPF")
    void criarCliente_deveCriarComCPF() {
        // Arrange
        CriarClienteUseCase useCase = new CriarClienteUseCase(clienteRepository);
        when(clienteRepository.existsByCpfCnpj(anyString())).thenReturn(false);
        when(clienteRepository.save(any(Cliente.class))).thenReturn(clienteMock);
        
        // Act
        Cliente result = useCase.executar("Maria da Silva", "11144477735", "maria@example.com", "11987654321", "Rua Teste, 123");
        
        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getNomeCompleto()).isEqualTo("Maria da Silva");
        verify(clienteRepository).save(any(Cliente.class));
    }
    
    @Test
    @DisplayName("[CriarCliente] Deve criar cliente com CNPJ")
    void criarCliente_deveCriarComCNPJ() {
        // Arrange
        CriarClienteUseCase useCase = new CriarClienteUseCase(clienteRepository);
        when(clienteRepository.existsByCpfCnpj(anyString())).thenReturn(false);
        when(clienteRepository.save(any(Cliente.class))).thenReturn(clienteMock);
        
        // Act
        Cliente result = useCase.executar("Empresa XYZ", "11222333000181", "empresa@example.com", null, null);
        
        // Assert
        assertThat(result).isNotNull();
        verify(clienteRepository).save(any(Cliente.class));
    }
    
    @Test
    @DisplayName("[CriarCliente] Deve lançar exceção se CPF/CNPJ já cadastrado")
    void criarCliente_deveLancarExcecaoSeCpfCnpjJaCadastrado() {
        // Arrange
        CriarClienteUseCase useCase = new CriarClienteUseCase(clienteRepository);
        when(clienteRepository.existsByCpfCnpj(anyString())).thenReturn(true);
        
        // Act & Assert
        assertThatThrownBy(() -> useCase.executar("Maria", "11144477735", "maria@example.com", null, null))
            .isInstanceOf(CpfCnpjJaCadastradoException.class);
        
        verify(clienteRepository, never()).save(any(Cliente.class));
    }
    
    // =================================================================
    // 2. BUSCAR CLIENTE POR ID USE CASE
    // =================================================================
    
    @Test
    @DisplayName("[BuscarPorId] Deve buscar cliente por ID")
    void buscarPorId_deveBuscarComSucesso() {
        // Arrange
        BuscarClientePorIdUseCase useCase = new BuscarClientePorIdUseCase(clienteRepository);
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(clienteMock));
        
        // Act
        Cliente result = useCase.executar(1L);
        
        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
    }
    
    @Test
    @DisplayName("[BuscarPorId] Deve lançar exceção se não encontrado")
    void buscarPorId_deveLancarExcecaoSeNaoEncontrado() {
        // Arrange
        BuscarClientePorIdUseCase useCase = new BuscarClientePorIdUseCase(clienteRepository);
        when(clienteRepository.findById(999L)).thenReturn(Optional.empty());
        
        // Act & Assert
        assertThatThrownBy(() -> useCase.executar(999L))
            .isInstanceOf(ClienteNaoEncontradoException.class);
    }
    
    // =================================================================
    // 3. BUSCAR CLIENTE POR CPF/CNPJ USE CASE
    // =================================================================
    
    @Test
    @DisplayName("[BuscarPorCpfCnpj] Deve buscar cliente por CPF/CNPJ")
    void buscarPorCpfCnpj_deveBuscarComSucesso() {
        // Arrange
        BuscarClientePorCpfCnpjUseCase useCase = new BuscarClientePorCpfCnpjUseCase(clienteRepository);
        when(clienteRepository.findByCpfCnpj(anyString())).thenReturn(Optional.of(clienteMock));
        
        // Act
        Cliente result = useCase.executar("11144477735");
        
        // Assert
        assertThat(result).isNotNull();
    }
    
    // =================================================================
    // 4. LISTAR CLIENTES USE CASE
    // =================================================================
    
    @Test
    @DisplayName("[ListarClientes] Deve listar todos os clientes")
    void listarClientes_deveListarTodos() {
        // Arrange
        ListarClientesUseCase useCase = new ListarClientesUseCase(clienteRepository);
        Cliente cliente2 = Cliente.builder()
            .id(2L)
            .nomeCompleto("João Cliente")
            .cpfCnpj(new CpfCnpj("52998224725"))
            .ativo(true)
            .build();
        
        when(clienteRepository.findAll()).thenReturn(Arrays.asList(clienteMock, cliente2));
        
        // Act
        List<Cliente> result = useCase.executar(false);
        
        // Assert
        assertThat(result).hasSize(2);
        assertThat(result).contains(clienteMock, cliente2);
    }
    
    @Test
    @DisplayName("[ListarClientes] Deve retornar lista vazia se não houver clientes")
    void listarClientes_deveRetornarListaVazia() {
        // Arrange
        ListarClientesUseCase useCase = new ListarClientesUseCase(clienteRepository);
        when(clienteRepository.findAll()).thenReturn(Arrays.asList());
        
        // Act
        List<Cliente> result = useCase.executar(false);
        
        // Assert
        assertThat(result).isEmpty();
    }
    
    // =================================================================
    // 5. ATUALIZAR CLIENTE USE CASE
    // =================================================================
    
    @Test
    @DisplayName("[AtualizarCliente] Deve atualizar com sucesso")
    void atualizarCliente_deveAtualizarComSucesso() {
        // Arrange
        AtualizarClienteUseCase useCase = new AtualizarClienteUseCase(clienteRepository);
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(clienteMock));
        when(clienteRepository.update(any(Cliente.class))).thenReturn(clienteMock);
        
        // Act
        Cliente result = useCase.executar(1L, "Novo Nome", "novoemail@example.com", "11999999999", "Rua Nova, 456");
        
        // Assert
        assertThat(result).isNotNull();
        verify(clienteRepository).update(any(Cliente.class));
    }
    
    @Test
    @DisplayName("[AtualizarCliente] Deve lançar exceção se não encontrado")
    void atualizarCliente_deveLancarExcecaoSeNaoEncontrado() {
        // Arrange
        AtualizarClienteUseCase useCase = new AtualizarClienteUseCase(clienteRepository);
        when(clienteRepository.findById(999L)).thenReturn(Optional.empty());
        
        // Act & Assert
        assertThatThrownBy(() -> useCase.executar(999L, "Nome", "email@test.com", null, null))
            .isInstanceOf(ClienteNaoEncontradoException.class);
    }
    
    // =================================================================
    // 6. INATIVAR CLIENTE USE CASE
    // =================================================================
    
    @Test
    @DisplayName("[InativarCliente] Deve inativar com sucesso")
    void inativarCliente_deveInativarComSucesso() {
        // Arrange
        InativarClienteUseCase useCase = new InativarClienteUseCase(clienteRepository);
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(clienteMock));
        when(clienteRepository.update(any(Cliente.class))).thenReturn(clienteMock);
        
        // Act
        useCase.executar(1L);
        
        // Assert
        verify(clienteRepository).update(any(Cliente.class));
    }
    
    // =================================================================
    // 7. DELETAR CLIENTE USE CASE
    // =================================================================
    
    @Test
    @DisplayName("[DeletarCliente] Deve deletar com sucesso")
    void deletarCliente_deveDeletarComSucesso() {
        // Arrange
        DeletarClienteUseCase useCase = new DeletarClienteUseCase(clienteRepository);
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(clienteMock));
        doNothing().when(clienteRepository).deleteById(1L);
        
        // Act
        useCase.executar(1L);
        
        // Assert
        verify(clienteRepository).deleteById(1L);
    }
    
    @Test
    @DisplayName("[DeletarCliente] Deve lançar exceção se não encontrado")
    void deletarCliente_deveLancarExcecaoSeNaoEncontrado() {
        // Arrange
        DeletarClienteUseCase useCase = new DeletarClienteUseCase(clienteRepository);
        when(clienteRepository.findById(999L)).thenReturn(Optional.empty());
        
        // Act & Assert
        assertThatThrownBy(() -> useCase.executar(999L))
            .isInstanceOf(ClienteNaoEncontradoException.class);
        
        verify(clienteRepository, never()).deleteById(anyLong());
    }
}

