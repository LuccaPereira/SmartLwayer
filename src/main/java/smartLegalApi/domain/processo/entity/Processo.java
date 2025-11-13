package smartLegalApi.domain.processo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import smartLegalApi.domain.processo.valueobject.NumeroProcesso;
import smartLegalApi.domain.processo.valueobject.StatusProcesso;
import smartLegalApi.domain.shared.exception.BusinessRuleException;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Entidade de Domínio: Processo (Agregado Raiz)
 * Representa um processo judicial
 */
@Getter
@Builder
@AllArgsConstructor
public class Processo {
    
    private Long id;
    private NumeroProcesso numeroProcesso;
    private String titulo;
    private String descricao;
    private StatusProcesso status;
    private LocalDate dataAbertura;
    private LocalDate dataEncerramento;
    private Long idAdvogado;  // Referência ao advogado responsável
    private Long idCliente;   // Referência ao cliente
    private LocalDateTime dataCadastro;
    private LocalDateTime dataAtualizacao;

    /**
     * Construtor para criar novo processo
     */
    public Processo(NumeroProcesso numeroProcesso, String titulo, String descricao, 
                   LocalDate dataAbertura, Long idAdvogado, Long idCliente) {
        this.validarDadosObrigatorios(numeroProcesso, titulo, dataAbertura, idAdvogado, idCliente);
        
        this.numeroProcesso = numeroProcesso;
        this.titulo = titulo;
        this.descricao = descricao;
        this.status = StatusProcesso.ATIVO;
        this.dataAbertura = dataAbertura;
        this.idAdvogado = idAdvogado;
        this.idCliente = idCliente;
        this.dataCadastro = LocalDateTime.now();
        this.dataAtualizacao = LocalDateTime.now();
    }
    
    /**
     * Método estático para criar novo processo
     */
    public static Processo criar(NumeroProcesso numeroProcesso, String titulo, String descricao, 
                                 Long idAdvogado, Long idCliente) {
        return new Processo(numeroProcesso, titulo, descricao, LocalDate.now(), idAdvogado, idCliente);
    }

    private void validarDadosObrigatorios(NumeroProcesso numeroProcesso, String titulo, 
                                         LocalDate dataAbertura, Long idAdvogado, Long idCliente) {
        if (numeroProcesso == null) {
            throw new BusinessRuleException("Número do processo é obrigatório");
        }
        
        if (titulo == null || titulo.isBlank()) {
            throw new BusinessRuleException("Título do processo é obrigatório");
        }
        
        if (titulo.length() > 150) {
            throw new BusinessRuleException("Título deve ter no máximo 150 caracteres");
        }
        
        if (dataAbertura == null) {
            throw new BusinessRuleException("Data de abertura é obrigatória");
        }
        
        if (dataAbertura.isAfter(LocalDate.now())) {
            throw new BusinessRuleException("Data de abertura não pode ser futura");
        }
        
        if (idAdvogado == null) {
            throw new BusinessRuleException("Advogado responsável é obrigatório");
        }
        
        if (idCliente == null) {
            throw new BusinessRuleException("Cliente é obrigatório");
        }
    }

    /**
     * Atualiza dados do processo
     */
    public void atualizar(String titulo, String descricao) {
        if (titulo != null && !titulo.isBlank()) {
            if (titulo.length() > 150) {
                throw new BusinessRuleException("Título deve ter no máximo 150 caracteres");
            }
            this.titulo = titulo;
        }
        
        this.descricao = descricao;
        this.dataAtualizacao = LocalDateTime.now();
    }

    /**
     * Encerra o processo
     */
    public void encerrar() {
        if (this.status == StatusProcesso.ARQUIVADO) {
            throw new BusinessRuleException("Processo já está encerrado");
        }
        
        this.status = StatusProcesso.ARQUIVADO;
        this.dataEncerramento = LocalDate.now();
        this.dataAtualizacao = LocalDateTime.now();
    }
    
    /**
     * Altera o status do processo
     */
    public void alterarStatus(StatusProcesso novoStatus) {
        if (novoStatus == null) {
            throw new BusinessRuleException("Status não pode ser nulo");
        }
        
        this.status = novoStatus;
        this.dataAtualizacao = LocalDateTime.now();
        
        // Se finalizado ou arquivado, registra data de encerramento
        if (novoStatus == StatusProcesso.FINALIZADO || novoStatus == StatusProcesso.ARQUIVADO) {
            if (this.dataEncerramento == null) {
                this.dataEncerramento = LocalDate.now();
            }
        }
    }

    /**
     * Arquiva o processo
     */
    public void arquivar() {
        this.alterarStatus(StatusProcesso.ARQUIVADO);
    }

    /**
     * Finaliza o processo
     */
    public void finalizar() {
        this.alterarStatus(StatusProcesso.FINALIZADO);
    }

    /**
     * Suspende o processo
     */
    public void suspender() {
        this.alterarStatus(StatusProcesso.SUSPENSO);
    }

    /**
     * Reativa o processo
     */
    public void reativar() {
        this.alterarStatus(StatusProcesso.ATIVO);
        this.dataEncerramento = null;
    }

    /**
     * Verifica se o processo está ativo
     */
    public boolean isAtivo() {
        return this.status == StatusProcesso.ATIVO;
    }

    /**
     * Verifica se o processo está encerrado
     */
    public boolean isEncerrado() {
        return this.status == StatusProcesso.FINALIZADO || this.status == StatusProcesso.ARQUIVADO;
    }
}

