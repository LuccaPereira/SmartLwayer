-- ============================================================
-- Migration V5: Criação da tabela de Documentos
-- ============================================================

CREATE TABLE IF NOT EXISTS documentos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_processo BIGINT NOT NULL,
    nome_documento VARCHAR(100) NOT NULL,
    caminho_arquivo VARCHAR(255) NOT NULL COMMENT 'Caminho do arquivo no sistema de arquivos ou storage',
    tipo_arquivo VARCHAR(50) COMMENT 'pdf, docx, jpg, etc',
    tamanho_bytes BIGINT COMMENT 'Tamanho do arquivo em bytes',
    hash_arquivo VARCHAR(64) COMMENT 'Hash SHA-256 para integridade',
    data_upload TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    CONSTRAINT fk_documento_processo 
        FOREIGN KEY (id_processo) REFERENCES processos(id) 
        ON DELETE CASCADE ON UPDATE CASCADE,
    
    INDEX idx_documento_processo (id_processo),
    INDEX idx_documento_tipo (tipo_arquivo)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='Tabela de documentos anexados aos processos';

