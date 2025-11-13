-- ============================================================
-- Migration V9: Adiciona coluna CPF à tabela advogado
-- ============================================================

ALTER TABLE advogado
ADD COLUMN cpf VARCHAR(14) UNIQUE NULL COMMENT 'CPF do advogado' AFTER nome;

-- Adiciona índice para CPF
CREATE INDEX idx_advogado_cpf ON advogado(cpf);

