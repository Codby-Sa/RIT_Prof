package br.edu.ufam.rit.dao;

import br.edu.ufam.rit.model.Artigo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe responsável pelas operações de acesso a dados da entidade Artigo.
 *
 * <p>Esta classe realiza cadastro, listagem, busca, atualização e remoção
 * de artigos publicados vinculados a um RIT.</p>
 */
public class ArtigoDAO {

    /**
     * Insere um novo artigo no banco de dados.
     *
     * @param artigo artigo que será cadastrado
     * @throws SQLException caso ocorra erro ao inserir o artigo
     */
    public void inserir(Artigo artigo) throws SQLException {
        String sql = """
            INSERT INTO artigos (rit_id, titulo, autores, nome_evento_ou_revista, ano_publicacao)
            VALUES (?, ?, ?, ?, ?)
        """;

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, artigo.getRitId());
            statement.setString(2, artigo.getTitulo());
            statement.setString(3, artigo.getAutores());
            statement.setString(4, artigo.getNomeEventoOuRevista());
            statement.setInt(5, artigo.getAnoPublicacao());

            statement.executeUpdate();
        }
    }

    /**
     * Lista os artigos vinculados a um RIT.
     *
     * @param ritId id do RIT
     * @return lista de artigos do RIT
     * @throws SQLException caso ocorra erro ao consultar os artigos
     */
    public List<Artigo> listarPorRit(int ritId) throws SQLException {
        String sql = """
            SELECT id, rit_id, titulo, autores, nome_evento_ou_revista, ano_publicacao
            FROM artigos
            WHERE rit_id = ?
            ORDER BY ano_publicacao DESC, titulo
        """;

        List<Artigo> artigos = new ArrayList<>();

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, ritId);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Artigo artigo = new Artigo(
                        resultSet.getInt("id"),
                        resultSet.getInt("rit_id"),
                        resultSet.getString("titulo"),
                        resultSet.getString("autores"),
                        resultSet.getString("nome_evento_ou_revista"),
                        resultSet.getInt("ano_publicacao")
                    );

                    artigos.add(artigo);
                }
            }
        }

        return artigos;
    }

    /**
     * Busca um artigo pelo identificador.
     *
     * @param id id do artigo
     * @return artigo encontrado ou null caso não exista
     * @throws SQLException caso ocorra erro ao buscar o artigo
     */
    public Artigo buscarPorId(int id) throws SQLException {
        String sql = """
            SELECT id, rit_id, titulo, autores, nome_evento_ou_revista, ano_publicacao
            FROM artigos
            WHERE id = ?
        """;

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Artigo(
                        resultSet.getInt("id"),
                        resultSet.getInt("rit_id"),
                        resultSet.getString("titulo"),
                        resultSet.getString("autores"),
                        resultSet.getString("nome_evento_ou_revista"),
                        resultSet.getInt("ano_publicacao")
                    );
                }
            }
        }

        return null;
    }

    /**
     * Atualiza um artigo existente.
     *
     * @param artigo artigo com os dados atualizados
     * @throws SQLException caso ocorra erro ao atualizar o artigo
     */
    public void atualizar(Artigo artigo) throws SQLException {
        String sql = """
            UPDATE artigos
            SET titulo = ?, autores = ?, nome_evento_ou_revista = ?, ano_publicacao = ?
            WHERE id = ?
        """;

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, artigo.getTitulo());
            statement.setString(2, artigo.getAutores());
            statement.setString(3, artigo.getNomeEventoOuRevista());
            statement.setInt(4, artigo.getAnoPublicacao());
            statement.setInt(5, artigo.getId());

            statement.executeUpdate();
        }
    }

    /**
     * Remove um artigo do banco de dados.
     *
     * @param id id do artigo que será removido
     * @throws SQLException caso ocorra erro ao remover o artigo
     */
    public void remover(int id) throws SQLException {
        String sql = """
            DELETE FROM artigos
            WHERE id = ?
        """;

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }
}