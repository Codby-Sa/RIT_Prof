package br.edu.ufam.rit.view;

import br.edu.ufam.rit.dao.AtividadeCoordenacaoDAO;
import br.edu.ufam.rit.model.AtividadeCoordenacao;

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
 * Janela de formulário usada para cadastrar ou editar atividades de coordenação.
 */
public class CoordenacaoFormDialog extends JDialog {

    private JTextField descricaoField;
    private JTextField cargoField;
    private JTextField cargaHorariaField;

    private AtividadeCoordenacao atividade;
    private AtividadeCoordenacaoDAO atividadeDAO;
    private int ritId;
    private boolean saved;

    /**
     * Cria o formulário de atividade de coordenação.
     *
     * @param parent janela principal
     * @param ritId id do RIT relacionado
     * @param atividade atividade para edição, ou null para cadastro
     */
    public CoordenacaoFormDialog(JFrame parent, int ritId, AtividadeCoordenacao atividade) {
        super(parent, true);

        this.ritId = ritId;
        this.atividade = atividade;
        this.atividadeDAO = new AtividadeCoordenacaoDAO();
        this.saved = false;

        configurarJanela();
        criarComponentes();
        preencherCamposSeEdicao();
    }

    /**
     * Configura a janela.
     */
    private void configurarJanela() {
        if (atividade == null) {
            setTitle("Adicionar Atividade de Coordenação");
        } else {
            setTitle("Editar Atividade de Coordenação");
        }

        setSize(500, 260);
        setLocationRelativeTo(getParent());
        setLayout(new BorderLayout(10, 10));
    }

    /**
     * Cria os componentes visuais do formulário.
     */
    private void criarComponentes() {
        JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 10));

        descricaoField = new JTextField();
        cargoField = new JTextField();
        cargaHorariaField = new JTextField();

        formPanel.add(new JLabel("Descrição:"));
        formPanel.add(descricaoField);

        formPanel.add(new JLabel("Cargo/Função:"));
        formPanel.add(cargoField);

        formPanel.add(new JLabel("Carga horária:"));
        formPanel.add(cargaHorariaField);

        JPanel buttonPanel = new JPanel();

        JButton saveButton = new JButton("Salvar");
        JButton cancelButton = new JButton("Cancelar");

        saveButton.addActionListener(event -> salvarAtividade());
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
        if (atividade != null) {
            descricaoField.setText(atividade.getDescricao());
            cargoField.setText(atividade.getCargo());
            cargaHorariaField.setText(String.valueOf(atividade.getCargaHoraria()));
        }
    }

    /**
     * Valida e salva a atividade no banco.
     */
    private void salvarAtividade() {
        String descricao = descricaoField.getText().trim();
        String cargo = cargoField.getText().trim();
        String cargaHorariaTexto = cargaHorariaField.getText().trim();

        if (descricao.isEmpty()) {
            JOptionPane.showMessageDialog(
                this,
                "A descrição da atividade é obrigatória.",
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
            if (atividade == null) {
                AtividadeCoordenacao novaAtividade = new AtividadeCoordenacao(
                    ritId,
                    descricao,
                    cargo,
                    cargaHoraria
                );

                atividadeDAO.inserir(novaAtividade);
            } else {
                atividade.setDescricao(descricao);
                atividade.setCargo(cargo);
                atividade.setCargaHoraria(cargaHoraria);

                atividadeDAO.atualizar(atividade);
            }

            saved = true;
            dispose();

        } catch (SQLException exception) {
            JOptionPane.showMessageDialog(
                this,
                "Erro ao salvar atividade de coordenação.",
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