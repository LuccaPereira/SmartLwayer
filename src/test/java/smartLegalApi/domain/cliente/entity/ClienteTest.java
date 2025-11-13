package smartLegalApi.domain.cliente.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import smartLegalApi.domain.cliente.valueobject.CpfCnpj;
import smartLegalApi.domain.cliente.valueobject.Endereco;
import smartLegalApi.domain.shared.exception.BusinessRuleException;
import smartLegalApi.domain.shared.valueobject.Email;
import smartLegalApi.domain.shared.valueobject.Telefone;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Testes unitários para entidade Cliente (Domain)
 */
@DisplayName("Cliente (Entity) - Testes")
class ClienteTest {
    
    private String nome;
    private CpfCnpj cpfCnpj;
    private Email email;
    private Telefone telefone;
    private Endereco endereco;
    
    @BeforeEach
    void setUp() {
        nome = "Maria da Silva";
        cpfCnpj = new CpfCnpj("11144477735");
        email = new Email("maria@example.com");
        telefone = new Telefone("11987654321");
        endereco = new Endereco("Rua Teste, 123, Centro, São Paulo - SP, 01234-567");
    }
    
    @Test
    @DisplayName("Deve criar cliente válido")
    void deveCriarClienteValido() {
        // Act
        Cliente cliente = new Cliente(nome, cpfCnpj, email, telefone, endereco);
        
        // Assert
        assertThat(cliente).isNotNull();
        assertThat(cliente.getNomeCompleto()).isEqualTo(nome);
        assertThat(cliente.getCpfCnpj()).isEqualTo(cpfCnpj);
        assertThat(cliente.getEmail()).isEqualTo(email);
        assertThat(cliente.getTelefone()).isEqualTo(telefone);
        assertThat(cliente.getEndereco()).isEqualTo(endereco);
        assertThat(cliente.isAtivo()).isTrue();
        assertThat(cliente.getDataCadastro()).isNotNull();
        assertThat(cliente.getDataAtualizacao()).isNotNull();
    }
    
    @Test
    @DisplayName("Deve criar cliente sem telefone e endereço")
    void deveCriarClienteSemTelefoneEEndereco() {
        // Act
        Cliente cliente = new Cliente(nome, cpfCnpj, email, null, null);
        
        // Assert
        assertThat(cliente).isNotNull();
        assertThat(cliente.getTelefone()).isNull();
        assertThat(cliente.getEndereco()).isNull();
    }
    
    @Test
    @DisplayName("Deve lançar exceção se nome for nulo")
    void deveLancarExcecaoSeNomeNulo() {
        // Act & Assert
        assertThatThrownBy(() -> new Cliente(null, cpfCnpj, email, telefone, endereco))
            .isInstanceOf(BusinessRuleException.class)
            .hasMessageContaining("Nome completo é obrigatório");
    }
    
    @Test
    @DisplayName("Deve lançar exceção se nome for muito curto")
    void deveLancarExcecaoSeNomeMuitoCurto() {
        // Act & Assert
        assertThatThrownBy(() -> new Cliente("Jo", cpfCnpj, email, telefone, endereco))
            .isInstanceOf(BusinessRuleException.class)
            .hasMessageContaining("Nome deve ter no mínimo 3 caracteres");
    }
    
    @Test
    @DisplayName("Deve lançar exceção se nome for muito longo")
    void deveLancarExcecaoSeNomeMuitoLongo() {
        // Arrange
        String nomeLongo = "a".repeat(101);
        
        // Act & Assert
        assertThatThrownBy(() -> new Cliente(nomeLongo, cpfCnpj, email, telefone, endereco))
            .isInstanceOf(BusinessRuleException.class)
            .hasMessageContaining("Nome deve ter no máximo 100 caracteres");
    }
    
    @Test
    @DisplayName("Deve lançar exceção se CPF/CNPJ for nulo")
    void deveLancarExcecaoSeCpfCnpjNulo() {
        // Act & Assert
        assertThatThrownBy(() -> new Cliente(nome, null, email, telefone, endereco))
            .isInstanceOf(BusinessRuleException.class)
            .hasMessageContaining("CPF/CNPJ é obrigatório");
    }
    
    @Test
    @DisplayName("Deve atualizar dados do cliente")
    void deveAtualizarDadosDoCliente() {
        // Arrange
        Cliente cliente = new Cliente(nome, cpfCnpj, email, telefone, endereco);
        Email novoEmail = new Email("novoemail@example.com");
        Telefone novoTelefone = new Telefone("11912345678");
        
        // Act
        cliente.atualizar("Novo Nome", novoEmail, novoTelefone, null);
        
        // Assert
        assertThat(cliente.getNomeCompleto()).isEqualTo("Novo Nome");
        assertThat(cliente.getEmail()).isEqualTo(novoEmail);
        assertThat(cliente.getTelefone()).isEqualTo(novoTelefone);
    }
    
    @Test
    @DisplayName("Deve inativar cliente")
    void deveInativarCliente() {
        // Arrange
        Cliente cliente = new Cliente(nome, cpfCnpj, email, telefone, endereco);
        
        // Act
        cliente.inativar();
        
        // Assert
        assertThat(cliente.isAtivo()).isFalse();
    }
    
    @Test
    @DisplayName("Deve lançar exceção ao inativar cliente já inativo")
    void deveLancarExcecaoAoInativarClienteJaInativo() {
        // Arrange
        Cliente cliente = new Cliente(nome, cpfCnpj, email, telefone, endereco);
        cliente.inativar();
        
        // Act & Assert
        assertThatThrownBy(() -> cliente.inativar())
            .isInstanceOf(BusinessRuleException.class)
            .hasMessageContaining("Cliente já está inativo");
    }
    
    @Test
    @DisplayName("Deve ativar cliente")
    void deveAtivarCliente() {
        // Arrange
        Cliente cliente = new Cliente(nome, cpfCnpj, email, telefone, endereco);
        cliente.inativar();
        
        // Act
        cliente.ativar();
        
        // Assert
        assertThat(cliente.isAtivo()).isTrue();
    }
    
    @Test
    @DisplayName("Deve lançar exceção ao ativar cliente já ativo")
    void deveLancarExcecaoAoAtivarClienteJaAtivo() {
        // Arrange
        Cliente cliente = new Cliente(nome, cpfCnpj, email, telefone, endereco);
        
        // Act & Assert
        assertThatThrownBy(() -> cliente.ativar())
            .isInstanceOf(BusinessRuleException.class)
            .hasMessageContaining("Cliente já está ativo");
    }
}

