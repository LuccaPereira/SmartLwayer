package smartLegalApi.domain.shared.valueobject;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import smartLegalApi.domain.shared.exception.DomainException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Testes unitários para Value Object OAB
 */
@DisplayName("OAB - Testes")
class OABTest {
    
    @ParameterizedTest
    @ValueSource(strings = {
        "SP123456",
        "RJ987654",
        "MG456789",
        "RS111222",
        "SP 123456",
        "sp123456",  // deve normalizar para maiúsculas
        "SP-123456"
    })
    @DisplayName("Deve criar OAB válida")
    void deveCriarOABValida(String oabStr) {
        // Act
        OAB oab = new OAB(oabStr);
        
        // Assert
        assertThat(oab).isNotNull();
        assertThat(oab.getValor()).hasSize(8); // UF(2) + 6 dígitos
        assertThat(oab.getValor()).matches("^[A-Z]{2}\\d{6}$");
    }
    
    @Test
    @DisplayName("Deve extrair UF corretamente")
    void deveExtrairUFCorretamente() {
        // Arrange & Act
        OAB oab = new OAB("SP123456");
        
        // Assert
        assertThat(oab.getUF()).isEqualTo("SP");
    }
    
    @Test
    @DisplayName("Deve extrair número corretamente")
    void deveExtrairNumeroCorretamente() {
        // Arrange & Act
        OAB oab = new OAB("SP123456");
        
        // Assert
        assertThat(oab.getNumero()).isEqualTo("123456");
    }
    
    @Test
    @DisplayName("Deve normalizar para maiúsculas")
    void deveNormalizarParaMaiusculas() {
        // Act
        OAB oab = new OAB("sp123456");
        
        // Assert
        assertThat(oab.getValor()).isEqualTo("SP123456");
        assertThat(oab.getUF()).isEqualTo("SP");
    }
    
    @ParameterizedTest
    @ValueSource(strings = {
        "XX123456",  // UF inválida
        "BR123456",  // UF inválida
        "ZZ999999"   // UF inválida
    })
    @DisplayName("Deve rejeitar UF inválida")
    void deveRejeitarUFInvalida(String oabInvalida) {
        // Act & Assert
        assertThatThrownBy(() -> new OAB(oabInvalida))
            .isInstanceOf(DomainException.class)
            .hasMessageContaining("OAB inválido");
    }
    
    @ParameterizedTest
    @ValueSource(strings = {
        "SP12345",    // 5 dígitos
        "SP1234567",  // 7 dígitos
        "SPABC123",   // contém letras no número
        "SP"          // só UF
    })
    @DisplayName("Deve rejeitar OAB com formato inválido")
    void deveRejeitarOABComFormatoInvalido(String oabInvalida) {
        // Act & Assert
        assertThatThrownBy(() -> new OAB(oabInvalida))
            .isInstanceOf(DomainException.class);
    }
    
    @Test
    @DisplayName("Deve rejeitar OAB nula")
    void deveRejeitarOABNula() {
        // Act & Assert
        assertThatThrownBy(() -> new OAB(null))
            .isInstanceOf(DomainException.class)
            .hasMessageContaining("OAB não pode ser nulo");
    }
    
    @Test
    @DisplayName("Deve rejeitar OAB vazia")
    void deveRejeitarOABVazia() {
        // Act & Assert
        assertThatThrownBy(() -> new OAB(""))
            .isInstanceOf(DomainException.class)
            .hasMessageContaining("OAB não pode ser nulo");
    }
    
    @Test
    @DisplayName("Deve limpar caracteres especiais")
    void deveLimparCaracteresEspeciais() {
        // Act
        OAB oab1 = new OAB("SP 123456");
        OAB oab2 = new OAB("SP-123456");
        OAB oab3 = new OAB("SP.123.456");
        
        // Assert
        assertThat(oab1.getValor()).isEqualTo("SP123456");
        assertThat(oab2.getValor()).isEqualTo("SP123456");
        assertThat(oab3.getValor()).isEqualTo("SP123456");
    }
    
    @ParameterizedTest
    @ValueSource(strings = {
        "AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA",
        "MT", "MS", "MG", "PA", "PB", "PR", "PE", "PI", "RJ", "RN",
        "RS", "RO", "RR", "SC", "SP", "SE", "TO"
    })
    @DisplayName("Deve aceitar todas as UFs válidas")
    void deveAceitarTodasUFsValidas(String uf) {
        // Act
        OAB oab = new OAB(uf + "123456");
        
        // Assert
        assertThat(oab.getUF()).isEqualTo(uf);
    }
    
    @Test
    @DisplayName("Duas OABs iguais devem ser equals")
    void duasOABsIguaisDevemSerEquals() {
        // Arrange
        OAB oab1 = new OAB("SP123456");
        OAB oab2 = new OAB("sp 123456");
        
        // Assert
        assertThat(oab1).isEqualTo(oab2);
        assertThat(oab1.hashCode()).isEqualTo(oab2.hashCode());
    }
    
    @Test
    @DisplayName("Duas OABs diferentes não devem ser equals")
    void duasOABsDiferentesNaoDevemSerEquals() {
        // Arrange
        OAB oab1 = new OAB("SP123456");
        OAB oab2 = new OAB("RJ987654");
        
        // Assert
        assertThat(oab1).isNotEqualTo(oab2);
    }
}

