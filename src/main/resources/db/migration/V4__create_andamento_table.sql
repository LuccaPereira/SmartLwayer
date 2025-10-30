-- ============================================================
-- Migration V4: Criação da tabela de Andamentos
-- ============================================================

CREATE TABLE IF NOT EXISTS andamentos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_processo BIGINT NOT NULL,
    data_andamento DATETIME NOT NULL,
    descricao TEXT NOT NULL,
    tipo VARCHAR(50) COMMENT 'Tipo do andamento (audiência, despacho, sentença, etc)',
    data_cadastro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    CONSTRAINT fk_andamento_processo 
        FOREIGN KEY (id_processo) REFERENCES processos(id) 
        ON DELETE CASCADE ON UPDATE CASCADE,
    
    INDEX idx_andamento_processo (id_processo),
    INDEX idx_andamento_data (data_andamento)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='Tabela de andamentos processuais';

