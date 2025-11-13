package smartLegalApi.application.advogado.usecase;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import smartLegalApi.domain.advogado.entity.Advogado;
import smartLegalApi.domain.advogado.exception.AdvogadoNaoEncontradoException;
import smartLegalApi.domain.advogado.exception.EmailJaCadastradoException;
import smartLegalApi.domain.advogado.exception.OABJaCadastradaException;
import smartLegalApi.domain.advogado.repository.AdvogadoRepository;
import smartLegalApi.domain.shared.valueobject.CPF;
import smartLegalApi.domain.shared.valueobject.Email;
import smartLegalApi.domain.shared.valueobject.OAB;
import smartLegalApi.domain.shared.valueobject.Telefone;
import smartLegalApi.infrastructure.security.service.PasswordEncoderService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Testes unitários para TODOS os Use Cases do módulo Advogado
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("Advogado Use Cases - Testes Completos")
class AdvogadoUseCasesTest {
    
    @Mock
    private AdvogadoRepository advogadoRepository;
    
    @Mock
    private PasswordEncoderService passwordEncoder;
    
    private Advogado advogadoMock;
    
    @BeforeEach
    void setUp() {
        advogadoMock = Advogado.builder()
            .id(1L)
            .oab(new OAB("SP123456"))
            .nome("João da Silva")
            .cpf(new CPF("11144477735"))
            .email(new Email("joao@example.com"))
            .telefone(new Telefone("11987654321"))
            .senhaHash("$2a$10$hashedpassword")
            .ativo(true)
            .build();
    }
    
    // =================================================================
    // 1. CRIAR ADVOGADO USE CASE
    // =================================================================
    
    @Test
    @DisplayName("[CriarAdvogado] Deve criar advogado com sucesso")
    void criarAdvogado_deveCriarComSucesso() {
        // Arrange
        CriarAdvogadoUseCase useCase = new CriarAdvogadoUseCase(advogadoRepository, passwordEncoder);
        
        when(advogadoRepository.existsByOAB(anyString())).thenReturn(false);
        when(advogadoRepository.existsByEmail(anyString())).thenReturn(false);
        when(passwordEncoder.encode(anyString())).thenReturn("$2a$10$hash");
        when(advogadoRepository.save(any(Advogado.class))).thenReturn(advogadoMock);
        
        // Act
        Advogado result = useCase.executar("João", "SP123456", "11144477735", "joao@example.com", "11987654321", "Senha123");
        
        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getNome()).isEqualTo("João da Silva");
        verify(advogadoRepository).save(any(Advogado.class));
    }
    
    @Test
    @DisplayName("[CriarAdvogado] Deve lançar exceção se OAB já cadastrada")
    void criarAdvogado_deveLancarExcecaoSeOABJaCadastrada() {
        // Arrange
        CriarAdvogadoUseCase useCase = new CriarAdvogadoUseCase(advogadoRepository, passwordEncoder);
        when(advogadoRepository.existsByOAB(anyString())).thenReturn(true);
        
        // Act & Assert
        assertThatThrownBy(() -> useCase.executar("João", "SP123456", "11144477735", "joao@example.com", null, "Senha123"))
            .isInstanceOf(OABJaCadastradaException.class);
        
        verify(advogadoRepository, never()).save(any(Advogado.class));
    }
    
    @Test
    @DisplayName("[CriarAdvogado] Deve lançar exceção se email já cadastrado")
    void criarAdvogado_deveLancarExcecaoSeEmailJaCadastrado() {
        // Arrange
        CriarAdvogadoUseCase useCase = new CriarAdvogadoUseCase(advogadoRepository, passwordEncoder);
        when(advogadoRepository.existsByOAB(anyString())).thenReturn(false);
        when(advogadoRepository.existsByEmail(anyString())).thenReturn(true);
        
        // Act & Assert
        assertThatThrownBy(() -> useCase.executar("João", "SP123456", "11144477735", "joao@example.com", null, "Senha123"))
            .isInstanceOf(EmailJaCadastradoException.class);
    }
    
    // =================================================================
    // 2. BUSCAR ADVOGADO POR ID USE CASE
    // =================================================================
    
    @Test
    @DisplayName("[BuscarPorId] Deve buscar advogado por ID")
    void buscarPorId_deveBuscarComSucesso() {
        // Arrange
        BuscarAdvogadoPorIdUseCase useCase = new BuscarAdvogadoPorIdUseCase(advogadoRepository);
        when(advogadoRepository.findById(1L)).thenReturn(Optional.of(advogadoMock));
        
        // Act
        Advogado result = useCase.executar(1L);
        
        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getNome()).isEqualTo("João da Silva");
    }
    
    @Test
    @DisplayName("[BuscarPorId] Deve lançar exceção se não encontrado")
    void buscarPorId_deveLancarExcecaoSeNaoEncontrado() {
        // Arrange
        BuscarAdvogadoPorIdUseCase useCase = new BuscarAdvogadoPorIdUseCase(advogadoRepository);
        when(advogadoRepository.findById(999L)).thenReturn(Optional.empty());
        
        // Act & Assert
        assertThatThrownBy(() -> useCase.executar(999L))
            .isInstanceOf(AdvogadoNaoEncontradoException.class);
    }
    
    // =================================================================
    // 3. BUSCAR ADVOGADO POR OAB USE CASE
    // =================================================================
    
    @Test
    @DisplayName("[BuscarPorOAB] Deve buscar advogado por OAB")
    void buscarPorOAB_deveBuscarComSucesso() {
        // Arrange
        BuscarAdvogadoPorOABUseCase useCase = new BuscarAdvogadoPorOABUseCase(advogadoRepository);
        when(advogadoRepository.findByOAB(anyString())).thenReturn(Optional.of(advogadoMock));
        
        // Act
        Advogado result = useCase.executar("SP123456");
        
        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getOab().getValor()).isEqualTo("SP123456");
    }
    
    // =================================================================
    // 4. LISTAR ADVOGADOS USE CASE
    // =================================================================
    
    @Test
    @DisplayName("[ListarAdvogados] Deve listar apenas ativos")
    void listarAdvogados_deveListarApenasAtivos() {
        // Arrange
        ListarAdvogadosUseCase useCase = new ListarAdvogadosUseCase(advogadoRepository);
        when(advogadoRepository.findAllAtivos()).thenReturn(Arrays.asList(advogadoMock));
        
        // Act
        List<Advogado> result = useCase.executar(true);
        
        // Assert
        assertThat(result).hasSize(1);
        verify(advogadoRepository).findAllAtivos();
        verify(advogadoRepository, never()).findAll();
    }
    
    @Test
    @DisplayName("[ListarAdvogados] Deve listar todos")
    void listarAdvogados_deveListarTodos() {
        // Arrange
        ListarAdvogadosUseCase useCase = new ListarAdvogadosUseCase(advogadoRepository);
        Advogado advogadoInativo = Advogado.builder()
            .id(2L)
            .oab(new OAB("RJ654321"))
            .nome("Maria Inativa")
            .cpf(new CPF("52998224725"))
            .email(new Email("maria@example.com"))
            .ativo(false)
            .build();
        
        when(advogadoRepository.findAll()).thenReturn(Arrays.asList(advogadoMock, advogadoInativo));
        
        // Act
        List<Advogado> result = useCase.executar(false);
        
        // Assert
        assertThat(result).hasSize(2);
        verify(advogadoRepository).findAll();
        verify(advogadoRepository, never()).findAllAtivos();
    }
    
    // =================================================================
    // 5. ATUALIZAR ADVOGADO USE CASE
    // =================================================================
    
    @Test
    @DisplayName("[AtualizarAdvogado] Deve atualizar com sucesso")
    void atualizarAdvogado_deveAtualizarComSucesso() {
        // Arrange
        AtualizarAdvogadoUseCase useCase = new AtualizarAdvogadoUseCase(advogadoRepository);
        when(advogadoRepository.findById(1L)).thenReturn(Optional.of(advogadoMock));
        when(advogadoRepository.existsByEmail(anyString())).thenReturn(false);
        when(advogadoRepository.update(any(Advogado.class))).thenReturn(advogadoMock);
        
        // Act
        Advogado result = useCase.executar(1L, "Novo Nome", "novoemail@example.com", "11999999999");
        
        // Assert
        assertThat(result).isNotNull();
        verify(advogadoRepository).update(any(Advogado.class));
    }
    
    @Test
    @DisplayName("[AtualizarAdvogado] Deve lançar exceção se não encontrado")
    void atualizarAdvogado_deveLancarExcecaoSeNaoEncontrado() {
        // Arrange
        AtualizarAdvogadoUseCase useCase = new AtualizarAdvogadoUseCase(advogadoRepository);
        when(advogadoRepository.findById(999L)).thenReturn(Optional.empty());
        
        // Act & Assert
        assertThatThrownBy(() -> useCase.executar(999L, "Nome", "email@test.com", null))
            .isInstanceOf(AdvogadoNaoEncontradoException.class);
    }
    
    // =================================================================
    // 6. INATIVAR ADVOGADO USE CASE
    // =================================================================
    
    @Test
    @DisplayName("[InativarAdvogado] Deve inativar com sucesso")
    void inativarAdvogado_deveInativarComSucesso() {
        // Arrange
        InativarAdvogadoUseCase useCase = new InativarAdvogadoUseCase(advogadoRepository);
        when(advogadoRepository.findById(1L)).thenReturn(Optional.of(advogadoMock));
        when(advogadoRepository.update(any(Advogado.class))).thenReturn(advogadoMock);
        
        // Act
        useCase.executar(1L);
        
        // Assert
        verify(advogadoRepository).update(any(Advogado.class));
    }
    
    // =================================================================
    // 7. DELETAR ADVOGADO USE CASE
    // =================================================================
    
    @Test
    @DisplayName("[DeletarAdvogado] Deve deletar com sucesso")
    void deletarAdvogado_deveDeletarComSucesso() {
        // Arrange
        DeletarAdvogadoUseCase useCase = new DeletarAdvogadoUseCase(advogadoRepository);
        when(advogadoRepository.findById(1L)).thenReturn(Optional.of(advogadoMock));
        doNothing().when(advogadoRepository).deleteById(1L);
        
        // Act
        useCase.executar(1L);
        
        // Assert
        verify(advogadoRepository).deleteById(1L);
    }
    
    @Test
    @DisplayName("[DeletarAdvogado] Deve lançar exceção se não encontrado")
    void deletarAdvogado_deveLancarExcecaoSeNaoEncontrado() {
        // Arrange
        DeletarAdvogadoUseCase useCase = new DeletarAdvogadoUseCase(advogadoRepository);
        when(advogadoRepository.findById(999L)).thenReturn(Optional.empty());
        
        // Act & Assert
        assertThatThrownBy(() -> useCase.executar(999L))
            .isInstanceOf(AdvogadoNaoEncontradoException.class);
        
        verify(advogadoRepository, never()).deleteById(anyLong());
    }
}

