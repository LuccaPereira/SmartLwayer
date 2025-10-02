package smartLegalApi.demo.Model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@Table(name = "documentos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Documentos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_processo", nullable = false)
    private Processos processo;
    @Column(name = "nome_documento", nullable = false, length = 100)
    private String nomeDocumento;
    @Column(name = "caminho_arquivo", nullable = false, length = 255)
    private String caminhoArquivo;
    @Column(name = "data_upload", nullable = false)
    private LocalDate dataUpload;

}
