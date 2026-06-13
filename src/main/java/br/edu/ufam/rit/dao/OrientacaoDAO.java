package br.edu.ufam.rit.dao;

import br.edu.ufam.rit.model.Orientacao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe responsável pelas operações de acesso a dados da entidade Orientacao.
 *
 * <p>Esta classe realiza cadastro, listagem, busca, atualização e remoção
 * de orientações vinculadas a um RIT.</p>
 */
public class OrientacaoDAO {

    /**
     * Insere uma nova orientação no banco de dados.
     *
     * @param orientacao orientação que será cadastrada
     * @throws SQLException caso ocorra erro ao inserir a orientação
     */
    public void inserir(Orientacao orientacao) throws SQLException {
        String sql = """
            INSERT INTO orientacoes (rit_id, nome_aluno, tipo, titulo_trabalho, curso)
            VALUES (?, ?, ?, ?, ?)
        """;

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, orientacao.getRitId());
            statement.setString(2, orientacao.getNomeAluno());
            statement.setString(3, orientacao.getTipo());
            statement.setString(4, orientacao.getTituloTrabalho());
            statement.setString(5, orientacao.getCurso());

            statement.executeUpdate();
        }
    }

    /**
     * Lista as orientações vinculadas a um RIT.
     *
     * @param ritId id do RIT
     * @return lista de orientações do RIT
     * @throws SQLException caso ocorra erro ao consultar as orientações
     */
    public List<Orientacao> listarPorRit(int ritId) throws SQLException {
        String sql = """
            SELECT id, rit_id, nome_aluno, tipo, titulo_trabalho, curso
            FROM orientacoes
            WHERE rit_id = ?
            ORDER BY tipo, nome_aluno
        """;

        List<Orientacao> orientacoes = new ArrayList<>();

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, ritId);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Orientacao orientacao = new Orientacao(
                        resultSet.getInt("id"),
                        resultSet.getInt("rit_id"),
                        resultSet.getString("nome_aluno"),
                        resultSet.getString("tipo"),
                        resultSet.getString("titulo_trabalho"),
                        resultSet.getString("curso")
                    );

                    orientacoes.add(orientacao);
                }
            }
        }

        return orientacoes;
    }

    /**
     * Busca uma orientação pelo identificador.
     *
     * @param id id da orientação
     * @return orientação encontrada ou null caso não exista
     * @throws SQLException caso ocorra erro ao buscar a orientação
     */
    public Orientacao buscarPorId(int id) throws SQLException {
        String sql = """
            SELECT id, rit_id, nome_aluno, tipo, titulo_trabalho, curso
            FROM orientacoes
            WHERE id = ?
        """;

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Orientacao(
                        resultSet.getInt("id"),
                        resultSet.getInt("rit_id"),
                        resultSet.getString("nome_aluno"),
                        resultSet.getString("tipo"),
                        resultSet.getString("titulo_trabalho"),
                        resultSet.getString("curso")
                    );
                }
            }
        }

        return null;
    }

    /**
     * Atualiza uma orientação existente.
     *
     * @param orientacao orientação com os dados atualizados
     * @throws SQLException caso ocorra erro ao atualizar a orientação
     */
    public void atualizar(Orientacao orientacao) throws SQLException {
        String sql = """
            UPDATE orientacoes
            SET nome_aluno = ?, tipo = ?, titulo_trabalho = ?, curso = ?
            WHERE id = ?
        """;

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, orientacao.getNomeAluno());
            statement.setString(2, orientacao.getTipo());
            statement.setString(3, orientacao.getTituloTrabalho());
            statement.setString(4, orientacao.getCurso());
            statement.setInt(5, orientacao.getId());

            statement.executeUpdate();
        }
    }

    /**
     * Remove uma orientação do banco de dados.
     *
     * @param id id da orientação que será removida
     * @throws SQLException caso ocorra erro ao remover a orientação
     */
    public void remover(int id) throws SQLException {
        String sql = """
            DELETE FROM orientacoes
            WHERE id = ?
        """;

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }
}