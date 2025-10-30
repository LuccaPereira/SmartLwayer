package smartLegalApi.domain.processo.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import smartLegalApi.domain.processo.valueobject.NumeroProcesso;
import smartLegalApi.domain.processo.valueobject.StatusProcesso;
import smartLegalApi.domain.shared.exception.BusinessRuleException;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Testes unitários para entidade Processo (Domain)
 */
@DisplayName("Processo (Entity) - Testes")
class ProcessoTest {
    
    private NumeroProcesso numeroProcesso;
    private String titulo;
    private String descricao;
    private LocalDate dataAbertura;
    private Long idAdvogado;
    private Long idCliente;
    
    @BeforeEach
    void setUp() {
        numeroProcesso = new NumeroProcesso("0000000-00.0000.0.00.0000");
        titulo = "Ação de Cobrança";
        descricao = "Processo de cobrança de valores";
        dataAbertura = LocalDate.now();
        idAdvogado = 1L;
        idCliente = 1L;
    }
    
    @Test
    @DisplayName("Deve criar processo válido")
    void deveCriarProcessoValido() {
        // Act
        Processo processo = new Processo(numeroProcesso, titulo, descricao, dataAbertura, idAdvogado, idCliente);
        
        // Assert
        assertThat(processo).isNotNull();
        assertThat(processo.getNumeroProcesso()).isEqualTo(numeroProcesso);
        assertThat(processo.getTitulo()).isEqualTo(titulo);
        assertThat(processo.getDescricao()).isEqualTo(descricao);
        assertThat(processo.getStatus()).isEqualTo(StatusProcesso.ATIVO);
        assertThat(processo.getDataAbertura()).isEqualTo(dataAbertura);
        assertThat(processo.getIdAdvogado()).isEqualTo(idAdvogado);
        assertThat(processo.getIdCliente()).isEqualTo(idCliente);
        assertThat(processo.getDataCadastro()).isNotNull();
        assertThat(processo.getDataAtualizacao()).isNotNull();
    }
    
    @Test
    @DisplayName("Deve criar processo usando método estático")
    void deveCriarProcessoUsandoMetodoEstatico() {
        // Act
        Processo processo = Processo.criar(numeroProcesso, titulo, descricao, idAdvogado, idCliente);
        
        // Assert
        assertThat(processo).isNotNull();
        assertThat(processo.getStatus()).isEqualTo(StatusProcesso.ATIVO);
        assertThat(processo.getDataAbertura()).isEqualTo(LocalDate.now());
    }
    
    @Test
    @DisplayName("Deve lançar exceção se número do processo for nulo")
    void deveLancarExcecaoSeNumeroProcessoNulo() {
        // Act & Assert
        assertThatThrownBy(() -> new Processo(null, titulo, descricao, dataAbertura, idAdvogado, idCliente))
            .isInstanceOf(BusinessRuleException.class)
            .hasMessageContaining("Número do processo é obrigatório");
    }
    
    @Test
    @DisplayName("Deve lançar exceção se título for nulo")
    void deveLancarExcecaoSeTituloNulo() {
        // Act & Assert
        assertThatThrownBy(() -> new Processo(numeroProcesso, null, descricao, dataAbertura, idAdvogado, idCliente))
            .isInstanceOf(BusinessRuleException.class)
            .hasMessageContaining("Título do processo é obrigatório");
    }
    
    @Test
    @DisplayName("Deve lançar exceção se título for muito longo")
    void deveLancarExcecaoSeTituloMuitoLongo() {
        // Arrange
        String tituloLongo = "a".repeat(151);
        
        // Act & Assert
        assertThatThrownBy(() -> new Processo(numeroProcesso, tituloLongo, descricao, dataAbertura, idAdvogado, idCliente))
            .isInstanceOf(BusinessRuleException.class)
            .hasMessageContaining("Título deve ter no máximo 150 caracteres");
    }
    
    @Test
    @DisplayName("Deve lançar exceção se data de abertura for futura")
    void deveLancarExcecaoSeDataAberturaFutura() {
        // Arrange
        LocalDate dataFutura = LocalDate.now().plusDays(1);
        
        // Act & Assert
        assertThatThrownBy(() -> new Processo(numeroProcesso, titulo, descricao, dataFutura, idAdvogado, idCliente))
            .isInstanceOf(BusinessRuleException.class)
            .hasMessageContaining("Data de abertura não pode ser futura");
    }
    
    @Test
    @DisplayName("Deve lançar exceção se advogado for nulo")
    void deveLancarExcecaoSeAdvogadoNulo() {
        // Act & Assert
        assertThatThrownBy(() -> new Processo(numeroProcesso, titulo, descricao, dataAbertura, null, idCliente))
            .isInstanceOf(BusinessRuleException.class)
            .hasMessageContaining("Advogado responsável é obrigatório");
    }
    
    @Test
    @DisplayName("Deve lançar exceção se cliente for nulo")
    void deveLancarExcecaoSeClienteNulo() {
        // Act & Assert
        assertThatThrownBy(() -> new Processo(numeroProcesso, titulo, descricao, dataAbertura, idAdvogado, null))
            .isInstanceOf(BusinessRuleException.class)
            .hasMessageContaining("Cliente é obrigatório");
    }
    
    @Test
    @DisplayName("Deve atualizar dados do processo")
    void deveAtualizarDadosDoProcesso() {
        // Arrange
        Processo processo = new Processo(numeroProcesso, titulo, descricao, dataAbertura, idAdvogado, idCliente);
        
        // Act
        processo.atualizar("Novo Título", "Nova Descrição");
        
        // Assert
        assertThat(processo.getTitulo()).isEqualTo("Novo Título");
        assertThat(processo.getDescricao()).isEqualTo("Nova Descrição");
    }
    
    @Test
    @DisplayName("Deve encerrar processo")
    void deveEncerrarProcesso() {
        // Arrange
        Processo processo = new Processo(numeroProcesso, titulo, descricao, dataAbertura, idAdvogado, idCliente);
        
        // Act
        processo.encerrar();
        
        // Assert
        assertThat(processo.getStatus()).isEqualTo(StatusProcesso.ARQUIVADO);
        assertThat(processo.getDataEncerramento()).isEqualTo(LocalDate.now());
    }
    
    @Test
    @DisplayName("Deve lançar exceção ao encerrar processo já encerrado")
    void deveLancarExcecaoAoEncerrarProcessoJaEncerrado() {
        // Arrange
        Processo processo = new Processo(numeroProcesso, titulo, descricao, dataAbertura, idAdvogado, idCliente);
        processo.encerrar();
        
        // Act & Assert
        assertThatThrownBy(() -> processo.encerrar())
            .isInstanceOf(BusinessRuleException.class)
            .hasMessageContaining("já está encerrado");
    }
    
    @Test
    @DisplayName("Deve alterar status do processo")
    void deveAlterarStatusDoProcesso() {
        // Arrange
        Processo processo = new Processo(numeroProcesso, titulo, descricao, dataAbertura, idAdvogado, idCliente);
        
        // Act
        processo.alterarStatus(StatusProcesso.SUSPENSO);
        
        // Assert
        assertThat(processo.getStatus()).isEqualTo(StatusProcesso.SUSPENSO);
    }
    
    @Test
    @DisplayName("Deve registrar data de encerramento ao finalizar processo")
    void deveRegistrarDataEncerramentoAoFinalizarProcesso() {
        // Arrange
        Processo processo = new Processo(numeroProcesso, titulo, descricao, dataAbertura, idAdvogado, idCliente);
        
        // Act
        processo.alterarStatus(StatusProcesso.FINALIZADO);
        
        // Assert
        assertThat(processo.getDataEncerramento()).isEqualTo(LocalDate.now());
    }
}

