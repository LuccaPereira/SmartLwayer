package smartLegalApi.infrastructure.security.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import smartLegalApi.infrastructure.security.config.JwtProperties;
import smartLegalApi.infrastructure.security.filter.JwtAccessDeniedHandler;
import smartLegalApi.infrastructure.security.filter.JwtAuthenticationEntryPoint;
import smartLegalApi.infrastructure.security.filter.JwtAuthenticationFilter;
import smartLegalApi.infrastructure.security.service.CustomUserDetailsService;
import smartLegalApi.infrastructure.security.service.JwtService;

import java.util.Arrays;
import java.util.List;

/**
 * Configuração completa de Segurança com JWT
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final CustomUserDetailsService userDetailsService;
    
    /**
     * Endpoints públicos (não requerem autenticação)
     */
    private static final String[] PUBLIC_ENDPOINTS = {
        // Autenticação
        "/api/auth/**",
        // Swagger/OpenAPI
        "/swagger-ui/**",
        "/v3/api-docs/**",
        "/swagger-ui.html",
        "/swagger-resources/**",
        "/webjars/**",
        // Actuator (health check público)
        "/actuator/health",
        "/actuator/info"
    };
    
    /**
     * Endpoints protegidos (requerem autenticação)
     */
    private static final String[] PROTECTED_ENDPOINTS = {
        "/api/advogados/**",
        "/api/clientes/**",
        "/api/processos/**",
        "/api/peticoes/**"
    };
    
    @Bean
    public SecurityFilterChain securityFilterChain(
        HttpSecurity http,
        JwtAuthenticationFilter jwtAuthenticationFilter
    ) throws Exception {
        http
            // Desabilita CSRF (não necessário para API REST stateless)
            .csrf(AbstractHttpConfigurer::disable)
            
            // Configuração de CORS
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            
            // Session Management: Stateless (não mantém sessão)
            .sessionManagement(session -> 
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            
            // Configuração de autorização de requests
            .authorizeHttpRequests(auth -> auth
                // Endpoints públicos
                .requestMatchers(PUBLIC_ENDPOINTS).permitAll()
                
                // Endpoints protegidos
                .requestMatchers(PROTECTED_ENDPOINTS).authenticated()
                
                // Qualquer outra requisição precisa de autenticação
                .anyRequest().authenticated()
            )
            
            // Exception handlers
            .exceptionHandling(exception -> exception
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)
            )
            
            // Authentication provider
            .authenticationProvider(authenticationProvider())
            
            // Adiciona o filtro JWT na cadeia de segurança
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        
        return http.build();
    }
    
    /**
     * Configuração de CORS
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        
        // Origens permitidas (em produção, especificar domínios específicos)
        configuration.setAllowedOriginPatterns(List.of("*"));
        
        // Métodos HTTP permitidos
        configuration.setAllowedMethods(Arrays.asList(
            "GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"
        ));
        
        // Headers permitidos
        configuration.setAllowedHeaders(Arrays.asList(
            "Authorization",
            "Content-Type",
            "X-Requested-With",
            "Accept",
            "Origin",
            "Access-Control-Request-Method",
            "Access-Control-Request-Headers"
        ));
        
        // Headers expostos
        configuration.setExposedHeaders(Arrays.asList(
            "Authorization",
            "Content-Disposition"
        ));
        
        // Permite credenciais
        configuration.setAllowCredentials(true);
        
        // Tempo máximo de cache da configuração CORS
        configuration.setMaxAge(3600L);
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        
        return source;
    }
    
    /**
     * Authentication Provider
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }
    
    /**
     * Authentication Manager
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
    
    /**
     * Password Encoder (BCrypt)
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    /**
     * JWT Authentication Filter Bean
     * Criado como @Bean para evitar duplo registro (não usar @Component!)
     */
    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(
        JwtService jwtService,
        CustomUserDetailsService userDetailsService,
        JwtProperties jwtProperties
    ) {
        return new JwtAuthenticationFilter(jwtService, userDetailsService, jwtProperties);
    }
    
    /**
     * CRÍTICO: Desabilita o auto-registro do JwtAuthenticationFilter como Servlet Filter
     * Sem isso, o Tomcat tenta registrar o filtro automaticamente, causando NullPointerException
     */
    @Bean
    public FilterRegistrationBean<JwtAuthenticationFilter> jwtFilterRegistration(
        JwtAuthenticationFilter jwtAuthenticationFilter
    ) {
        FilterRegistrationBean<JwtAuthenticationFilter> registrationBean = 
            new FilterRegistrationBean<>(jwtAuthenticationFilter);
        
        // DESABILITA o auto-registro! O filtro será gerenciado APENAS pelo Spring Security
        registrationBean.setEnabled(false);
        
        return registrationBean;
    }
}

