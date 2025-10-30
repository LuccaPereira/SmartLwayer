package smartLegalApi.presentation.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import smartLegalApi.application.auth.dto.request.*;
import smartLegalApi.application.auth.dto.response.*;
import smartLegalApi.application.auth.usecase.*;
import smartLegalApi.infrastructure.security.model.UserPrincipal;

/**
 * Controller: Autenticação e gerenciamento de conta
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Autenticação", description = "Endpoints de autenticação e gerenciamento de conta")
public class AuthController {
    
    private final LoginUseCase loginUseCase;
    private final RefreshTokenUseCase refreshTokenUseCase;
    private final ValidateTokenUseCase validateTokenUseCase;
    private final RegistrarAdvogadoUseCase registrarAdvogadoUseCase;
    private final AlterarSenhaUseCase alterarSenhaUseCase;
    private final EsqueceuSenhaUseCase esqueceuSenhaUseCase;
    private final ResetarSenhaUseCase resetarSenhaUseCase;
    
    // ============================================================
    // ENDPOINTS PÚBLICOS (não requerem autenticação)
    // ============================================================
    
    @PostMapping("/registro")
    @Operation(summary = "Registrar novo advogado", description = "Cria uma nova conta de advogado no sistema")
    public ResponseEntity<RegistroResponse> registrar(@Valid @RequestBody RegistroRequest request) {
        log.info("POST /api/auth/registro - Email: {}", request.getEmail());
        
        var result = registrarAdvogadoUseCase.executar(
            new RegistrarAdvogadoUseCase.RegistroRequest(
                request.getOabNumero(),
                request.getOabUf(),
                request.getNome(),
                request.getCpf(),
                request.getEmail(),
                request.getTelefone(),
                request.getSenha(),
                request.getConfirmarSenha()
            )
        );
        
        RegistroResponse response = RegistroResponse.builder()
            .id(result.id())
            .email(result.email())
            .nome(result.nome())
            .oab(result.oab())
            .mensagem("Advogado registrado com sucesso. Faça login para acessar o sistema.")
            .build();
        
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    @PostMapping("/login")
    @Operation(summary = "Fazer login", description = "Autentica um advogado e retorna tokens JWT")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        log.info("POST /api/auth/login - Email: {}", request.getEmail());
        
        var result = loginUseCase.executar(request.getEmail(), request.getSenha());
        
        LoginResponse response = LoginResponse.builder()
            .accessToken(result.accessToken())
            .refreshToken(result.refreshToken())
            .tokenType("Bearer")
            .expiresIn(86400L) // 24 horas em segundos
            .userId(result.userId())
            .email(result.email())
            .nome(result.nome())
            .role(result.role())
            .build();
        
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/refresh")
    @Operation(summary = "Renovar token", description = "Gera novo access token usando refresh token")
    public ResponseEntity<RefreshTokenResponse> refresh(@Valid @RequestBody RefreshTokenRequest request) {
        log.info("POST /api/auth/refresh");
        
        var result = refreshTokenUseCase.executar(request.getRefreshToken());
        
        RefreshTokenResponse response = RefreshTokenResponse.builder()
            .accessToken(result.accessToken())
            .refreshToken(result.refreshToken())
            .tokenType("Bearer")
            .expiresIn(86400L)
            .build();
        
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/validate")
    @Operation(summary = "Validar token", description = "Valida se um token JWT é válido")
    public ResponseEntity<TokenValidationResponse> validate(@RequestParam String token) {
        log.info("POST /api/auth/validate");
        
        var result = validateTokenUseCase.executar(token);
        
        TokenValidationResponse response = TokenValidationResponse.builder()
            .valid(result.valid())
            .userId(result.userId())
            .email(result.email())
            .role(result.role())
            .build();
        
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/esqueceu-senha")
    @Operation(summary = "Esqueceu senha", description = "Solicita reset de senha - envia email com token")
    public ResponseEntity<MensagemResponse> esqueceuSenha(@Valid @RequestBody EsqueceuSenhaRequest request) {
        log.info("POST /api/auth/esqueceu-senha - Email: {}", request.getEmail());
        
        var result = esqueceuSenhaUseCase.executar(request.getEmail());
        
        // Em produção, não retornar o token! Deve ser enviado apenas por email
        MensagemResponse response = MensagemResponse.builder()
            .mensagem(result.mensagem())
            .dados(result.resetToken()) // REMOVER EM PRODUÇÃO!
            .build();
        
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/resetar-senha")
    @Operation(summary = "Resetar senha", description = "Reseta a senha usando token recebido por email")
    public ResponseEntity<MensagemResponse> resetarSenha(@Valid @RequestBody ResetarSenhaRequest request) {
        log.info("POST /api/auth/resetar-senha");
        
        resetarSenhaUseCase.executar(
            new ResetarSenhaUseCase.ResetarSenhaRequest(
                request.getToken(),
                request.getNovaSenha(),
                request.getConfirmarNovaSenha()
            )
        );
        
        MensagemResponse response = new MensagemResponse("Senha resetada com sucesso. Faça login com sua nova senha.");
        
        return ResponseEntity.ok(response);
    }
    
    // ============================================================
    // ENDPOINTS PROTEGIDOS (requerem autenticação)
    // ============================================================
    
    @PostMapping("/alterar-senha")
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(summary = "Alterar senha", description = "Altera a senha do usuário autenticado")
    public ResponseEntity<MensagemResponse> alterarSenha(
        @AuthenticationPrincipal UserPrincipal userPrincipal,
        @Valid @RequestBody AlterarSenhaRequest request
    ) {
        log.info("POST /api/auth/alterar-senha - Advogado ID: {}", userPrincipal.getId());
        
        alterarSenhaUseCase.executar(
            userPrincipal.getId(),
            new AlterarSenhaUseCase.AlterarSenhaRequest(
                request.getSenhaAtual(),
                request.getNovaSenha(),
                request.getConfirmarNovaSenha()
            )
        );
        
        MensagemResponse response = new MensagemResponse("Senha alterada com sucesso");
        
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/me")
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(summary = "Obter dados do usuário autenticado", description = "Retorna informações do usuário logado")
    public ResponseEntity<UserInfoResponse> me(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        log.info("GET /api/auth/me - Advogado ID: {}", userPrincipal.getId());
        
        UserInfoResponse response = UserInfoResponse.builder()
            .id(userPrincipal.getId())
            .email(userPrincipal.getEmail())
            .nome(userPrincipal.getNome())
            .role(userPrincipal.getRole())
            .build();
        
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/logout")
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(summary = "Fazer logout", description = "Logout do usuário (invalidar tokens no cliente)")
    public ResponseEntity<MensagemResponse> logout(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        log.info("POST /api/auth/logout - Advogado ID: {}", userPrincipal.getId());
        
        // JWT é stateless - o logout é feito removendo o token no cliente
        // Em uma implementação mais robusta, poderia adicionar o token a uma blacklist no Redis
        
        MensagemResponse response = new MensagemResponse("Logout realizado com sucesso. Remova o token do cliente.");
        
        return ResponseEntity.ok(response);
    }
}
