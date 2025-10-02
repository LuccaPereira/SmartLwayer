CREATE DATABASE AdvocaciaDB;
USE AdvocaciaDB;

CREATE TABLE Advogado (
    id INT AUTO_INCREMENT PRIMARY KEY,
    oab VARCHAR(15) UNIQUE NOT NULL,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    telefone VARCHAR(15),
    senha VARCHAR(255) NOT NULL,
    data_cadastro TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);


CREATE TABLE Clientes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome_completo VARCHAR(100) NOT NULL,
    cpf_cnpj VARCHAR(18) UNIQUE,
    email VARCHAR(100),
    telefone VARCHAR(20),
    endereco VARCHAR(255),
    data_cadastro TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE Processos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    numero_processo VARCHAR(30) UNIQUE NOT NULL,
    titulo VARCHAR(150) NOT NULL,
    descricao TEXT,
    status ENUM('Ativo', 'Arquivado', 'Suspenso', 'Finalizado') NOT NULL DEFAULT 'Ativo',
    data_abertura DATE NOT NULL,
    id_advogado INT NOT NULL,
    id_cliente INT NOT NULL,
    FOREIGN KEY (id_advogado) REFERENCES Advogado(id),
    FOREIGN KEY (id_cliente) REFERENCES Clientes(id) ON DELETE CASCADE
);

CREATE TABLE Andamentos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_processo INT NOT NULL,
    data_andamento DATETIME NOT NULL,
    descricao TEXT NOT NULL,
    FOREIGN KEY (id_processo) REFERENCES Processos(id) ON DELETE CASCADE
);

CREATE TABLE Documentos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_processo INT NOT NULL,
    nome_documento VARCHAR(100) NOT NULL,
    caminho_arquivo VARCHAR(255) NOT NULL,
    data_upload TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_processo) REFERENCES Processos(id) ON DELETE CASCADE
);




