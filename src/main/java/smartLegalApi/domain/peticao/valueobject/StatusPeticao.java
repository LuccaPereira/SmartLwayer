package smartLegalApi.domain.peticao.valueobject;

/**
 * Status da petição
 */
public enum StatusPeticao {
    RASCUNHO("Rascunho"),
    GERANDO("Gerando com IA"),
    REVISAO("Em Revisão"),
    APROVADA("Aprovada"),
    PROTOCOLADA("Protocolada");
    
    private final String descricao;
    
    StatusPeticao(String descricao) {
        this.descricao = descricao;
    }
    
    public String getDescricao() {
        return descricao;
    }
}

