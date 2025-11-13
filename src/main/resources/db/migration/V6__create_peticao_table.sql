-- ============================================================
-- Migration V6: Criação da tabela de Petições
-- ============================================================

CREATE TABLE IF NOT EXISTS peticoes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_advogado BIGINT NOT NULL,
    id_cliente BIGINT,
    id_processo BIGINT COMMENT 'Processo relacionado (opcional)',
    
    titulo VARCHAR(150) NOT NULL,
    tipo_acao VARCHAR(50) NOT NULL COMMENT 'COBRANCA, DANOS_MORAIS, RESCISAO_CONTRATUAL, etc',
    
    -- Dados do autor (cliente)
    nome_autor VARCHAR(100),
    cpf_cnpj_autor VARCHAR(18),
    endereco_autor VARCHAR(255),
    profissao_autor VARCHAR(100),
    estado_civil_autor VARCHAR(50),
    
    -- Dados do réu
    nome_reu VARCHAR(100),
    cpf_cnpj_reu VARCHAR(18),
    endereco_reu VARCHAR(255),
    
    -- Detalhes da petição
    motivo_acao TEXT,
    pedidos_autor TEXT,
    justica_gratuita BOOLEAN DEFAULT FALSE,
    data_ocorrido DATE,
    cidade_peticao VARCHAR(100),
    outras_informacoes TEXT,
    
    -- Conteúdo gerado
    conteudo_peticao LONGTEXT COMMENT 'Conteúdo da petição gerado pela IA',
    caminho_arquivo VARCHAR(255) COMMENT 'Caminho do arquivo Word/PDF gerado',
    
    -- Metadados
    gerado_por_ia BOOLEAN DEFAULT FALSE,
    data_geracao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    data_atualizacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    
    CONSTRAINT fk_peticao_advogado 
        FOREIGN KEY (id_advogado) REFERENCES advogado(id) 
        ON DELETE RESTRICT ON UPDATE CASCADE,
    
    CONSTRAINT fk_peticao_cliente 
        FOREIGN KEY (id_cliente) REFERENCES clientes(id) 
        ON DELETE SET NULL ON UPDATE CASCADE,
    
    CONSTRAINT fk_peticao_processo 
        FOREIGN KEY (id_processo) REFERENCES processos(id) 
        ON DELETE SET NULL ON UPDATE CASCADE,
    
    INDEX idx_peticao_advogado (id_advogado),
    INDEX idx_peticao_cliente (id_cliente),
    INDEX idx_peticao_processo (id_processo),
    INDEX idx_peticao_tipo_acao (tipo_acao),
    INDEX idx_peticao_data_geracao (data_geracao)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='Tabela de petições geradas manual ou automaticamente pela IA';

