package smartLegalApi.domain.shared.valueobject;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import smartLegalApi.domain.shared.exception.DomainException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Testes unitários para Value Object Telefone
 */
@DisplayName("Telefone - Testes")
class TelefoneTest {
    
    @ParameterizedTest
    @ValueSource(strings = {
        "11987654321",      // Celular com 9 dígitos
        "1134567890",       // Fixo com 8 dígitos
        "(11)98765-4321",   // Formatado celular
        "(11)3456-7890",    // Formatado fixo
        "11 98765-4321",
        "11-98765-4321"
    })
    @DisplayName("Deve criar telefone válido")
    void deveCriarTelefoneValido(String telefoneStr) {
        // Act
        Telefone telefone = new Telefone(telefoneStr);
        
        // Assert
        assertThat(telefone).isNotNull();
        assertThat(telefone.getValor()).containsOnlyDigits();
        assertThat(telefone.getValor().length()).isBetween(10, 11);
    }
    
    @Test
    @DisplayName("Deve formatar telefone celular corretamente")
    void deveFormatarTelefoneCelularCorretamente() {
        // Arrange & Act
        Telefone telefone = new Telefone("11987654321");
        
        // Assert
        assertThat(telefone.formatado()).isEqualTo("(11) 98765-4321");
    }
    
    @Test
    @DisplayName("Deve formatar telefone fixo corretamente")
    void deveFormatarTelefoneFixoCorretamente() {
        // Arrange & Act
        Telefone telefone = new Telefone("1134567890");
        
        // Assert
        assertThat(telefone.formatado()).isEqualTo("(11) 3456-7890");
    }
    
    @ParameterizedTest
    @ValueSource(strings = {
        "123",
        "123456",
        "123456789",       // 9 dígitos (faltando DDD)
        "123456789012",    // 12 dígitos
        "00000000000",     // Zeros
        "11111111111"      // Dígitos repetidos
    })
    @DisplayName("Deve rejeitar telefone com tamanho inválido ou padrão suspeito")
    void deveRejeitarTelefoneInvalido(String telefoneInvalido) {
        // Act & Assert
        assertThatThrownBy(() -> new Telefone(telefoneInvalido))
            .isInstanceOf(DomainException.class);
    }
    
    @Test
    @DisplayName("Deve rejeitar telefone nulo")
    void deveRejeitarTelefoneNulo() {
        // Act & Assert
        assertThatThrownBy(() -> new Telefone(null))
            .isInstanceOf(DomainException.class)
            .hasMessageContaining("Telefone não pode ser nulo");
    }
    
    @Test
    @DisplayName("Deve rejeitar telefone vazio")
    void deveRejeitarTelefoneVazio() {
        // Act & Assert
        assertThatThrownBy(() -> new Telefone(""))
            .isInstanceOf(DomainException.class)
            .hasMessageContaining("Telefone não pode ser nulo");
    }
    
    @Test
    @DisplayName("Deve limpar caracteres especiais")
    void deveLimparCaracteresEspeciais() {
        // Act
        Telefone tel1 = new Telefone("(11) 98765-4321");
        Telefone tel2 = new Telefone("11 98765 4321");
        Telefone tel3 = new Telefone("11-98765-4321");
        
        // Assert
        assertThat(tel1.getValor()).isEqualTo("11987654321");
        assertThat(tel2.getValor()).isEqualTo("11987654321");
        assertThat(tel3.getValor()).isEqualTo("11987654321");
    }
    
    @Test
    @DisplayName("Dois telefones iguais devem ser equals")
    void doisTelefonesIguaisDevemSerEquals() {
        // Arrange
        Telefone tel1 = new Telefone("11987654321");
        Telefone tel2 = new Telefone("(11) 98765-4321");
        
        // Assert
        assertThat(tel1).isEqualTo(tel2);
        assertThat(tel1.hashCode()).isEqualTo(tel2.hashCode());
    }
    
    @Test
    @DisplayName("Dois telefones diferentes não devem ser equals")
    void doisTelefonesDiferentesNaoDevemSerEquals() {
        // Arrange
        Telefone tel1 = new Telefone("11987654321");
        Telefone tel2 = new Telefone("11912345678");
        
        // Assert
        assertThat(tel1).isNotEqualTo(tel2);
    }
}

