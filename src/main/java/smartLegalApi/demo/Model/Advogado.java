package smartLegalApi.demo.Model;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "advogado")
public class Advogado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Corresponde a: oab VARCHAR(15) UNIQUE NOT NULL
    @Column(unique = true, nullable = false, length = 15)
    private String oab;

    // Corresponde a: nome VARCHAR(100) NOT NULL
    @Column(nullable = false, length = 100)
    private String nome;

    // Corresponde a: email VARCHAR(100) UNIQUE NOT NULL
    @Column(unique = true, nullable = false, length = 100)
    private String email;

    // Corresponde a: telefone VARCHAR(15)
    @Column(length = 15)
    private String telefone;

    // Corresponde a: senha VARCHAR(255) NOT NULL
    @Column(nullable = false, length = 255)
    private String senha;

    // Corresponde a: data_cadastro TIMESTAMP
    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime dataCadastro;

    // Construtor vazio (boas praticas para JPA)
    public Advogado() {
    }

    // Construtor padrão
    public Advogado(Long id, String oab, String nome, String email, String telefone, String senha, LocalDateTime dataCadastro) {
        this.id = id;
        this.oab = oab;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.senha = senha;
        this.dataCadastro = dataCadastro;
    }

    //Getters
    public Long getId() { return this.id; }

    public String getOab() { return oab; }

    public String getNome() { return nome; }

    public String getEmail() { return email;} 

    public String getTelefone() { return telefone; }

    public String getSenha() { return senha; }

    public LocalDateTime getDataCadastro() { return dataCadastro; }

    //Setters
    public void  setId(Long id) { this.id = id; }

    public void setOab(String oab) { this.oab = oab; }

    public void  setNome(String nome) { this.nome = nome; } 

    public void  setEmail(String email) { this.email = email; }

    public void  setTelefone(String telefone) { this.telefone = telefone; }

    public void  setSenha(String senha) { this.senha = senha; }

    public void setDataCadastro(LocalDateTime dataCadastro) { this.dataCadastro = dataCadastro; }
}
