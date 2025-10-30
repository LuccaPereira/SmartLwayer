package smartLegalApi.domain.advogado.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import smartLegalApi.domain.shared.exception.BusinessRuleException;
import smartLegalApi.domain.shared.valueobject.CPF;
import smartLegalApi.domain.shared.valueobject.Email;
import smartLegalApi.domain.shared.valueobject.OAB;
import smartLegalApi.domain.shared.valueobject.Telefone;

import java.time.LocalDateTime;

/**
 * Entidade de Domínio: Advogado
 * Contém as regras de negócio e comportamentos relacionados ao advogado
 */
@Getter
@Builder
@AllArgsConstructor
public class Advogado {
    
    private Long id;
    private OAB oab;
    private String nome;
    private CPF cpf;
    private Email email;
    private Telefone telefone;
    private String senhaHash;  // NUNCA armazene senha em texto plano!
    private LocalDateTime dataCadastro;
    private LocalDateTime dataAtualizacao;
    private boolean ativo;

    /**
     * Construtor para criar novo advogado (sem ID)
     */
    public Advogado(OAB oab, String nome, CPF cpf, Email email, Telefone telefone, String senhaHash) {
        this.validarDadosObrigatorios(oab, nome, cpf, email, senhaHash);
        
        this.oab = oab;
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.telefone = telefone;
        this.senhaHash = senhaHash;
        this.dataCadastro = LocalDateTime.now();
        this.dataAtualizacao = LocalDateTime.now();
        this.ativo = true;
    }

    /**
     * Retorna a senha hash (alias para compatibilidade)
     */
    public String getSenha() {
        return this.senhaHash;
    }
    
    /**
     * Valida dados obrigatórios
     */
    private void validarDadosObrigatorios(OAB oab, String nome, CPF cpf, Email email, String senhaHash) {
        if (oab == null) {
            throw new BusinessRuleException("OAB é obrigatória");
        }
        
        if (nome == null || nome.isBlank()) {
            throw new BusinessRuleException("Nome é obrigatório");
        }
        
        if (nome.length() < 3) {
            throw new BusinessRuleException("Nome deve ter no mínimo 3 caracteres");
        }
        
        if (nome.length() > 100) {
            throw new BusinessRuleException("Nome deve ter no máximo 100 caracteres");
        }
        
        if (cpf == null) {
            throw new BusinessRuleException("CPF é obrigatório");
        }
        
        if (email == null) {
            throw new BusinessRuleException("Email é obrigatório");
        }
        
        if (senhaHash == null || senhaHash.isBlank()) {
            throw new BusinessRuleException("Senha é obrigatória");
        }
    }

    /**
     * Atualiza dados do advogado
     */
    public void atualizar(String nome, Email email, Telefone telefone) {
        if (nome != null && !nome.isBlank()) {
            if (nome.length() < 3 || nome.length() > 100) {
                throw new BusinessRuleException("Nome deve ter entre 3 e 100 caracteres");
            }
            this.nome = nome;
        }
        
        if (email != null) {
            this.email = email;
        }
        
        if (telefone != null) {
            this.telefone = telefone;
        }
        
        this.dataAtualizacao = LocalDateTime.now();
    }

    /**
     * Atualiza a senha do advogado
     */
    public void atualizarSenha(String novaSenhaHash) {
        if (novaSenhaHash == null || novaSenhaHash.isBlank()) {
            throw new BusinessRuleException("Senha não pode ser vazia");
        }
        
        this.senhaHash = novaSenhaHash;
        this.dataAtualizacao = LocalDateTime.now();
    }

    /**
     * Ativa o advogado
     */
    public void ativar() {
        if (this.ativo) {
            throw new BusinessRuleException("Advogado já está ativo");
        }
        this.ativo = true;
        this.dataAtualizacao = LocalDateTime.now();
    }

    /**
     * Inativa o advogado (soft delete)
     */
    public void inativar() {
        if (!this.ativo) {
            throw new BusinessRuleException("Advogado já está inativo");
        }
        this.ativo = false;
        this.dataAtualizacao = LocalDateTime.now();
    }

    /**
     * Verifica se o advogado pode fazer login
     */
    public boolean podeLogar() {
        return this.ativo;
    }

    /**
     * Retorna nome para exibição
     */
    public String getNomeExibicao() {
        return "Dr(a). " + this.nome;
    }
}

