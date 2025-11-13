package smartLegalApi.domain.advogado.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import smartLegalApi.domain.shared.exception.BusinessRuleException;
import smartLegalApi.domain.shared.valueobject.CPF;
import smartLegalApi.domain.shared.valueobject.Email;
import smartLegalApi.domain.shared.valueobject.OAB;
import smartLegalApi.domain.shared.valueobject.Telefone;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Testes unitários para entidade Advogado (Domain)
 */
@DisplayName("Advogado (Entity) - Testes")
class AdvogadoTest {
    
    private OAB oab;
    private String nome;
    private CPF cpf;
    private Email email;
    private Telefone telefone;
    private String senhaHash;
    
    @BeforeEach
    void setUp() {
        oab = new OAB("SP123456");
        nome = "João da Silva";
        cpf = new CPF("11144477735");
        email = new Email("joao@example.com");
        telefone = new Telefone("11987654321");
        senhaHash = "$2a$10$hashedpassword";
    }
    
    @Test
    @DisplayName("Deve criar advogado válido")
    void deveCriarAdvogadoValido() {
        // Act
        Advogado advogado = new Advogado(oab, nome, cpf, email, telefone, senhaHash);
        
        // Assert
        assertThat(advogado).isNotNull();
        assertThat(advogado.getOab()).isEqualTo(oab);
        assertThat(advogado.getNome()).isEqualTo(nome);
        assertThat(advogado.getCpf()).isEqualTo(cpf);
        assertThat(advogado.getEmail()).isEqualTo(email);
        assertThat(advogado.getTelefone()).isEqualTo(telefone);
        assertThat(advogado.getSenhaHash()).isEqualTo(senhaHash);
        assertThat(advogado.isAtivo()).isTrue();
        assertThat(advogado.getDataCadastro()).isNotNull();
        assertThat(advogado.getDataAtualizacao()).isNotNull();
    }
    
    @Test
    @DisplayName("Deve criar advogado sem telefone")
    void deveCriarAdvogadoSemTelefone() {
        // Act
        Advogado advogado = new Advogado(oab, nome, cpf, email, null, senhaHash);
        
        // Assert
        assertThat(advogado).isNotNull();
        assertThat(advogado.getTelefone()).isNull();
    }
    
    @Test
    @DisplayName("Deve lançar exceção se OAB for nula")
    void deveLancarExcecaoSeOABNula() {
        // Act & Assert
        assertThatThrownBy(() -> new Advogado(null, nome, cpf, email, telefone, senhaHash))
            .isInstanceOf(BusinessRuleException.class)
            .hasMessageContaining("OAB é obrigatória");
    }
    
    @Test
    @DisplayName("Deve lançar exceção se nome for nulo")
    void deveLancarExcecaoSeNomeNulo() {
        // Act & Assert
        assertThatThrownBy(() -> new Advogado(oab, null, cpf, email, telefone, senhaHash))
            .isInstanceOf(BusinessRuleException.class)
            .hasMessageContaining("Nome é obrigatório");
    }
    
    @Test
    @DisplayName("Deve lançar exceção se nome for muito curto")
    void deveLancarExcecaoSeNomeMuitoCurto() {
        // Act & Assert
        assertThatThrownBy(() -> new Advogado(oab, "Jo", cpf, email, telefone, senhaHash))
            .isInstanceOf(BusinessRuleException.class)
            .hasMessageContaining("Nome deve ter no mínimo 3 caracteres");
    }
    
    @Test
    @DisplayName("Deve lançar exceção se nome for muito longo")
    void deveLancarExcecaoSeNomeMuitoLongo() {
        // Arrange
        String nomeLongo = "a".repeat(101);
        
        // Act & Assert
        assertThatThrownBy(() -> new Advogado(oab, nomeLongo, cpf, email, telefone, senhaHash))
            .isInstanceOf(BusinessRuleException.class)
            .hasMessageContaining("Nome deve ter no máximo 100 caracteres");
    }
    
    @Test
    @DisplayName("Deve lançar exceção se CPF for nulo")
    void deveLancarExcecaoSeCPFNulo() {
        // Act & Assert
        assertThatThrownBy(() -> new Advogado(oab, nome, null, email, telefone, senhaHash))
            .isInstanceOf(BusinessRuleException.class)
            .hasMessageContaining("CPF é obrigatório");
    }
    
    @Test
    @DisplayName("Deve lançar exceção se email for nulo")
    void deveLancarExcecaoSeEmailNulo() {
        // Act & Assert
        assertThatThrownBy(() -> new Advogado(oab, nome, cpf, null, telefone, senhaHash))
            .isInstanceOf(BusinessRuleException.class)
            .hasMessageContaining("Email é obrigatório");
    }
    
    @Test
    @DisplayName("Deve lançar exceção se senha for nula")
    void deveLancarExcecaoSeSenhaNula() {
        // Act & Assert
        assertThatThrownBy(() -> new Advogado(oab, nome, cpf, email, telefone, null))
            .isInstanceOf(BusinessRuleException.class)
            .hasMessageContaining("Senha é obrigatória");
    }
    
    @Test
    @DisplayName("Deve atualizar dados do advogado")
    void deveAtualizarDadosDoAdvogado() {
        // Arrange
        Advogado advogado = new Advogado(oab, nome, cpf, email, telefone, senhaHash);
        Email novoEmail = new Email("novo@example.com");
        Telefone novoTelefone = new Telefone("11912345678");
        
        // Act
        advogado.atualizar("Novo Nome", novoEmail, novoTelefone);
        
        // Assert
        assertThat(advogado.getNome()).isEqualTo("Novo Nome");
        assertThat(advogado.getEmail()).isEqualTo(novoEmail);
        assertThat(advogado.getTelefone()).isEqualTo(novoTelefone);
    }
    
    @Test
    @DisplayName("Deve inativar advogado")
    void deveInativarAdvogado() {
        // Arrange
        Advogado advogado = new Advogado(oab, nome, cpf, email, telefone, senhaHash);
        
        // Act
        advogado.inativar();
        
        // Assert
        assertThat(advogado.isAtivo()).isFalse();
    }
    
    @Test
    @DisplayName("Deve lançar exceção ao inativar advogado já inativo")
    void deveLancarExcecaoAoInativarAdvogadoJaInativo() {
        // Arrange
        Advogado advogado = new Advogado(oab, nome, cpf, email, telefone, senhaHash);
        advogado.inativar();
        
        // Act & Assert
        assertThatThrownBy(() -> advogado.inativar())
            .isInstanceOf(BusinessRuleException.class)
            .hasMessageContaining("Advogado já está inativo");
    }
    
    @Test
    @DisplayName("Deve ativar advogado")
    void deveAtivarAdvogado() {
        // Arrange
        Advogado advogado = new Advogado(oab, nome, cpf, email, telefone, senhaHash);
        advogado.inativar();
        
        // Act
        advogado.ativar();
        
        // Assert
        assertThat(advogado.isAtivo()).isTrue();
    }
    
    @Test
    @DisplayName("Deve lançar exceção ao ativar advogado já ativo")
    void deveLancarExcecaoAoAtivarAdvogadoJaAtivo() {
        // Arrange
        Advogado advogado = new Advogado(oab, nome, cpf, email, telefone, senhaHash);
        
        // Act & Assert
        assertThatThrownBy(() -> advogado.ativar())
            .isInstanceOf(BusinessRuleException.class)
            .hasMessageContaining("Advogado já está ativo");
    }
}

