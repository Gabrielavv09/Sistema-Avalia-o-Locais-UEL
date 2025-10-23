-- Criação da Tabela USUARIO
CREATE TABLE USUARIO (
    id_usuario SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    tipo VARCHAR(20) NOT NULL,
    curso_nome VARCHAR(100),
    departamento VARCHAR(100),
    CONSTRAINT CHK_TIPO CHECK (tipo IN ('aluno', 'professor'))
);

-- Criação da Tabela LOCALCAMPUS
CREATE TABLE LOCALCAMPUS (
    id_local SERIAL PRIMARY KEY,
    nome VARCHAR(100) UNIQUE NOT NULL,
    descricao TEXT,
    localizacao VARCHAR(100),
    url_image VARCHAR(255)
);

-- Criação da Tabela QUESTAO
CREATE TABLE QUESTAO (
    id_questao SERIAL PRIMARY KEY,
    texto VARCHAR(255) NOT NULL,
    tipo VARCHAR(20) NOT NULL,
    id_usuario_criador INT NULL,
    FOREIGN KEY (id_usuario_criador) REFERENCES USUARIO(id_usuario)
);

-- Criação da Tabela AVALIACAO
CREATE TABLE AVALIACAO (
    id_avaliacao SERIAL PRIMARY KEY,
    id_usuario INT NOT NULL,
    id_local INT NOT NULL,
    data_avaliacao DATE NOT NULL,
    FOREIGN KEY (id_usuario) REFERENCES USUARIO(id_usuario) ON DELETE CASCADE,
    FOREIGN KEY (id_local) REFERENCES LOCALCAMPUS(id_local) ON DELETE CASCADE
);

-- Tabela associativa entre Avaliação e Questão (respostas das perguntas)
CREATE TABLE AVALIACAO_QUESTAO (
    id_avaliacao INT NOT NULL,
    id_questao INT NOT NULL,
    valor VARCHAR(100),
    PRIMARY KEY (id_avaliacao, id_questao),
    FOREIGN KEY (id_avaliacao) REFERENCES AVALIACAO(id_avaliacao) ON DELETE CASCADE,
    FOREIGN KEY (id_questao) REFERENCES QUESTAO(id_questao) ON DELETE CASCADE
);