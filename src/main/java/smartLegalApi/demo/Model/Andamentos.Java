package smartLegalApi.demo.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@Table(name = "andamentos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Andamentos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) 
    @JoinColumn(name = "id_processo", nullable = false)
    private Processos processo;

    @Column(name = "data_andamento", nullable = false)
    private LocalDate dataAndamento;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String descricao;
}