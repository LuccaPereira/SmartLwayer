package smartLegalApi.application.processo.usecase;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import smartLegalApi.domain.advogado.entity.Advogado;
import smartLegalApi.domain.advogado.repository.AdvogadoRepository;
import smartLegalApi.domain.cliente.entity.Cliente;
import smartLegalApi.domain.cliente.repository.ClienteRepository;
import smartLegalApi.domain.processo.entity.Processo;
import smartLegalApi.domain.processo.exception.NumeroProcessoJaCadastradoException;
import smartLegalApi.domain.processo.exception.ProcessoNaoEncontradoException;
import smartLegalApi.domain.processo.repository.ProcessoRepository;
import smartLegalApi.domain.processo.valueobject.NumeroProcesso;
import smartLegalApi.domain.processo.valueobject.StatusProcesso;
import smartLegalApi.domain.shared.exception.NotFoundException;
import smartLegalApi.domain.shared.valueobject.CPF;
import smartLegalApi.domain.shared.valueobject.Email;
import smartLegalApi.domain.shared.valueobject.OAB;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Testes unitários para Use Cases de Processo
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("Processo Use Cases - Testes")
class ProcessoUseCasesTest {
    
    @Mock
    private ProcessoRepository processoRepository;
    
    @Mock
    private AdvogadoRepository advogadoRepository;
    
    @Mock
    private ClienteRepository clienteRepository;
    
    private Processo processoMock;
    private Advogado advogadoMock;
    private Cliente clienteMock;
    
    @BeforeEach
    void setUp() {
        advogadoMock = Advogado.builder()
            .id(1L)
            .nome("Dr. João")
            .oab(new OAB("SP123456"))
            .cpf(new CPF("11144477735"))
            .email(new Email("joao@example.com"))
            .ativo(true)
            .build();
        
        clienteMock = Cliente.builder()
            .id(1L)
            .build();
        
        processoMock = Processo.builder()
            .id(1L)
            .numeroProcesso(new NumeroProcesso("0000000-00.0000.0.00.0000"))
            .titulo("Ação de Cobrança")
            .descricao("Cobrança de valores")
            .status(StatusProcesso.ATIVO)
            .dataAbertura(LocalDate.now())
            .idAdvogado(1L)
            .idCliente(1L)
            .build();
    }
    
    // =================================================================
    // 1. CRIAR PROCESSO USE CASE
    // =================================================================
    
    @Test
    @DisplayName("[CriarProcesso] Deve criar processo com sucesso")
    void criarProcesso_deveCriarComSucesso() {
        // Arrange
        CriarProcessoUseCase useCase = new CriarProcessoUseCase(processoRepository, advogadoRepository, clienteRepository);
        
        when(advogadoRepository.findById(1L)).thenReturn(Optional.of(advogadoMock));
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(clienteMock));
        when(processoRepository.existsByNumeroProcesso(anyString())).thenReturn(false);
        when(processoRepository.save(any(Processo.class))).thenReturn(processoMock);
        
        // Act
        Processo result = useCase.executar("0000000-00.0000.0.00.0000", "Ação de Cobrança", "Descrição", 1L, 1L);
        
        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getTitulo()).isEqualTo("Ação de Cobrança");
        verify(processoRepository).save(any(Processo.class));
    }
    
    @Test
    @DisplayName("[CriarProcesso] Deve lançar exceção se número já existe")
    void criarProcesso_deveLancarExcecaoSeNumeroJaExiste() {
        // Arrange
        CriarProcessoUseCase useCase = new CriarProcessoUseCase(processoRepository, advogadoRepository, clienteRepository);
        
        when(advogadoRepository.findById(1L)).thenReturn(Optional.of(advogadoMock));
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(clienteMock));
        when(processoRepository.existsByNumeroProcesso(anyString())).thenReturn(true);
        
        // Act & Assert
        assertThatThrownBy(() -> useCase.executar("0000000-00.0000.0.00.0000", "Título", "Descrição", 1L, 1L))
            .isInstanceOf(NumeroProcessoJaCadastradoException.class);
    }
    
    @Test
    @DisplayName("[CriarProcesso] Deve lançar exceção se advogado não existe")
    void criarProcesso_deveLancarExcecaoSeAdvogadoNaoExiste() {
        // Arrange
        CriarProcessoUseCase useCase = new CriarProcessoUseCase(processoRepository, advogadoRepository, clienteRepository);
        
        when(advogadoRepository.findById(999L)).thenReturn(Optional.empty());
        
        // Act & Assert
        assertThatThrownBy(() -> useCase.executar("0000000-00.0000.0.00.0000", "Título", "Descrição", 999L, 1L))
            .isInstanceOf(NotFoundException.class)
            .hasMessageContaining("Advogado não encontrado");
    }
    
    @Test
    @DisplayName("[CriarProcesso] Deve lançar exceção se cliente não existe")
    void criarProcesso_deveLancarExcecaoSeClienteNaoExiste() {
        // Arrange
        CriarProcessoUseCase useCase = new CriarProcessoUseCase(processoRepository, advogadoRepository, clienteRepository);
        
        when(advogadoRepository.findById(1L)).thenReturn(Optional.of(advogadoMock));
        when(clienteRepository.findById(999L)).thenReturn(Optional.empty());
        
        // Act & Assert
        assertThatThrownBy(() -> useCase.executar("0000000-00.0000.0.00.0000", "Título", "Descrição", 1L, 999L))
            .isInstanceOf(NotFoundException.class)
            .hasMessageContaining("Cliente não encontrado");
    }
    
    // =================================================================
    // 2. BUSCAR PROCESSO POR ID USE CASE
    // =================================================================
    
    @Test
    @DisplayName("[BuscarPorId] Deve buscar processo por ID")
    void buscarPorId_deveBuscarComSucesso() {
        // Arrange
        BuscarProcessoPorIdUseCase useCase = new BuscarProcessoPorIdUseCase(processoRepository);
        when(processoRepository.findById(1L)).thenReturn(Optional.of(processoMock));
        
        // Act
        Processo result = useCase.executar(1L);
        
        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
    }
    
    @Test
    @DisplayName("[BuscarPorId] Deve lançar exceção se não encontrado")
    void buscarPorId_deveLancarExcecaoSeNaoEncontrado() {
        // Arrange
        BuscarProcessoPorIdUseCase useCase = new BuscarProcessoPorIdUseCase(processoRepository);
        when(processoRepository.findById(999L)).thenReturn(Optional.empty());
        
        // Act & Assert
        assertThatThrownBy(() -> useCase.executar(999L))
            .isInstanceOf(ProcessoNaoEncontradoException.class);
    }
    
    // =================================================================
    // 3. BUSCAR PROCESSO POR NÚMERO USE CASE
    // =================================================================
    
    @Test
    @DisplayName("[BuscarPorNumero] Deve buscar processo por número")
    void buscarPorNumero_deveBuscarComSucesso() {
        // Arrange
        BuscarProcessoPorNumeroUseCase useCase = new BuscarProcessoPorNumeroUseCase(processoRepository);
        when(processoRepository.findByNumeroProcesso(anyString())).thenReturn(Optional.of(processoMock));
        
        // Act
        Processo result = useCase.executar("0000000-00.0000.0.00.0000");
        
        // Assert
        assertThat(result).isNotNull();
    }
    
    // =================================================================
    // 4. LISTAR PROCESSOS POR ADVOGADO USE CASE
    // =================================================================
    
    @Test
    @DisplayName("[ListarPorAdvogado] Deve listar processos do advogado")
    void listarPorAdvogado_deveListarComSucesso() {
        // Arrange
        ListarProcessosPorAdvogadoUseCase useCase = new ListarProcessosPorAdvogadoUseCase(processoRepository);
        
        when(processoRepository.findByAdvogado(1L)).thenReturn(Arrays.asList(processoMock));
        
        // Act
        List<Processo> result = useCase.executar(1L, null);
        
        // Assert
        assertThat(result).hasSize(1);
    }
    
    @Test
    @DisplayName("[ListarPorAdvogado] Deve filtrar por status")
    void listarPorAdvogado_deveFiltrarPorStatus() {
        // Arrange
        ListarProcessosPorAdvogadoUseCase useCase = new ListarProcessosPorAdvogadoUseCase(processoRepository);
        
        when(processoRepository.findByAdvogadoAndStatus(1L, StatusProcesso.ATIVO))
            .thenReturn(Arrays.asList(processoMock));
        
        // Act
        List<Processo> result = useCase.executar(1L, StatusProcesso.ATIVO);
        
        // Assert
        assertThat(result).hasSize(1);
        verify(processoRepository).findByAdvogadoAndStatus(1L, StatusProcesso.ATIVO);
    }
    
    // =================================================================
    // 5. LISTAR PROCESSOS POR CLIENTE USE CASE
    // =================================================================
    
    @Test
    @DisplayName("[ListarPorCliente] Deve listar processos do cliente")
    void listarPorCliente_deveListarComSucesso() {
        // Arrange
        ListarProcessosPorClienteUseCase useCase = new ListarProcessosPorClienteUseCase(processoRepository);
        
        when(processoRepository.findByCliente(1L)).thenReturn(Arrays.asList(processoMock));
        
        // Act
        List<Processo> result = useCase.executar(1L);
        
        // Assert
        assertThat(result).hasSize(1);
    }
    
    // =================================================================
    // 6. ATUALIZAR PROCESSO USE CASE
    // =================================================================
    
    @Test
    @DisplayName("[AtualizarProcesso] Deve atualizar com sucesso")
    void atualizarProcesso_deveAtualizarComSucesso() {
        // Arrange
        AtualizarProcessoUseCase useCase = new AtualizarProcessoUseCase(processoRepository);
        when(processoRepository.findById(1L)).thenReturn(Optional.of(processoMock));
        when(processoRepository.update(any(Processo.class))).thenReturn(processoMock);
        
        // Act
        Processo result = useCase.executar(1L, "Novo Título", "Nova Descrição");
        
        // Assert
        assertThat(result).isNotNull();
        verify(processoRepository).update(any(Processo.class));
    }
    
    // =================================================================
    // 7. ENCERRAR PROCESSO USE CASE
    // =================================================================
    
    @Test
    @DisplayName("[EncerrarProcesso] Deve encerrar processo")
    void encerrarProcesso_deveEncerrarComSucesso() {
        // Arrange
        EncerrarProcessoUseCase useCase = new EncerrarProcessoUseCase(processoRepository);
        when(processoRepository.findById(1L)).thenReturn(Optional.of(processoMock));
        when(processoRepository.update(any(Processo.class))).thenReturn(processoMock);
        
        // Act
        Processo result = useCase.executar(1L);
        
        // Assert
        assertThat(result).isNotNull();
        verify(processoRepository).update(any(Processo.class));
    }
    
    // =================================================================
    // 8. DELETAR PROCESSO USE CASE
    // =================================================================
    
    @Test
    @DisplayName("[DeletarProcesso] Deve deletar com sucesso")
    void deletarProcesso_deveDeletarComSucesso() {
        // Arrange
        DeletarProcessoUseCase useCase = new DeletarProcessoUseCase(processoRepository);
        when(processoRepository.findById(1L)).thenReturn(Optional.of(processoMock));
        doNothing().when(processoRepository).deleteById(1L);
        
        // Act
        useCase.executar(1L);
        
        // Assert
        verify(processoRepository).deleteById(1L);
    }
    
}

