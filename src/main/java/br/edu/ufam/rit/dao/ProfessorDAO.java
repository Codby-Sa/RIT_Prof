package br.edu.ufam.rit.dao;

import br.edu.ufam.rit.model.Professor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe responsável pelas operações de acesso a dados da entidade Professor.
 *
 * <p>Esta classe realiza as operações de cadastro, listagem, busca,
 * atualização e remoção de professores no banco de dados.</p>
 */
public class ProfessorDAO {

    /**
     * Insere um novo professor no banco de dados.
     *
     * @param professor professor que será cadastrado
     * @throws SQLException caso ocorra erro ao inserir o professor
     */
    public void inserir(Professor professor) throws SQLException {
        String sql = """
            INSERT INTO professores (nome, email, departamento)
            VALUES (?, ?, ?)
        """;

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, professor.getNome());
            statement.setString(2, professor.getEmail());
            statement.setString(3, professor.getDepartamento());

            statement.executeUpdate();
        }
    }

    /**
     * Lista todos os professores cadastrados.
     *
     * @return lista de professores
     * @throws SQLException caso ocorra erro ao consultar os professores
     */
    public List<Professor> listar() throws SQLException {
        String sql = """
            SELECT id, nome, email, departamento
            FROM professores
            ORDER BY nome
        """;

        List<Professor> professores = new ArrayList<>();

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Professor professor = new Professor(
                    resultSet.getInt("id"),
                    resultSet.getString("nome"),
                    resultSet.getString("email"),
                    resultSet.getString("departamento")
                );

                professores.add(professor);
            }
        }

        return professores;
    }

    /**
     * Busca um professor pelo identificador.
     *
     * @param id identificador do professor
     * @return professor encontrado ou null caso não exista
     * @throws SQLException caso ocorra erro ao buscar o professor
     */
    public Professor buscarPorId(int id) throws SQLException {
        String sql = """
            SELECT id, nome, email, departamento
            FROM professores
            WHERE id = ?
        """;

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Professor(
                        resultSet.getInt("id"),
                        resultSet.getString("nome"),
                        resultSet.getString("email"),
                        resultSet.getString("departamento")
                    );
                }
            }
        }

        return null;
    }

    /**
     * Atualiza os dados de um professor existente.
     *
     * @param professor professor com os dados atualizados
     * @throws SQLException caso ocorra erro ao atualizar o professor
     */
    public void atualizar(Professor professor) throws SQLException {
        String sql = """
            UPDATE professores
            SET nome = ?, email = ?, departamento = ?
            WHERE id = ?
        """;

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, professor.getNome());
            statement.setString(2, professor.getEmail());
            statement.setString(3, professor.getDepartamento());
            statement.setInt(4, professor.getId());

            statement.executeUpdate();
        }
    }

    /**
     * Remove um professor do banco de dados.
     *
     * @param id identificador do professor que será removido
     * @throws SQLException caso ocorra erro ao remover o professor
     */
    public void remover(int id) throws SQLException {
        String sql = """
            DELETE FROM professores
            WHERE id = ?
        """;

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            statement.executeUpdate();
        }
    }
}