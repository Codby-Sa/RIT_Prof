package br.edu.ufam.rit;

import br.edu.ufam.rit.dao.DatabaseInitializer;
import br.edu.ufam.rit.view.ProfessorListFrame;

import javax.swing.SwingUtilities;
import java.sql.SQLException;

/**
 * Classe principal do sistema de gerenciamento de RIT.
 *
 * <p>
 * Esta classe inicializa o banco de dados e abre a tela principal
 * da aplicação.
 * </p>
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
            DatabaseInitializer.insertInitialData();

            SwingUtilities.invokeLater(() -> {
                ProfessorListFrame frame = new ProfessorListFrame();
                frame.setVisible(true);
            });

        } catch (SQLException exception) {
            System.err.println("Erro ao inicializar o banco de dados.");
            exception.printStackTrace();
        }
    }
}