package smartLegalApi.domain.shared.valueobject;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import smartLegalApi.domain.shared.exception.DomainException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Testes unitários para Value Object CPF
 */
@DisplayName("CPF - Testes")
class CPFTest {
    
    @ParameterizedTest
    @ValueSource(strings = {
        "11144477735",
        "111.444.777-35",
        "52998224725",
        "529.982.247-25"
    })
    @DisplayName("Deve criar CPF válido")
    void deveCriarCPFValido(String cpfStr) {
        // Act
        CPF cpf = new CPF(cpfStr);
        
        // Assert
        assertThat(cpf).isNotNull();
        assertThat(cpf.getValor()).hasSize(11);
        assertThat(cpf.getValor()).containsOnlyDigits();
    }
    
    @Test
    @DisplayName("Deve formatar CPF corretamente")
    void deveFormatarCPFCorretamente() {
        // Arrange & Act
        CPF cpf = new CPF("11144477735");
        
        // Assert
        assertThat(cpf.formatado()).isEqualTo("111.444.777-35");
    }
    
    @ParameterizedTest
    @ValueSource(strings = {
        "00000000000",
        "11111111111",
        "22222222222",
        "33333333333",
        "44444444444",
        "55555555555",
        "66666666666",
        "77777777777",
        "88888888888",
        "99999999999"
    })
    @DisplayName("Deve rejeitar CPFs com dígitos repetidos")
    void deveRejeitarCPFsComDigitosRepetidos(String cpfInvalido) {
        // Act & Assert
        assertThatThrownBy(() -> new CPF(cpfInvalido))
            .isInstanceOf(DomainException.class)
            .hasMessageContaining("CPF inválido");
    }
    
    @ParameterizedTest
    @ValueSource(strings = {
        "12345678901",  // dígitos verificadores errados
        "11144477725"   // penúltimo dígito errado
    })
    @DisplayName("Deve rejeitar CPFs com dígitos verificadores inválidos")
    void deveRejeitarCPFsComDigitosVerificadoresInvalidos(String cpfInvalido) {
        // Act & Assert
        assertThatThrownBy(() -> new CPF(cpfInvalido))
            .isInstanceOf(DomainException.class)
            .hasMessageContaining("CPF inválido");
    }
    
    @ParameterizedTest
    @ValueSource(strings = {
        "",
        "   ",
        "123",
        "123456",
        "1234567890",      // 10 dígitos
        "123456789012",    // 12 dígitos
        "12345678901234"   // 14 dígitos
    })
    @DisplayName("Deve rejeitar CPF com tamanho inválido")
    void deveRejeitarCPFComTamanhoInvalido(String cpfInvalido) {
        // Act & Assert
        assertThatThrownBy(() -> new CPF(cpfInvalido))
            .isInstanceOf(DomainException.class);
    }
    
    @Test
    @DisplayName("Deve rejeitar CPF nulo")
    void deveRejeitarCPFNulo() {
        // Act & Assert
        assertThatThrownBy(() -> new CPF(null))
            .isInstanceOf(DomainException.class)
            .hasMessageContaining("CPF não pode ser nulo");
    }
    
    @Test
    @DisplayName("Deve limpar caracteres especiais do CPF")
    void deveLimparCaracteresEspeciais() {
        // Act
        CPF cpf1 = new CPF("111.444.777-35");
        CPF cpf2 = new CPF("111 444 777 35");
        CPF cpf3 = new CPF("111-444-777-35");
        
        // Assert
        assertThat(cpf1.getValor()).isEqualTo("11144477735");
        assertThat(cpf2.getValor()).isEqualTo("11144477735");
        assertThat(cpf3.getValor()).isEqualTo("11144477735");
    }
    
    @Test
    @DisplayName("Dois CPFs iguais devem ser equals")
    void doisCPFsIguaisDevemSerEquals() {
        // Arrange
        CPF cpf1 = new CPF("11144477735");
        CPF cpf2 = new CPF("111.444.777-35");
        
        // Assert
        assertThat(cpf1).isEqualTo(cpf2);
        assertThat(cpf1.hashCode()).isEqualTo(cpf2.hashCode());
    }
    
    @Test
    @DisplayName("Dois CPFs diferentes não devem ser equals")
    void doisCPFsDiferentesNaoDevemSerEquals() {
        // Arrange
        CPF cpf1 = new CPF("11144477735");
        CPF cpf2 = new CPF("52998224725");
        
        // Assert
        assertThat(cpf1).isNotEqualTo(cpf2);
    }
}

