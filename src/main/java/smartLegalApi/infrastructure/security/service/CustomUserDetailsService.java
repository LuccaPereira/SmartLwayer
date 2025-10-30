package smartLegalApi.infrastructure.security.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import smartLegalApi.domain.advogado.entity.Advogado;
import smartLegalApi.domain.advogado.repository.AdvogadoRepository;
import smartLegalApi.infrastructure.security.model.UserPrincipal;

/**
 * Serviço customizado para carregar dados do usuário
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {
    
    private final AdvogadoRepository advogadoRepository;
    
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        log.debug("Carregando usuário por email: {}", email);
        
        Advogado advogado = advogadoRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com email: " + email));
        
        return UserPrincipal.builder()
            .id(advogado.getId())
            .email(advogado.getEmail().getValor())
            .senha(advogado.getSenha())
            .nome(advogado.getNome())
            .role("ADVOGADO")
            .ativo(advogado.isAtivo())
            .build();
    }
    
    /**
     * Carrega usuário por ID
     */
    public UserDetails loadUserById(Long id) {
        log.debug("Carregando usuário por ID: {}", id);
        
        Advogado advogado = advogadoRepository.findById(id)
            .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com ID: " + id));
        
        return UserPrincipal.builder()
            .id(advogado.getId())
            .email(advogado.getEmail().getValor())
            .senha(advogado.getSenha())
            .nome(advogado.getNome())
            .role("ADVOGADO")
            .ativo(advogado.isAtivo())
            .build();
    }
}

