package br.edu.ufam.rit.view;

import br.edu.ufam.rit.dao.ProfessorDAO;
import br.edu.ufam.rit.model.Professor;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.sql.SQLException;

/**
 * Janela de formulário usada para cadastrar ou editar professores.
 *
 * <p>Quando recebe um professor nulo, funciona como cadastro.
 * Quando recebe um professor existente, funciona como edição.</p>
 */
public class ProfessorFormDialog extends JDialog {

    private JTextField nomeField;
    private JTextField emailField;
    private JTextField departamentoField;

    private Professor professor;
    private ProfessorDAO professorDAO;
    private boolean saved;

    /**
     * Cria o formulário de professor.
     *
     * @param parent janela principal que abriu o formulário
     * @param professor professor que será editado, ou null para cadastro
     */
    public ProfessorFormDialog(JFrame parent, Professor professor) {
        super(parent, true);

        this.professor = professor;
        this.professorDAO = new ProfessorDAO();
        this.saved = false;

        configurarJanela();
        criarComponentes();
        preencherCamposSeEdicao();
    }

    /**
     * Configura as propriedades principais da janela.
     */
    private void configurarJanela() {
        if (professor == null) {
            setTitle("Adicionar Professor");
        } else {
            setTitle("Editar Professor");
        }

        setSize(400, 250);
        setLocationRelativeTo(getParent());
        setLayout(new BorderLayout(10, 10));
    }

    /**
     * Cria os campos e botões do formulário.
     */
    private void criarComponentes() {
        JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 10));

        JLabel nomeLabel = new JLabel("Nome:");
        JLabel emailLabel = new JLabel("Email:");
        JLabel departamentoLabel = new JLabel("Departamento:");

        nomeField = new JTextField();
        emailField = new JTextField();
        departamentoField = new JTextField();

        formPanel.add(nomeLabel);
        formPanel.add(nomeField);

        formPanel.add(emailLabel);
        formPanel.add(emailField);

        formPanel.add(departamentoLabel);
        formPanel.add(departamentoField);

        JPanel buttonPanel = new JPanel();

        JButton saveButton = new JButton("Salvar");
        JButton cancelButton = new JButton("Cancelar");

        saveButton.addActionListener(event -> salvarProfessor());
        cancelButton.addActionListener(event -> dispose());

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    /**
     * Preenche os campos caso o formulário esteja no modo de edição.
     */
    private void preencherCamposSeEdicao() {
        if (professor != null) {
            nomeField.setText(professor.getNome());
            emailField.setText(professor.getEmail());
            departamentoField.setText(professor.getDepartamento());
        }
    }

    /**
     * Valida os dados e salva o professor no banco.
     */
    private void salvarProfessor() {
        String nome = nomeField.getText().trim();
        String email = emailField.getText().trim();
        String departamento = departamentoField.getText().trim();

        if (nome.isEmpty()) {
            JOptionPane.showMessageDialog(
                this,
                "O nome do professor é obrigatório.",
                "Validação",
                JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        try {
            if (professor == null) {
                Professor novoProfessor = new Professor(nome, email, departamento);
                professorDAO.inserir(novoProfessor);
            } else {
                professor.setNome(nome);
                professor.setEmail(email);
                professor.setDepartamento(departamento);

                professorDAO.atualizar(professor);
            }

            saved = true;
            dispose();

        } catch (SQLException exception) {
            JOptionPane.showMessageDialog(
                this,
                "Erro ao salvar professor.",
                "Erro",
                JOptionPane.ERROR_MESSAGE
            );

            exception.printStackTrace();
        }
    }

    /**
     * Informa se o formulário salvou algum dado.
     *
     * @return true se salvou, false caso contrário
     */
    public boolean isSaved() {
        return saved;
    }
}