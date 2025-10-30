package smartLegalApi.domain.processo.valueobject;

/**
 * Enum para Status do Processo
 */
public enum StatusProcesso {
    ATIVO("Ativo"),
    ARQUIVADO("Arquivado"),
    SUSPENSO("Suspenso"),
    FINALIZADO("Finalizado");
    
    private final String descricao;
    
    StatusProcesso(String descricao) {
        this.descricao = descricao;
    }
    
    public String getDescricao() {
        return descricao;
    }
}

