package br.edu.ufam.rit.dao;

import br.edu.ufam.rit.model.Disciplina;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe responsável pelas operações de acesso a dados da entidade Disciplina.
 *
 * <p>Esta classe realiza cadastro, listagem, busca, atualização e remoção
 * de disciplinas ministradas em um RIT.</p>
 */
public class DisciplinaDAO {

    /**
     * Insere uma nova disciplina no banco de dados.
     *
     * @param disciplina disciplina que será cadastrada
     * @throws SQLException caso ocorra erro ao inserir a disciplina
     */
    public void inserir(Disciplina disciplina) throws SQLException {
        String sql = """
            INSERT INTO disciplinas (rit_id, nome, codigo, carga_horaria, curso)
            VALUES (?, ?, ?, ?, ?)
        """;

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, disciplina.getRitId());
            statement.setString(2, disciplina.getNome());
            statement.setString(3, disciplina.getCodigo());
            statement.setInt(4, disciplina.getCargaHoraria());
            statement.setString(5, disciplina.getCurso());

            statement.executeUpdate();
        }
    }

    /**
     * Lista as disciplinas vinculadas a um RIT.
     *
     * @param ritId id do RIT
     * @return lista de disciplinas do RIT
     * @throws SQLException caso ocorra erro ao consultar as disciplinas
     */
    public List<Disciplina> listarPorRit(int ritId) throws SQLException {
        String sql = """
            SELECT id, rit_id, nome, codigo, carga_horaria, curso
            FROM disciplinas
            WHERE rit_id = ?
            ORDER BY nome
        """;

        List<Disciplina> disciplinas = new ArrayList<>();

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, ritId);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Disciplina disciplina = new Disciplina(
                        resultSet.getInt("id"),
                        resultSet.getInt("rit_id"),
                        resultSet.getString("nome"),
                        resultSet.getString("codigo"),
                        resultSet.getInt("carga_horaria"),
                        resultSet.getString("curso")
                    );

                    disciplinas.add(disciplina);
                }
            }
        }

        return disciplinas;
    }

    /**
     * Busca uma disciplina pelo identificador.
     *
     * @param id id da disciplina
     * @return disciplina encontrada ou null caso não exista
     * @throws SQLException caso ocorra erro ao buscar a disciplina
     */
    public Disciplina buscarPorId(int id) throws SQLException {
        String sql = """
            SELECT id, rit_id, nome, codigo, carga_horaria, curso
            FROM disciplinas
            WHERE id = ?
        """;

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Disciplina(
                        resultSet.getInt("id"),
                        resultSet.getInt("rit_id"),
                        resultSet.getString("nome"),
                        resultSet.getString("codigo"),
                        resultSet.getInt("carga_horaria"),
                        resultSet.getString("curso")
                    );
                }
            }
        }

        return null;
    }

    /**
     * Atualiza uma disciplina existente.
     *
     * @param disciplina disciplina com os dados atualizados
     * @throws SQLException caso ocorra erro ao atualizar a disciplina
     */
    public void atualizar(Disciplina disciplina) throws SQLException {
        String sql = """
            UPDATE disciplinas
            SET nome = ?, codigo = ?, carga_horaria = ?, curso = ?
            WHERE id = ?
        """;

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, disciplina.getNome());
            statement.setString(2, disciplina.getCodigo());
            statement.setInt(3, disciplina.getCargaHoraria());
            statement.setString(4, disciplina.getCurso());
            statement.setInt(5, disciplina.getId());

            statement.executeUpdate();
        }
    }

    /**
     * Remove uma disciplina do banco de dados.
     *
     * @param id id da disciplina que será removida
     * @throws SQLException caso ocorra erro ao remover a disciplina
     */
    public void remover(int id) throws SQLException {
        String sql = """
            DELETE FROM disciplinas
            WHERE id = ?
        """;

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }
}