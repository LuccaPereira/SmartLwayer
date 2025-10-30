package smartLegalApi.domain.audit.valueobject;

/**
 * Tipos de operação auditadas
 */
public enum TipoOperacao {
    CREATE("Criação"),
    UPDATE("Atualização"),
    DELETE("Exclusão"),
    READ("Leitura"),
    LOGIN("Login"),
    LOGOUT("Logout"),
    GENERATE_IA("Geração com IA"),
    APPROVE("Aprovação"),
    PROTOCOL("Protocolo");
    
    private final String descricao;
    
    TipoOperacao(String descricao) {
        this.descricao = descricao;
    }
    
    public String getDescricao() {
        return descricao;
    }
}

