public class Item {
    private String nome;
    private String descricao;
    private String efeito;
    private int quantidade;
    
    
    public Item (String nome, String descricao, String efeito, int quantidade){
        this.nome = nome;
        this.descricao = descricao;
        this.efeito = efeito;
        this.quantidade = quantidade;
    }

    public String getDescricao() {
        return descricao;
    }
    
    public String getEfeito() {
        return efeito;
    }

    public String getNome() {
        return nome;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setEfeito(String efeito) {
        this.efeito = efeito;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
}