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

