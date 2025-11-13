package smartLegalApi.infrastructure.persistence.jpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Entidade JPA para Documento
 */
@Entity
@Table(name = "documentos", indexes = {
    @Index(name = "idx_documento_processo", columnList = "id_processo"),
    @Index(name = "idx_documento_tipo", columnList = "tipo_arquivo")
})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DocumentoJpaEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "id_processo", nullable = false)
    private Long idProcesso;
    
    @Column(name = "nome_documento", nullable = false, length = 100)
    private String nomeDocumento;
    
    @Column(name = "caminho_arquivo", nullable = false, length = 255)
    private String caminhoArquivo;
    
    @Column(name = "tipo_arquivo", length = 50)
    private String tipoArquivo;
    
    @Column(name = "tamanho_bytes")
    private Long tamanhoBytes;
    
    @Column(name = "hash_arquivo", length = 64)
    private String hashArquivo;
    
    @Column(name = "data_upload", nullable = false)
    private LocalDateTime dataUpload;
}

