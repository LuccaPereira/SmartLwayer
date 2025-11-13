package smartLegalApi.domain.peticao.valueobject;

/**
 * Tipos de petição judicial
 */
public enum TipoPeticao {
    INICIAL("Petição Inicial"),
    CONTESTACAO("Contestação"),
    RECURSO("Recurso"),
    AGRAVO("Agravo"),
    APELACAO("Apelação"),
    EMBARGOS("Embargos"),
    MANIFESTACAO("Manifestação"),
    HABEAS_CORPUS("Habeas Corpus"),
    MANDADO_SEGURANCA("Mandado de Segurança"),
    OUTRAS("Outras");
    
    private final String descricao;
    
    TipoPeticao(String descricao) {
        this.descricao = descricao;
    }
    
    public String getDescricao() {
        return descricao;
    }
}

