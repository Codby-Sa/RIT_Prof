package br.edu.ufam.rit.view;

import br.edu.ufam.rit.dao.OrientacaoDAO;
import br.edu.ufam.rit.model.Orientacao;

import javax.swing.JButton;
import javax.swing.JComboBox;
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
 * Janela de formulário usada para cadastrar ou editar orientações.
 */
public class OrientacaoFormDialog extends JDialog {

    private JTextField nomeAlunoField;
    private JComboBox<String> tipoComboBox;
    private JTextField tituloTrabalhoField;
    private JTextField cursoField;

    private Orientacao orientacao;
    private OrientacaoDAO orientacaoDAO;
    private int ritId;
    private boolean saved;

    /**
     * Cria o formulário de orientação.
     *
     * @param parent janela principal
     * @param ritId id do RIT relacionado
     * @param orientacao orientação para edição, ou null para cadastro
     */
    public OrientacaoFormDialog(JFrame parent, int ritId, Orientacao orientacao) {
        super(parent, true);

        this.ritId = ritId;
        this.orientacao = orientacao;
        this.orientacaoDAO = new OrientacaoDAO();
        this.saved = false;

        configurarJanela();
        criarComponentes();
        preencherCamposSeEdicao();
    }

    /**
     * Configura a janela.
     */
    private void configurarJanela() {
        if (orientacao == null) {
            setTitle("Adicionar Orientação");
        } else {
            setTitle("Editar Orientação");
        }

        setSize(500, 300);
        setLocationRelativeTo(getParent());
        setLayout(new BorderLayout(10, 10));
    }

    /**
     * Cria os componentes visuais do formulário.
     */
    private void criarComponentes() {
        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));

        nomeAlunoField = new JTextField();

        tipoComboBox = new JComboBox<>();
        tipoComboBox.addItem("Projeto final/TCC");
        tipoComboBox.addItem("Iniciação científica");
        tipoComboBox.addItem("Mestrado");
        tipoComboBox.addItem("Doutorado");

        tituloTrabalhoField = new JTextField();
        cursoField = new JTextField();

        formPanel.add(new JLabel("Nome do aluno:"));
        formPanel.add(nomeAlunoField);

        formPanel.add(new JLabel("Tipo:"));
        formPanel.add(tipoComboBox);

        formPanel.add(new JLabel("Título do trabalho:"));
        formPanel.add(tituloTrabalhoField);

        formPanel.add(new JLabel("Curso:"));
        formPanel.add(cursoField);

        JPanel buttonPanel = new JPanel();

        JButton saveButton = new JButton("Salvar");
        JButton cancelButton = new JButton("Cancelar");

        saveButton.addActionListener(event -> salvarOrientacao());
        cancelButton.addActionListener(event -> dispose());

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    /**
     * Preenche os campos quando o formulário está em modo de edição.
     */
    private void preencherCamposSeEdicao() {
        if (orientacao != null) {
            nomeAlunoField.setText(orientacao.getNomeAluno());
            tipoComboBox.setSelectedItem(orientacao.getTipo());
            tituloTrabalhoField.setText(orientacao.getTituloTrabalho());
            cursoField.setText(orientacao.getCurso());
        }
    }

    /**
     * Valida e salva a orientação no banco.
     */
    private void salvarOrientacao() {
        String nomeAluno = nomeAlunoField.getText().trim();
        String tipo = (String) tipoComboBox.getSelectedItem();
        String tituloTrabalho = tituloTrabalhoField.getText().trim();
        String curso = cursoField.getText().trim();

        if (nomeAluno.isEmpty()) {
            JOptionPane.showMessageDialog(
                this,
                "O nome do aluno é obrigatório.",
                "Validação",
                JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        if (tipo == null || tipo.isEmpty()) {
            JOptionPane.showMessageDialog(
                this,
                "O tipo da orientação é obrigatório.",
                "Validação",
                JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        try {
            if (orientacao == null) {
                Orientacao novaOrientacao = new Orientacao(
                    ritId,
                    nomeAluno,
                    tipo,
                    tituloTrabalho,
                    curso
                );

                orientacaoDAO.inserir(novaOrientacao);
            } else {
                orientacao.setNomeAluno(nomeAluno);
                orientacao.setTipo(tipo);
                orientacao.setTituloTrabalho(tituloTrabalho);
                orientacao.setCurso(curso);

                orientacaoDAO.atualizar(orientacao);
            }

            saved = true;
            dispose();

        } catch (SQLException exception) {
            JOptionPane.showMessageDialog(
                this,
                "Erro ao salvar orientação.",
                "Erro",
                JOptionPane.ERROR_MESSAGE
            );

            exception.printStackTrace();
        }
    }

    /**
     * Informa se o formulário salvou os dados.
     *
     * @return true se salvou, false caso contrário
     */
    public boolean isSaved() {
        return saved;
    }
}