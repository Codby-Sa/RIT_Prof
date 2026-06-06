package br.edu.ufam.rit.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Classe responsável por criar as tabelas do banco de dados.
 *
 * <p>As tabelas são criadas apenas se ainda não existirem.</p>
 */
public class DatabaseInitializer {

    /**
     * Inicializa o banco de dados da aplicação.
     *
     * <p>Este método cria todas as tabelas necessárias para o funcionamento
     * do sistema de gerenciamento de RIT.</p>
     *
     * @throws SQLException caso ocorra algum erro durante a criação das tabelas
     */
    public static void initialize() throws SQLException {
        try (Connection connection = ConnectionFactory.getConnection();
             Statement statement = connection.createStatement()) {

            statement.execute("""
                CREATE TABLE IF NOT EXISTS professores (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    nome TEXT NOT NULL,
                    email TEXT,
                    departamento TEXT
                );
            """);

            statement.execute("""
                CREATE TABLE IF NOT EXISTS rits (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    professor_id INTEGER NOT NULL,
                    semestre TEXT NOT NULL,
                    ano INTEGER NOT NULL,
                    FOREIGN KEY (professor_id) REFERENCES professores(id)
                );
            """);

            statement.execute("""
                CREATE TABLE IF NOT EXISTS disciplinas (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    rit_id INTEGER NOT NULL,
                    nome TEXT NOT NULL,
                    codigo TEXT,
                    carga_horaria INTEGER,
                    curso TEXT,
                    FOREIGN KEY (rit_id) REFERENCES rits(id)
                );
            """);

            statement.execute("""
                CREATE TABLE IF NOT EXISTS orientacoes (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    rit_id INTEGER NOT NULL,
                    nome_aluno TEXT NOT NULL,
                    tipo TEXT NOT NULL,
                    titulo_trabalho TEXT,
                    curso TEXT,
                    FOREIGN KEY (rit_id) REFERENCES rits(id)
                );
            """);

            statement.execute("""
                CREATE TABLE IF NOT EXISTS artigos (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    rit_id INTEGER NOT NULL,
                    titulo TEXT NOT NULL,
                    autores TEXT,
                    nome_evento_ou_revista TEXT,
                    ano_publicacao INTEGER,
                    FOREIGN KEY (rit_id) REFERENCES rits(id)
                );
            """);

            statement.execute("""
                CREATE TABLE IF NOT EXISTS atividades_coordenacao (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    rit_id INTEGER NOT NULL,
                    descricao TEXT NOT NULL,
                    cargo TEXT,
                    carga_horaria INTEGER,
                    FOREIGN KEY (rit_id) REFERENCES rits(id)
                );
            """);
        }
    }
}