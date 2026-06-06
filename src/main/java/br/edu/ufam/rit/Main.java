package br.edu.ufam.rit;

import br.edu.ufam.rit.dao.DatabaseInitializer;
import br.edu.ufam.rit.model.Professor;

import java.sql.SQLException;

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

            Professor professor = new Professor("Ana Silva", "ana@ufam.edu.br", "Computação");

            System.out.println("Sistema de Gerenciamento de RIT iniciado.");
            System.out.println("Banco de dados inicializado com sucesso.");
            System.out.println("Professor de teste: " + professor.getNome());
        } catch (SQLException exception) {
            System.err.println("Erro ao inicializar o banco de dados.");
            exception.printStackTrace();
        }
    }
}