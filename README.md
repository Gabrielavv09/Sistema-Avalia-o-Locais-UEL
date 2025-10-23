# 🏛️ Sistema de Avaliação de Locais do Campus UEL

## 🎓 Objetivo do Projeto de Banco de Dados I

O objetivo principal deste projeto é desenvolver um sistema que permita realizar avaliações em diferentes espaços do campus (Cantinas, RU, Bibliotecas, Salas de Aula, etc.). O sistema deve apresentar relatórios e gráficos baseados nos dados coletados, aplicando conhecimentos práticos de integração de bancos de dados em aplicações web em camadas.

---

## 🛠️ Stack Tecnológica

* **Linguagem de Backend:** Java 17
* **Framework Web:** Spring Boot 3 (Spring MVC)
* **Templates (Front-end):** Thymeleaf (Para simplificar o desenvolvimento e evitar o uso de frameworks JavaScript)
* **Banco de Dados:** PostgreSQL 
* **Acesso a Dados:** Spring Data JDBC (Com SQL nativo implementado pelos alunos, conforme restrição de não usar JPA/Hibernate)

---

## 🎯 Escopo do Sistema (Funcionalidades Essenciais)

O sistema foi modelado para atender às seguintes entidades:

1.  **Usuário:** Cadastro de Alunos e Professores.
2.  **LocalCampus:** Cadastro dos locais a serem avaliados (RU, Biblioteca, etc.).
3.  **Questão:** Cadastro de perguntas (padrão ou personalizadas).
4.  **Avaliação:** Registro da avaliação de um local por um usuário.
5.  **Relatórios:** Geração de dados agregados, tabelas e gráficos.

---

## ⚙️ Como Rodar o Projeto

### Pré-requisitos
1.  **Java JDK 17** ou superior.
2.  **PostgreSQL** instalado e rodando na porta `5432`.
3.  **IntelliJ IDEA** (recomendado).

### Configuração do Banco de Dados
1.  Crie um novo banco de dados no pgAdmin com o nome: **`avaliacao_campus_uel`**.
2.  Execute o script SQL de criação das tabelas (disponível na pasta do projeto).
3.  Ajuste o `application.properties` com suas credenciais do PostgreSQL (`username` e `password`).

### Execução
1.  Abra o projeto no IntelliJ IDEA.
2.  Execute a classe principal `AvaliacaoCampusApplication.java`.
3.  Acesse a rota inicial no seu navegador (com o sistema rodando): `http://localhost:8080/usuarios/cadastro`
