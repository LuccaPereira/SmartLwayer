-- ============================================================
-- Migration V3: Criação da tabela de Processos
-- ============================================================

CREATE TABLE IF NOT EXISTS processos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    numero_processo VARCHAR(30) UNIQUE NOT NULL COMMENT 'Número CNJ do processo',
    titulo VARCHAR(150) NOT NULL,
    descricao TEXT,
    status ENUM('ATIVO', 'ARQUIVADO', 'SUSPENSO', 'FINALIZADO') NOT NULL DEFAULT 'ATIVO',
    data_abertura DATE NOT NULL,
    data_encerramento DATE,
    id_advogado BIGINT NOT NULL,
    id_cliente BIGINT NOT NULL,
    data_cadastro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    data_atualizacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    
    CONSTRAINT fk_processo_advogado 
        FOREIGN KEY (id_advogado) REFERENCES advogado(id) 
        ON DELETE RESTRICT ON UPDATE CASCADE,
    
    CONSTRAINT fk_processo_cliente 
        FOREIGN KEY (id_cliente) REFERENCES clientes(id) 
        ON DELETE CASCADE ON UPDATE CASCADE,
    
    INDEX idx_processo_numero (numero_processo),
    INDEX idx_processo_advogado (id_advogado),
    INDEX idx_processo_cliente (id_cliente),
    INDEX idx_processo_status (status),
    INDEX idx_processo_data_abertura (data_abertura)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='Tabela de processos judiciais';

