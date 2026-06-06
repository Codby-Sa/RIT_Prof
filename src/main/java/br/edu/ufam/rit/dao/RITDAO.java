package br.edu.ufam.rit.dao;

import br.edu.ufam.rit.model.RIT;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Classe responsável pelas operações de acesso a dados da entidade RIT.
 *
 * <p>Esta classe permite cadastrar e buscar Relatórios Individuais de Trabalho
 * relacionados a professores.</p>
 */
public class RITDAO {

    /**
     * Insere um novo RIT no banco de dados.
     *
     * @param rit RIT que será cadastrado
     * @return id gerado para o RIT cadastrado
     * @throws SQLException caso ocorra erro ao inserir o RIT
     */
    public int inserir(RIT rit) throws SQLException {
        String sql = """
            INSERT INTO rits (professor_id, semestre, ano)
            VALUES (?, ?, ?)
        """;

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                 sql,
                 PreparedStatement.RETURN_GENERATED_KEYS
             )) {

            statement.setInt(1, rit.getProfessorId());
            statement.setString(2, rit.getSemestre());
            statement.setInt(3, rit.getAno());

            statement.executeUpdate();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                }
            }
        }

        return 0;
    }

    /**
     * Busca um RIT pelo professor, semestre e ano.
     *
     * @param professorId id do professor relacionado ao RIT
     * @param semestre semestre do relatório
     * @param ano ano do relatório
     * @return RIT encontrado ou null caso não exista
     * @throws SQLException caso ocorra erro ao buscar o RIT
     */
    public RIT buscarPorProfessorSemestreAno(int professorId, String semestre, int ano) throws SQLException {
        String sql = """
            SELECT id, professor_id, semestre, ano
            FROM rits
            WHERE professor_id = ?
              AND semestre = ?
              AND ano = ?
        """;

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, professorId);
            statement.setString(2, semestre);
            statement.setInt(3, ano);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new RIT(
                        resultSet.getInt("id"),
                        resultSet.getInt("professor_id"),
                        resultSet.getString("semestre"),
                        resultSet.getInt("ano")
                    );
                }
            }
        }

        return null;
    }

    /**
     * Busca um RIT pelo identificador.
     *
     * @param id identificador do RIT
     * @return RIT encontrado ou null caso não exista
     * @throws SQLException caso ocorra erro ao buscar o RIT
     */
    public RIT buscarPorId(int id) throws SQLException {
        String sql = """
            SELECT id, professor_id, semestre, ano
            FROM rits
            WHERE id = ?
        """;

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new RIT(
                        resultSet.getInt("id"),
                        resultSet.getInt("professor_id"),
                        resultSet.getString("semestre"),
                        resultSet.getInt("ano")
                    );
                }
            }
        }

        return null;
    }
}