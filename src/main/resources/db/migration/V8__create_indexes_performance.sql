-- ============================================================
-- Migration V8: Índices adicionais para performance
-- ============================================================

-- Índices compostos para queries comuns

-- Buscar processos ativos de um advogado específico
CREATE INDEX idx_processos_advogado_status ON processos(id_advogado, status);

-- Buscar processos de um cliente específico com status
CREATE INDEX idx_processos_cliente_status ON processos(id_cliente, status);

-- Buscar andamentos recentes de um processo
CREATE INDEX idx_andamentos_processo_data ON andamentos(id_processo, data_andamento DESC);

-- Buscar documentos de um processo por tipo
CREATE INDEX idx_documentos_processo_tipo ON documentos(id_processo, tipo_arquivo);

-- Buscar petições de um advogado por tipo de ação
CREATE INDEX idx_peticoes_advogado_tipo ON peticoes(id_advogado, tipo_acao);

-- Buscar logs de auditoria de um usuário por data
CREATE INDEX idx_audit_usuario_data ON audit_log(usuario_id, data_hora DESC);

-- Full-text search em descrição de processos (opcional)
-- ALTER TABLE processos ADD FULLTEXT INDEX ft_processo_descricao (titulo, descricao);

-- Full-text search em conteúdo de petições (opcional)
-- ALTER TABLE peticoes ADD FULLTEXT INDEX ft_peticao_conteudo (conteudo_peticao);

