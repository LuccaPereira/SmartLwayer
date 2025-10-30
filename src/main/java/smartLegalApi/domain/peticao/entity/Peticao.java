package smartLegalApi.domain.peticao.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import smartLegalApi.domain.peticao.valueobject.StatusPeticao;
import smartLegalApi.domain.peticao.valueobject.TipoPeticao;
import smartLegalApi.domain.shared.exception.DomainException;

import java.time.LocalDateTime;

/**
 * Entidade de domínio: Petição
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Peticao {
    
    private Long id;
    private Long idProcesso;
    private Long idAdvogado;
    private TipoPeticao tipo;
    private String titulo;
    private String conteudo;
    private String conteudoGeradoIA;
    private String promptUtilizado;
    private StatusPeticao status;
    private String caminhoDocumento;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;
    private LocalDateTime dataProtocolo;
    
    /**
     * Cria uma nova petição em rascunho
     */
    public static Peticao criar(Long idProcesso, Long idAdvogado, TipoPeticao tipo, String titulo) {
        validarCamposObrigatorios(idProcesso, idAdvogado, tipo, titulo);
        
        return Peticao.builder()
            .idProcesso(idProcesso)
            .idAdvogado(idAdvogado)
            .tipo(tipo)
            .titulo(titulo)
            .status(StatusPeticao.RASCUNHO)
            .dataCriacao(LocalDateTime.now())
            .build();
    }
    
    /**
     * Inicia o processo de geração com IA
     */
    public void iniciarGeracao(String prompt) {
        if (this.status != StatusPeticao.RASCUNHO && this.status != StatusPeticao.REVISAO) {
            throw new DomainException("Só é possível gerar IA para petições em rascunho ou revisão");
        }
        
        if (prompt == null || prompt.isBlank()) {
            throw new DomainException("Prompt não pode ser vazio");
        }
        
        this.status = StatusPeticao.GERANDO;
        this.promptUtilizado = prompt;
        this.dataAtualizacao = LocalDateTime.now();
    }
    
    /**
     * Finaliza a geração com IA
     */
    public void finalizarGeracao(String conteudoGerado) {
        if (this.status != StatusPeticao.GERANDO) {
            throw new DomainException("Petição não está em processo de geração");
        }
        
        if (conteudoGerado == null || conteudoGerado.isBlank()) {
            throw new DomainException("Conteúdo gerado não pode ser vazio");
        }
        
        this.conteudoGeradoIA = conteudoGerado;
        this.conteudo = conteudoGerado; // Inicia com o conteúdo da IA
        this.status = StatusPeticao.REVISAO;
        this.dataAtualizacao = LocalDateTime.now();
    }
    
    /**
     * Atualiza o conteúdo da petição (edição manual)
     */
    public void atualizarConteudo(String novoConteudo) {
        if (this.status == StatusPeticao.PROTOCOLADA) {
            throw new DomainException("Não é possível editar petição protocolada");
        }
        
        if (novoConteudo == null || novoConteudo.isBlank()) {
            throw new DomainException("Conteúdo não pode ser vazio");
        }
        
        this.conteudo = novoConteudo;
        this.dataAtualizacao = LocalDateTime.now();
    }
    
    /**
     * Aprova a petição para protocolo
     */
    public void aprovar() {
        if (this.status == StatusPeticao.PROTOCOLADA) {
            throw new DomainException("Petição já foi protocolada");
        }
        
        if (this.conteudo == null || this.conteudo.isBlank()) {
            throw new DomainException("Petição sem conteúdo não pode ser aprovada");
        }
        
        this.status = StatusPeticao.APROVADA;
        this.dataAtualizacao = LocalDateTime.now();
    }
    
    /**
     * Registra o protocolo da petição
     */
    public void protocolar(String caminhoDocumento) {
        if (this.status != StatusPeticao.APROVADA) {
            throw new DomainException("Só é possível protocolar petições aprovadas");
        }
        
        if (caminhoDocumento == null || caminhoDocumento.isBlank()) {
            throw new DomainException("Caminho do documento é obrigatório");
        }
        
        this.status = StatusPeticao.PROTOCOLADA;
        this.caminhoDocumento = caminhoDocumento;
        this.dataProtocolo = LocalDateTime.now();
        this.dataAtualizacao = LocalDateTime.now();
    }
    
    /**
     * Volta a petição para revisão
     */
    public void voltarParaRevisao() {
        if (this.status == StatusPeticao.PROTOCOLADA) {
            throw new DomainException("Petição protocolada não pode voltar para revisão");
        }
        
        this.status = StatusPeticao.REVISAO;
        this.dataAtualizacao = LocalDateTime.now();
    }
    
    /**
     * Validações de campos obrigatórios
     */
    private static void validarCamposObrigatorios(Long idProcesso, Long idAdvogado, TipoPeticao tipo, String titulo) {
        if (idProcesso == null) {
            throw new DomainException("ID do processo é obrigatório");
        }
        
        if (idAdvogado == null) {
            throw new DomainException("ID do advogado é obrigatório");
        }
        
        if (tipo == null) {
            throw new DomainException("Tipo da petição é obrigatório");
        }
        
        if (titulo == null || titulo.isBlank()) {
            throw new DomainException("Título da petição é obrigatório");
        }
        
        if (titulo.length() > 200) {
            throw new DomainException("Título da petição não pode ter mais de 200 caracteres");
        }
    }
}

