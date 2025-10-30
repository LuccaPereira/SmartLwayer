package smartLegalApi.domain.shared.valueobject;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import smartLegalApi.domain.shared.exception.DomainException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Testes unitários para Value Object Email
 */
@DisplayName("Email - Testes")
class EmailTest {
    
    @ParameterizedTest
    @ValueSource(strings = {
        "teste@example.com",
        "usuario.teste@example.com",
        "usuario+tag@example.com",
        "usuario123@example.co.uk",
        "USER@EXAMPLE.COM",
        "user_test@example-domain.com"
    })
    @DisplayName("Deve criar email válido")
    void deveCriarEmailValido(String emailStr) {
        // Act
        Email email = new Email(emailStr);
        
        // Assert
        assertThat(email).isNotNull();
        assertThat(email.getValor()).isNotEmpty();
    }
    
    @Test
    @DisplayName("Deve normalizar email para minúsculas")
    void deveNormalizarEmailParaMinusculas() {
        // Act
        Email email = new Email("TESTE@EXAMPLE.COM");
        
        // Assert
        assertThat(email.getValor()).isEqualTo("teste@example.com");
    }
    
    @ParameterizedTest
    @ValueSource(strings = {
        "invalido",
        "invalido@",
        "@example.com",
        "invalido @example.com",
        "invalido@.com",
        "invalido@example",
        "invalido@example.",
        "invalido..teste@example.com",
        "invalido@exam ple.com"
    })
    @DisplayName("Deve rejeitar emails inválidos")
    void deveRejeitarEmailsInvalidos(String emailInvalido) {
        // Act & Assert
        assertThatThrownBy(() -> new Email(emailInvalido))
            .isInstanceOf(DomainException.class)
            .hasMessageContaining("Email inválido");
    }
    
    @Test
    @DisplayName("Deve rejeitar email nulo")
    void deveRejeitarEmailNulo() {
        // Act & Assert
        assertThatThrownBy(() -> new Email(null))
            .isInstanceOf(DomainException.class)
            .hasMessageContaining("Email não pode ser nulo");
    }
    
    @Test
    @DisplayName("Deve rejeitar email vazio")
    void deveRejeitarEmailVazio() {
        // Act & Assert
        assertThatThrownBy(() -> new Email(""))
            .isInstanceOf(DomainException.class)
            .hasMessageContaining("Email não pode ser nulo");
    }
    
    @Test
    @DisplayName("Deve remover espaços em branco")
    void deveRemoverEspacosEmBranco() {
        // Act
        Email email = new Email("  teste@example.com  ");
        
        // Assert
        assertThat(email.getValor()).isEqualTo("teste@example.com");
    }
    
    @Test
    @DisplayName("Dois emails iguais devem ser equals")
    void doisEmailsIguaisDevemSerEquals() {
        // Arrange
        Email email1 = new Email("teste@example.com");
        Email email2 = new Email("TESTE@EXAMPLE.COM");
        
        // Assert
        assertThat(email1).isEqualTo(email2);
        assertThat(email1.hashCode()).isEqualTo(email2.hashCode());
    }
    
    @Test
    @DisplayName("Dois emails diferentes não devem ser equals")
    void doisEmailsDiferentesNaoDevemSerEquals() {
        // Arrange
        Email email1 = new Email("teste1@example.com");
        Email email2 = new Email("teste2@example.com");
        
        // Assert
        assertThat(email1).isNotEqualTo(email2);
    }
}

