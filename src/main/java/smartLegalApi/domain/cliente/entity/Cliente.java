package smartLegalApi.domain.cliente.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import smartLegalApi.domain.cliente.valueobject.CpfCnpj;
import smartLegalApi.domain.cliente.valueobject.Endereco;
import smartLegalApi.domain.shared.exception.BusinessRuleException;
import smartLegalApi.domain.shared.valueobject.Email;
import smartLegalApi.domain.shared.valueobject.Telefone;

import java.time.LocalDateTime;

/**
 * Entidade de Domínio: Cliente
 * Representa clientes do escritório (pessoa física ou jurídica)
 */
@Getter
@Builder
@AllArgsConstructor
public class Cliente {
    
    private Long id;
    private String nomeCompleto;
    private CpfCnpj cpfCnpj;
    private Email email;
    private Telefone telefone;
    private Endereco endereco;
    private LocalDateTime dataCadastro;
    private LocalDateTime dataAtualizacao;
    private boolean ativo;

    /**
     * Construtor para criar novo cliente (sem ID)
     */
    public Cliente(String nomeCompleto, CpfCnpj cpfCnpj, Email email, Telefone telefone, Endereco endereco) {
        this.validarDadosObrigatorios(nomeCompleto, cpfCnpj);
        
        this.nomeCompleto = nomeCompleto;
        this.cpfCnpj = cpfCnpj;
        this.email = email;
        this.telefone = telefone;
        this.endereco = endereco;
        this.dataCadastro = LocalDateTime.now();
        this.dataAtualizacao = LocalDateTime.now();
        this.ativo = true;
    }

    /**
     * Valida dados obrigatórios
     */
    private void validarDadosObrigatorios(String nomeCompleto, CpfCnpj cpfCnpj) {
        if (nomeCompleto == null || nomeCompleto.isBlank()) {
            throw new BusinessRuleException("Nome completo é obrigatório");
        }
        
        if (nomeCompleto.length() < 3) {
            throw new BusinessRuleException("Nome deve ter no mínimo 3 caracteres");
        }
        
        if (nomeCompleto.length() > 100) {
            throw new BusinessRuleException("Nome deve ter no máximo 100 caracteres");
        }
        
        if (cpfCnpj == null) {
            throw new BusinessRuleException("CPF/CNPJ é obrigatório");
        }
    }

    /**
     * Atualiza dados do cliente
     */
    public void atualizar(String nomeCompleto, Email email, Telefone telefone, Endereco endereco) {
        if (nomeCompleto != null && !nomeCompleto.isBlank()) {
            if (nomeCompleto.length() < 3 || nomeCompleto.length() > 100) {
                throw new BusinessRuleException("Nome deve ter entre 3 e 100 caracteres");
            }
            this.nomeCompleto = nomeCompleto;
        }
        
        if (email != null) {
            this.email = email;
        }
        
        if (telefone != null) {
            this.telefone = telefone;
        }
        
        if (endereco != null) {
            this.endereco = endereco;
        }
        
        this.dataAtualizacao = LocalDateTime.now();
    }

    /**
     * Ativa o cliente
     */
    public void ativar() {
        if (this.ativo) {
            throw new BusinessRuleException("Cliente já está ativo");
        }
        this.ativo = true;
        this.dataAtualizacao = LocalDateTime.now();
    }

    /**
     * Inativa o cliente (soft delete)
     */
    public void inativar() {
        if (!this.ativo) {
            throw new BusinessRuleException("Cliente já está inativo");
        }
        this.ativo = false;
        this.dataAtualizacao = LocalDateTime.now();
    }

    /**
     * Verifica se é pessoa física
     */
    public boolean isPessoaFisica() {
        return cpfCnpj.isPessoaFisica();
    }

    /**
     * Verifica se é pessoa jurídica
     */
    public boolean isPessoaJuridica() {
        return cpfCnpj.isPessoaJuridica();
    }

    /**
     * Retorna tipo de pessoa para exibição
     */
    public String getTipoPessoa() {
        return isPessoaFisica() ? "Pessoa Física" : "Pessoa Jurídica";
    }
}

