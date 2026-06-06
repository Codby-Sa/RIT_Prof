package br.edu.ufam.rit;

import br.edu.ufam.rit.dao.DatabaseInitializer;
import br.edu.ufam.rit.dao.ProfessorDAO;
import br.edu.ufam.rit.model.Professor;

import java.sql.SQLException;
import java.util.List;

/**
 * Classe principal do sistema de gerenciamento de RIT.
 *
 * <p>Esta classe será responsável por iniciar a aplicação.</p>
 */
public class Main {

    /**
     * Método principal da aplicação.
     *
     * @param args argumentos recebidos pela linha de comando
     */
    public static void main(String[] args) {
        try {
            DatabaseInitializer.initialize();

            ProfessorDAO professorDAO = new ProfessorDAO();

            Professor professor = new Professor(
                "Ana Silva",
                "ana@ufam.edu.br",
                "Computação"
            );

            professorDAO.inserir(professor);

            List<Professor> professores = professorDAO.listar();

            System.out.println("Professores cadastrados:");

            for (Professor item : professores) {
                System.out.println(
                    item.getId() + " - " +
                    item.getNome() + " - " +
                    item.getEmail() + " - " +
                    item.getDepartamento()
                );
            }

        } catch (SQLException exception) {
            System.err.println("Erro ao executar operação no banco de dados.");
            exception.printStackTrace();
        }
    }
}