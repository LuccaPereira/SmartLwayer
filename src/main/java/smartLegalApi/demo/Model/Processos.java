package smartLegalApi.demo.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@Table(name = "processos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Processos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numero_processo", unique = true, nullable = false, length = 30)
    private String numeroProcesso;

    @Column(nullable = false, length = 150)
    private String titulo;

    @Column(columnDefinition = "TEXT")
    private String descricao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status = Status.ATIVO;

    @Column(name = "data_abertura", nullable = false)
    private LocalDate dataAbertura;

    @ManyToOne
    @JoinColumn(name = "id_advogado", nullable = false)
    private Advogado advogado;

    @ManyToOne
    @JoinColumn(name = "id_cliente", nullable = false)
    private Cliente cliente;

    public enum Status {
        ATIVO,
        ARQUIVADO,
        SUSPENSO,
        FINALIZADO
    }
}