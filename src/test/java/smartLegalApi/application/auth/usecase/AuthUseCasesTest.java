package smartLegalApi.application.auth.usecase;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import smartLegalApi.application.advogado.usecase.CriarAdvogadoUseCase;
import smartLegalApi.application.auth.usecase.LoginUseCase.LoginResult;
import smartLegalApi.application.auth.usecase.RegistrarAdvogadoUseCase.RegistroRequest;
import smartLegalApi.application.auth.usecase.RegistrarAdvogadoUseCase.RegistroResult;
import smartLegalApi.application.auth.usecase.AlterarSenhaUseCase.AlterarSenhaRequest;
import smartLegalApi.application.auth.usecase.ResetarSenhaUseCase.ResetarSenhaRequest;
import smartLegalApi.domain.advogado.entity.Advogado;
import smartLegalApi.domain.advogado.exception.AdvogadoNaoEncontradoException;
import smartLegalApi.domain.advogado.repository.AdvogadoRepository;
import smartLegalApi.domain.shared.exception.BusinessRuleException;
import smartLegalApi.domain.shared.valueobject.CPF;
import smartLegalApi.domain.shared.valueobject.Email;
import smartLegalApi.domain.shared.valueobject.OAB;
import smartLegalApi.domain.shared.valueobject.Telefone;
import smartLegalApi.infrastructure.security.model.UserPrincipal;
import smartLegalApi.infrastructure.security.service.CustomUserDetailsService;
import smartLegalApi.infrastructure.security.service.JwtService;
import smartLegalApi.infrastructure.security.service.PasswordEncoderService;
import smartLegalApi.infrastructure.security.service.PasswordResetTokenService;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Testes unitários para Use Cases de Auth
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("Auth Use Cases - Testes")
class AuthUseCasesTest {
    
    @Mock
    private AdvogadoRepository advogadoRepository;
    
    @Mock
    private PasswordEncoderService passwordEncoder;
    
    @Mock
    private AuthenticationManager authenticationManager;
    
    @Mock
    private JwtService jwtService;
    
    @Mock
    private PasswordResetTokenService resetTokenService;
    
    @Mock
    private CustomUserDetailsService userDetailsService;
    
    @Mock
    private CriarAdvogadoUseCase criarAdvogadoUseCase;
    
    @Mock
    private Authentication authentication;
    
    private Advogado advogadoMock;
    private UserPrincipal userPrincipalMock;
    
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
        
        userPrincipalMock = new UserPrincipal(
            1L,
            "joao@example.com",
            "$2a$10$hashedpassword",
            "João da Silva",
            "ADVOGADO",
            true
        );
    }
    
    // =================================================================
    // 1. LOGIN USE CASE
    // =================================================================
    
    @Test
    @DisplayName("[Login] Deve fazer login com sucesso")
    void login_deveFazerLoginComSucesso() {
        // Arrange
        LoginUseCase useCase = new LoginUseCase(authenticationManager, jwtService);
        
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
            .thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(userPrincipalMock);
        when(jwtService.generateToken(anyString(), anyLong(), anyString())).thenReturn("access-token");
        when(jwtService.generateRefreshToken(anyString(), anyLong())).thenReturn("refresh-token");
        
        // Act
        LoginResult result = useCase.executar("joao@example.com", "Senha123");
        
        // Assert
        assertThat(result).isNotNull();
        assertThat(result.accessToken()).isEqualTo("access-token");
        assertThat(result.refreshToken()).isEqualTo("refresh-token");
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
    }
    
    @Test
    @DisplayName("[Login] Deve lançar exceção com credenciais inválidas")
    void login_deveLancarExcecaoComCredenciaisInvalidas() {
        // Arrange
        LoginUseCase useCase = new LoginUseCase(authenticationManager, jwtService);
        
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
            .thenThrow(new BadCredentialsException("Credenciais inválidas"));
        
        // Act & Assert
        assertThatThrownBy(() -> useCase.executar("joao@example.com", "SenhaErrada"))
            .isInstanceOf(BadCredentialsException.class);
    }
    
    // =================================================================
    // 2. REGISTRAR ADVOGADO USE CASE
    // =================================================================
    
    @Test
    @DisplayName("[Registrar] Deve registrar advogado com sucesso")
    void registrar_deveRegistrarAdvogadoComSucesso() {
        // Arrange
        RegistrarAdvogadoUseCase useCase = new RegistrarAdvogadoUseCase(criarAdvogadoUseCase);
        
        when(criarAdvogadoUseCase.executar(anyString(), anyString(), anyString(), anyString(), anyString(), anyString()))
            .thenReturn(advogadoMock);
        
        RegistroRequest request = new RegistroRequest(
            "123456", "SP", "João da Silva", "11144477735",
            "joao@example.com", "11987654321", "Senha123", "Senha123"
        );
        
        // Act
        RegistroResult result = useCase.executar(request);
        
        // Assert
        assertThat(result).isNotNull();
        assertThat(result.email()).isEqualTo("joao@example.com");
        verify(criarAdvogadoUseCase).executar(anyString(), anyString(), anyString(), anyString(), anyString(), anyString());
    }
    
    @Test
    @DisplayName("[Registrar] Deve lançar exceção se senhas não coincidem")
    void registrar_deveLancarExcecaoSeSenhasNaoCoincidem() {
        // Arrange
        RegistrarAdvogadoUseCase useCase = new RegistrarAdvogadoUseCase(criarAdvogadoUseCase);
        
        RegistroRequest request = new RegistroRequest(
            "123456", "SP", "João", "11144477735",
            "joao@example.com", null, "Senha123", "SenhaErrada"
        );
        
        // Act & Assert
        assertThatThrownBy(() -> useCase.executar(request))
            .isInstanceOf(BusinessRuleException.class)
            .hasMessageContaining("senhas não coincidem");
    }
    
    // =================================================================
    // 3. REFRESH TOKEN USE CASE
    // =================================================================
    
    @Test
    @DisplayName("[RefreshToken] Deve renovar access token mantendo refresh token")
    void refreshToken_deveRenovarTokensComSucesso() {
        // Arrange
        RefreshTokenUseCase useCase = new RefreshTokenUseCase(jwtService, userDetailsService);
        
        when(jwtService.validateToken(anyString())).thenReturn(true);
        when(jwtService.extractEmail(anyString())).thenReturn("joao@example.com");
        when(jwtService.extractUserId(anyString())).thenReturn(1L);
        when(userDetailsService.loadUserByUsername(anyString())).thenReturn(userPrincipalMock);
        when(jwtService.generateToken(anyString(), anyLong(), anyString())).thenReturn("new-access-token");
        
        // Act
        RefreshTokenUseCase.RefreshResult result = useCase.executar("old-refresh-token");
        
        // Assert
        assertThat(result).isNotNull();
        assertThat(result.accessToken()).isEqualTo("new-access-token");
        assertThat(result.refreshToken()).isEqualTo("old-refresh-token"); // Mantém o mesmo refresh token
    }
    
    @Test
    @DisplayName("[RefreshToken] Deve lançar exceção se token inválido")
    void refreshToken_deveLancarExcecaoSeTokenInvalido() {
        // Arrange
        RefreshTokenUseCase useCase = new RefreshTokenUseCase(jwtService, userDetailsService);
        when(jwtService.validateToken(anyString())).thenReturn(false);
        
        // Act & Assert
        assertThatThrownBy(() -> useCase.executar("invalid-token"))
            .isInstanceOf(BadCredentialsException.class);
    }
    
    // =================================================================
    // 4. ALTERAR SENHA USE CASE
    // =================================================================
    
    @Test
    @DisplayName("[AlterarSenha] Deve alterar senha com sucesso")
    void alterarSenha_deveAlterarSenhaComSucesso() {
        // Arrange
        AlterarSenhaUseCase useCase = new AlterarSenhaUseCase(advogadoRepository, passwordEncoder);
        
        when(advogadoRepository.findById(1L)).thenReturn(Optional.of(advogadoMock));
        // Primeira chamada: valida senha atual (retorna true)
        // Segunda chamada: verifica se nova senha é diferente da antiga (retorna false)
        when(passwordEncoder.matches(anyString(), anyString()))
            .thenReturn(true)  // Senha atual correta
            .thenReturn(false); // Nova senha diferente da atual
        when(passwordEncoder.encode(anyString())).thenReturn("$2a$10$newhash");
        when(advogadoRepository.save(any(Advogado.class))).thenReturn(advogadoMock);
        
        AlterarSenhaRequest request = new AlterarSenhaRequest("SenhaAntiga123", "NovaSenha123", "NovaSenha123");
        
        // Act
        useCase.executar(1L, request);
        
        // Assert
        verify(advogadoRepository).save(any(Advogado.class));
    }
    
    @Test
    @DisplayName("[AlterarSenha] Deve lançar exceção se senha atual incorreta")
    void alterarSenha_deveLancarExcecaoSeSenhaAtualIncorreta() {
        // Arrange
        AlterarSenhaUseCase useCase = new AlterarSenhaUseCase(advogadoRepository, passwordEncoder);
        
        when(advogadoRepository.findById(1L)).thenReturn(Optional.of(advogadoMock));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(false);
        
        AlterarSenhaRequest request = new AlterarSenhaRequest("SenhaErrada", "NovaSenha123", "NovaSenha123");
        
        // Act & Assert
        assertThatThrownBy(() -> useCase.executar(1L, request))
            .isInstanceOf(BadCredentialsException.class)
            .hasMessageContaining("Senha atual incorreta");
    }
    
    // =================================================================
    // 5. ESQUECEU SENHA USE CASE
    // =================================================================
    
    @Test
    @DisplayName("[EsqueceuSenha] Deve gerar token de reset")
    void esqueceuSenha_deveGerarTokenDeReset() {
        // Arrange
        EsqueceuSenhaUseCase useCase = new EsqueceuSenhaUseCase(advogadoRepository, resetTokenService);
        
        when(advogadoRepository.findByEmail(anyString())).thenReturn(Optional.of(advogadoMock));
        when(resetTokenService.generateResetToken(anyLong(), anyString())).thenReturn("reset-token-123");
        
        // Act
        EsqueceuSenhaUseCase.EsqueceuSenhaResult result = useCase.executar("joao@example.com");
        
        // Assert
        assertThat(result).isNotNull();
        assertThat(result.resetToken()).isEqualTo("reset-token-123");
        verify(resetTokenService).generateResetToken(1L, "joao@example.com");
    }
    
    @Test
    @DisplayName("[EsqueceuSenha] Deve lançar exceção se email não encontrado")
    void esqueceuSenha_deveLancarExcecaoSeEmailNaoEncontrado() {
        // Arrange
        EsqueceuSenhaUseCase useCase = new EsqueceuSenhaUseCase(advogadoRepository, resetTokenService);
        when(advogadoRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        
        // Act & Assert
        assertThatThrownBy(() -> useCase.executar("inexistente@example.com"))
            .isInstanceOf(AdvogadoNaoEncontradoException.class);
    }
    
    // =================================================================
    // 6. RESETAR SENHA USE CASE
    // =================================================================
    
    @Test
    @DisplayName("[ResetarSenha] Deve resetar senha com token válido")
    void resetarSenha_deveResetarSenhaComTokenValido() {
        // Arrange
        ResetarSenhaUseCase useCase = new ResetarSenhaUseCase(advogadoRepository, resetTokenService, passwordEncoder);
        
        when(resetTokenService.validateAndGetUserId(anyString())).thenReturn(1L);
        when(advogadoRepository.findById(1L)).thenReturn(Optional.of(advogadoMock));
        when(passwordEncoder.encode(anyString())).thenReturn("$2a$10$newhash");
        when(advogadoRepository.save(any(Advogado.class))).thenReturn(advogadoMock);
        doNothing().when(resetTokenService).invalidateToken(anyString());
        
        ResetarSenhaRequest request = new ResetarSenhaRequest("reset-token-123", "NovaSenha123", "NovaSenha123");
        
        // Act
        useCase.executar(request);
        
        // Assert
        verify(advogadoRepository).save(any(Advogado.class));
        verify(resetTokenService).invalidateToken(anyString());
    }
    
    @Test
    @DisplayName("[ResetarSenha] Deve lançar exceção se token inválido")
    void resetarSenha_deveLancarExcecaoSeTokenInvalido() {
        // Arrange
        ResetarSenhaUseCase useCase = new ResetarSenhaUseCase(advogadoRepository, resetTokenService, passwordEncoder);
        when(resetTokenService.validateAndGetUserId(anyString())).thenReturn(null);
        
        ResetarSenhaRequest request = new ResetarSenhaRequest("invalid-token", "NovaSenha123", "NovaSenha123");
        
        // Act & Assert
        assertThatThrownBy(() -> useCase.executar(request))
            .isInstanceOf(BadCredentialsException.class);
    }
}

