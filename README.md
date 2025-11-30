# 🏋️‍♂️ Sistema de Gerenciamento de Academia (JDBC)

> Projeto final da disciplina de Banco de Dados I.
> Desenvolvido em Java utilizando o padrão DAO (Data Access Object) para persistência em banco de dados MySQL.

## 📄 Sobre o Projeto

Este projeto consiste em uma aplicação de console (CLI) para o gerenciamento das atividades de uma academia. O sistema permite o cadastro, consulta e remoção de clientes, funcionários, instrutores e turmas, garantindo a integridade dos dados e aplicando regras de negócio definidas na modelagem do banco de dados.

O projeto foi desenvolvido como parte da avaliação da disciplina, implementando o modelo relacional corrigido e normalizado.

## 🚀 Funcionalidades

O sistema oferece um menu interativo com as seguintes opções:

### 👥 Gestão de Clientes
- **Inserir:** Cadastro de novos alunos com dados pessoais e de contato.
- **Remover:** Exclusão de alunos do sistema.
- **Listar:** Visualização de todos os alunos cadastrados.
- **Relatório:** Listar clientes e as turmas em que estão matriculados (JOIN).

### 👔 Gestão de Funcionários e Instrutores
- **Inserir Funcionário:** Cadastro base de funcionários.
- **Inserir Instrutor:** Especialização de um funcionário existente para a função de instrutor (validação de CPF).
- **Listar e Remover:** Gerenciamento dos registros de equipe.

### 📅 Gestão de Turmas e Participação
- **Turmas:** Criação de turmas com modalidade, vagas e instrutor responsável.
- **Participação:** Matrícula de clientes em turmas específicas (Gerenciamento da tabela associativa).
- **Relatório Avançado:** Consultar turmas com média de capacidade acima de um valor específico (Utilizando `GROUP BY` e `HAVING`).

## 🛠️ Tecnologias Utilizadas

- **Java (JDK 17+)**: Linguagem principal.
- **JDBC (Java Database Connectivity)**: Para conexão com o banco de dados.
- **MySQL**: Sistema Gerenciador de Banco de Dados (SGBD).
- **Padrão DAO**: Separação da lógica de acesso a dados da lógica de negócio.

## 📂 Estrutura do Projeto

O código está organizado no pacote `projetoban` seguindo a estrutura:

- **Modelos (Beans):** `clientes.java`, `funcionarios.java`, `instrutor.java`, `turma.java`, `participacao.java` (Representam as tabelas do banco como objetos Java).
- **DAOs (Data Access Objects):** Classes responsáveis por executar os comandos SQL (`INSERT`, `DELETE`, `SELECT`) para cada entidade.
- **Conexão:** `conexao.java` (Gerencia a conexão Singleton com o MySQL).
- **Main:** `Projetoban.java` (Contém o menu principal e a interação com o usuário).

## ⚙️ Configuração e Instalação

### Pré-requisitos
- Java JDK instalado.
- MySQL Server instalado e rodando.
- Um driver JDBC para MySQL (ex: `mysql-connector-j`) adicionado ao classpath do projeto.

### 1. Configuração do Banco de Dados
Antes de rodar a aplicação, execute o script SQL gerado pelo Modelo Lógico no seu banco de dados para criar as tabelas.

Certifique-se de criar o banco com o nome `udesc` (ou altere na classe de conexão) e colar o conteúdo do arquivo `banco.sql`.
