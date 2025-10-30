-- ============================================================
-- Migration V1: Criação da tabela de Advogados
-- ============================================================

CREATE TABLE IF NOT EXISTS advogado (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    oab VARCHAR(15) UNIQUE NOT NULL COMMENT 'Número da OAB (Ex: SP123456)',
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    telefone VARCHAR(15),
    senha VARCHAR(255) NOT NULL COMMENT 'Senha criptografada com BCrypt',
    data_cadastro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    data_atualizacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    ativo BOOLEAN DEFAULT TRUE,
    
    INDEX idx_advogado_oab (oab),
    INDEX idx_advogado_email (email),
    INDEX idx_advogado_ativo (ativo)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='Tabela de cadastro de advogados';

