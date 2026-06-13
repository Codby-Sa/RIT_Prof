package br.edu.ufam.rit.dao;

import br.edu.ufam.rit.model.AtividadeCoordenacao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe responsável pelas operações de acesso a dados da entidade AtividadeCoordenacao.
 *
 * <p>Esta classe realiza cadastro, listagem, busca, atualização e remoção
 * de atividades de coordenação vinculadas a um RIT.</p>
 */
public class AtividadeCoordenacaoDAO {

    /**
     * Insere uma nova atividade de coordenação no banco de dados.
     *
     * @param atividade atividade de coordenação que será cadastrada
     * @throws SQLException caso ocorra erro ao inserir a atividade
     */
    public void inserir(AtividadeCoordenacao atividade) throws SQLException {
        String sql = """
            INSERT INTO atividades_coordenacao (rit_id, descricao, cargo, carga_horaria)
            VALUES (?, ?, ?, ?)
        """;

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, atividade.getRitId());
            statement.setString(2, atividade.getDescricao());
            statement.setString(3, atividade.getCargo());
            statement.setInt(4, atividade.getCargaHoraria());

            statement.executeUpdate();
        }
    }

    /**
     * Lista as atividades de coordenação vinculadas a um RIT.
     *
     * @param ritId id do RIT
     * @return lista de atividades de coordenação do RIT
     * @throws SQLException caso ocorra erro ao consultar as atividades
     */
    public List<AtividadeCoordenacao> listarPorRit(int ritId) throws SQLException {
        String sql = """
            SELECT id, rit_id, descricao, cargo, carga_horaria
            FROM atividades_coordenacao
            WHERE rit_id = ?
            ORDER BY cargo, descricao
        """;

        List<AtividadeCoordenacao> atividades = new ArrayList<>();

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, ritId);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    AtividadeCoordenacao atividade = new AtividadeCoordenacao(
                        resultSet.getInt("id"),
                        resultSet.getInt("rit_id"),
                        resultSet.getString("descricao"),
                        resultSet.getString("cargo"),
                        resultSet.getInt("carga_horaria")
                    );

                    atividades.add(atividade);
                }
            }
        }

        return atividades;
    }

    /**
     * Busca uma atividade de coordenação pelo identificador.
     *
     * @param id id da atividade
     * @return atividade encontrada ou null caso não exista
     * @throws SQLException caso ocorra erro ao buscar a atividade
     */
    public AtividadeCoordenacao buscarPorId(int id) throws SQLException {
        String sql = """
            SELECT id, rit_id, descricao, cargo, carga_horaria
            FROM atividades_coordenacao
            WHERE id = ?
        """;

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new AtividadeCoordenacao(
                        resultSet.getInt("id"),
                        resultSet.getInt("rit_id"),
                        resultSet.getString("descricao"),
                        resultSet.getString("cargo"),
                        resultSet.getInt("carga_horaria")
                    );
                }
            }
        }

        return null;
    }

    /**
     * Atualiza uma atividade de coordenação existente.
     *
     * @param atividade atividade com os dados atualizados
     * @throws SQLException caso ocorra erro ao atualizar a atividade
     */
    public void atualizar(AtividadeCoordenacao atividade) throws SQLException {
        String sql = """
            UPDATE atividades_coordenacao
            SET descricao = ?, cargo = ?, carga_horaria = ?
            WHERE id = ?
        """;

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, atividade.getDescricao());
            statement.setString(2, atividade.getCargo());
            statement.setInt(3, atividade.getCargaHoraria());
            statement.setInt(4, atividade.getId());

            statement.executeUpdate();
        }
    }

    /**
     * Remove uma atividade de coordenação do banco de dados.
     *
     * @param id id da atividade que será removida
     * @throws SQLException caso ocorra erro ao remover a atividade
     */
    public void remover(int id) throws SQLException {
        String sql = """
            DELETE FROM atividades_coordenacao
            WHERE id = ?
        """;

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }
}