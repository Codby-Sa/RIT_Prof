package br.edu.ufam.rit.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Classe responsável por criar conexões com o banco de dados SQLite.
 *
 * <p>A ConnectionFactory centraliza a configuração da conexão para evitar
 * repetição de código nas classes DAO.</p>
 */
public class ConnectionFactory {

    private static final String DATABASE_URL = "jdbc:sqlite:rit.db";

    /**
     * Cria e retorna uma conexão com o banco de dados.
     *
     * @return conexão ativa com o banco SQLite
     * @throws SQLException caso ocorra algum erro ao abrir a conexão
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DATABASE_URL);
    }
}