# TP1 - Sistema de Gerenciamento de RIT

Sistema desktop em Java para gerenciamento de Relatórios Individuais de Trabalho de professores.

## Tecnologias

- Java
- Swing
- SQLite
- JDBC
- Maven
- JavaDoc

## Funcionalidades previstas

- Cadastro, edição, listagem e remoção de professores
- Gerenciamento de RIT por professor e semestre
- Cadastro de disciplinas ministradas
- Cadastro de orientações
- Cadastro de artigos publicados
- Cadastro de atividades de coordenação
- Geração de tela de relatório final

## Pré-requisitos

Antes de executar o projeto, é necessário ter instalado:

-Java JDK 17 ou superior;
-Maven;
-Git.

Para verificar se estão instalados, execute:

```
java -version

mvn -version

git --version
```

## Como baixar o projeto

Clone o repositório:

```
git clone https://github.com/Codby-Sa/RIT_Prof.git
```

Entre na pasta do projeto:

cd RIT_Prof

## Como compilar o projeto

Na raiz do projeto, execute:

```
mvn clean compile
```

Se tudo estiver correto, o Maven exibirá uma mensagem semelhante a:

BUILD SUCCESS

## Como executar o sistema

Execute:

```
mvn exec:java
```
A aplicação será aberta em uma janela desktop usando Java Swing.
