package br.edu.ufam.rit.view;

import br.edu.ufam.rit.dao.DisciplinaDAO;
import br.edu.ufam.rit.model.Disciplina;

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
 * Janela de formulário usada para cadastrar ou editar disciplinas.
 */
public class DisciplinaFormDialog extends JDialog {

    private JTextField nomeField;
    private JTextField codigoField;
    private JTextField cargaHorariaField;
    private JTextField cursoField;

    private Disciplina disciplina;
    private DisciplinaDAO disciplinaDAO;
    private int ritId;
    private boolean saved;

    /**
     * Cria o formulário de disciplina.
     *
     * @param parent janela principal
     * @param ritId id do RIT relacionado
     * @param disciplina disciplina para edição, ou null para cadastro
     */
    public DisciplinaFormDialog(JFrame parent, int ritId, Disciplina disciplina) {
        super(parent, true);

        this.ritId = ritId;
        this.disciplina = disciplina;
        this.disciplinaDAO = new DisciplinaDAO();
        this.saved = false;

        configurarJanela();
        criarComponentes();
        preencherCamposSeEdicao();
    }

    /**
     * Configura a janela.
     */
    private void configurarJanela() {
        if (disciplina == null) {
            setTitle("Adicionar Disciplina");
        } else {
            setTitle("Editar Disciplina");
        }

        setSize(450, 280);
        setLocationRelativeTo(getParent());
        setLayout(new BorderLayout(10, 10));
    }

    /**
     * Cria os componentes visuais do formulário.
     */
    private void criarComponentes() {
        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));

        nomeField = new JTextField();
        codigoField = new JTextField();
        cargaHorariaField = new JTextField();
        cursoField = new JTextField();

        formPanel.add(new JLabel("Nome:"));
        formPanel.add(nomeField);

        formPanel.add(new JLabel("Código:"));
        formPanel.add(codigoField);

        formPanel.add(new JLabel("Carga horária:"));
        formPanel.add(cargaHorariaField);

        formPanel.add(new JLabel("Curso:"));
        formPanel.add(cursoField);

        JPanel buttonPanel = new JPanel();

        JButton saveButton = new JButton("Salvar");
        JButton cancelButton = new JButton("Cancelar");

        saveButton.addActionListener(event -> salvarDisciplina());
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
        if (disciplina != null) {
            nomeField.setText(disciplina.getNome());
            codigoField.setText(disciplina.getCodigo());
            cargaHorariaField.setText(String.valueOf(disciplina.getCargaHoraria()));
            cursoField.setText(disciplina.getCurso());
        }
    }

    /**
     * Valida e salva a disciplina no banco.
     */
    private void salvarDisciplina() {
        String nome = nomeField.getText().trim();
        String codigo = codigoField.getText().trim();
        String cargaHorariaTexto = cargaHorariaField.getText().trim();
        String curso = cursoField.getText().trim();

        if (nome.isEmpty()) {
            JOptionPane.showMessageDialog(
                this,
                "O nome da disciplina é obrigatório.",
                "Validação",
                JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        int cargaHoraria = 0;

        if (!cargaHorariaTexto.isEmpty()) {
            try {
                cargaHoraria = Integer.parseInt(cargaHorariaTexto);
            } catch (NumberFormatException exception) {
                JOptionPane.showMessageDialog(
                    this,
                    "A carga horária deve ser um número inteiro.",
                    "Validação",
                    JOptionPane.WARNING_MESSAGE
                );
                return;
            }
        }

        try {
            if (disciplina == null) {
                Disciplina novaDisciplina = new Disciplina(
                    ritId,
                    nome,
                    codigo,
                    cargaHoraria,
                    curso
                );

                disciplinaDAO.inserir(novaDisciplina);
            } else {
                disciplina.setNome(nome);
                disciplina.setCodigo(codigo);
                disciplina.setCargaHoraria(cargaHoraria);
                disciplina.setCurso(curso);

                disciplinaDAO.atualizar(disciplina);
            }

            saved = true;
            dispose();

        } catch (SQLException exception) {
            JOptionPane.showMessageDialog(
                this,
                "Erro ao salvar disciplina.",
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