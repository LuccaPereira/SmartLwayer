-- ============================================================
-- Migration V7: Criação da tabela de Auditoria
-- ============================================================

CREATE TABLE IF NOT EXISTS audit_log (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    usuario_id BIGINT COMMENT 'ID do advogado que realizou a ação',
    entidade VARCHAR(50) NOT NULL COMMENT 'Nome da entidade (Advogado, Cliente, Processo, etc)',
    entidade_id BIGINT COMMENT 'ID da entidade afetada',
    acao VARCHAR(20) NOT NULL COMMENT 'CREATE, UPDATE, DELETE, LOGIN, etc',
    dados_anteriores JSON COMMENT 'Estado anterior da entidade (apenas para UPDATE)',
    dados_novos JSON COMMENT 'Estado novo da entidade',
    ip_address VARCHAR(45) COMMENT 'Endereço IP do usuário',
    user_agent TEXT COMMENT 'User agent do navegador',
    data_hora TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    INDEX idx_audit_usuario (usuario_id),
    INDEX idx_audit_entidade (entidade, entidade_id),
    INDEX idx_audit_acao (acao),
    INDEX idx_audit_data (data_hora)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='Tabela de auditoria de ações do sistema';

