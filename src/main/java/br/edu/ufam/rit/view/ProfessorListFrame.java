package br.edu.ufam.rit.view;

import br.edu.ufam.rit.dao.ProfessorDAO;
import br.edu.ufam.rit.model.Professor;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.Font;
import java.sql.SQLException;
import java.util.List;

/**
 * Tela inicial do sistema, responsável por listar os professores cadastrados.
 *
 * <p>
 * A partir desta tela, o usuário poderá adicionar, editar, excluir
 * e futuramente abrir o gerenciamento de RIT de um professor.
 * </p>
 */
public class ProfessorListFrame extends JFrame {

    private JTable professorTable;
    private DefaultTableModel tableModel;
    private ProfessorDAO professorDAO;

    /**
     * Cria a tela de listagem de professores.
     */
    public ProfessorListFrame() {
        super("Sistema de Gerenciamento de RIT");

        professorDAO = new ProfessorDAO();

        configurarJanela();
        criarComponentes();
        carregarProfessores();
    }

    /**
     * Configura as propriedades principais da janela.
     */
    private void configurarJanela() {
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
    }

    /**
     * Cria e organiza os componentes visuais da tela.
     */
    private void criarComponentes() {
        JLabel titleLabel = new JLabel("Professores cadastrados", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));

        add(titleLabel, BorderLayout.NORTH);

        criarTabela();
        
        criarPainelBotoes();
    }

    /**
     * Cria a tabela responsável por exibir os professores.
     */
    private void criarTabela() {
    String[] columns = {"ID", "Nome", "Email", "Departamento"};

    tableModel = new DefaultTableModel(columns, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    professorTable = new JTable(tableModel);
    professorTable.setRowHeight(28);
    professorTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
    professorTable.setRowSelectionAllowed(true);
    professorTable.setColumnSelectionAllowed(false);

    professorTable.getColumnModel().getColumn(0).setMinWidth(0);
    professorTable.getColumnModel().getColumn(0).setMaxWidth(0);
    professorTable.getColumnModel().getColumn(0).setWidth(0);

    JScrollPane scrollPane = new JScrollPane(professorTable);
    add(scrollPane, BorderLayout.CENTER);
}

    /**
     * Cria o painel inferior com os botões principais.
     */
    private void criarPainelBotoes() {
    JPanel buttonPanel = new JPanel();

    JButton manageButton = new JButton("Gerenciar RIT");
    JButton addButton = new JButton("Adicionar professor");
    JButton editButton = new JButton("Editar");
    JButton deleteButton = new JButton("Excluir");

    manageButton.addActionListener(event -> gerenciarRIT());
    addButton.addActionListener(event -> adicionarProfessor());
    editButton.addActionListener(event -> editarProfessor());
    deleteButton.addActionListener(event -> excluirProfessor());

    buttonPanel.add(manageButton);
    buttonPanel.add(addButton);
    buttonPanel.add(editButton);
    buttonPanel.add(deleteButton);

    add(buttonPanel, BorderLayout.SOUTH);
}

    /**
 * Abre o gerenciamento de RIT do professor selecionado.
 *
 * <p>Por enquanto, esta ação apenas confirma o professor selecionado.
 * Na próxima etapa, abrirá a tela de RIT.</p>
 */
/**
 * Abre o gerenciamento de RIT do professor selecionado.
 */
private void gerenciarRIT() {
    int selectedRow = professorTable.getSelectedRow();

    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(
            this,
            "Selecione um professor para gerenciar o RIT."
        );
        return;
    }

    int professorId = (int) tableModel.getValueAt(selectedRow, 0);

    try {
        Professor professor = professorDAO.buscarPorId(professorId);

        if (professor == null) {
            JOptionPane.showMessageDialog(
                this,
                "Professor não encontrado.",
                "Erro",
                JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        RitFrame ritFrame = new RitFrame(professor);
        ritFrame.setVisible(true);

    } catch (SQLException exception) {
        JOptionPane.showMessageDialog(
            this,
            "Erro ao abrir gerenciamento de RIT.",
            "Erro",
            JOptionPane.ERROR_MESSAGE
        );

        exception.printStackTrace();
    }
}

    /**
     * Carrega os professores salvos no banco e atualiza a tabela.
     */
    private void carregarProfessores() {
        try {
            tableModel.setRowCount(0);

            List<Professor> professores = professorDAO.listar();

            for (Professor professor : professores) {
                Object[] row = {
                        professor.getId(),
                        professor.getNome(),
                        professor.getEmail(),
                        professor.getDepartamento()
                };

                tableModel.addRow(row);
            }
        } catch (SQLException exception) {
            JOptionPane.showMessageDialog(
                    this,
                    "Erro ao carregar professores.",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);

            exception.printStackTrace();
        }
    }

    /**
     * Ação temporária para o botão de adicionar professor.
     *
     * <p>
     * Na próxima etapa, essa ação abrirá um formulário de cadastro.
     * </p>
     */
    /**
 * Abre o formulário para cadastrar um novo professor.
 */
private void adicionarProfessor() {
    ProfessorFormDialog dialog = new ProfessorFormDialog(this, null);
    dialog.setVisible(true);

    if (dialog.isSaved()) {
        carregarProfessores();

        JOptionPane.showMessageDialog(
            this,
            "Professor cadastrado com sucesso."
        );
    }
}

    /**
     * Ação temporária para o botão de editar professor.
     *
     * <p>
     * Na próxima etapa, essa ação abrirá um formulário preenchido com os dados
     * do professor selecionado.
     * </p>
     */
    /**
 * Abre o formulário para editar o professor selecionado.
 */
private void editarProfessor() {
    int selectedRow = professorTable.getSelectedRow();

    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(
            this,
            "Selecione um professor para editar."
        );
        return;
    }

    int professorId = (int) tableModel.getValueAt(selectedRow, 0);

    try {
        Professor professor = professorDAO.buscarPorId(professorId);

        if (professor == null) {
            JOptionPane.showMessageDialog(
                this,
                "Professor não encontrado.",
                "Erro",
                JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        ProfessorFormDialog dialog = new ProfessorFormDialog(this, professor);
        dialog.setVisible(true);

        if (dialog.isSaved()) {
            carregarProfessores();

            JOptionPane.showMessageDialog(
                this,
                "Professor atualizado com sucesso."
            );
        }

    } catch (SQLException exception) {
        JOptionPane.showMessageDialog(
            this,
            "Erro ao buscar professor.",
            "Erro",
            JOptionPane.ERROR_MESSAGE
        );

        exception.printStackTrace();
    }
}

    /**
     * Remove o professor selecionado da tabela e do banco de dados.
     */
    private void excluirProfessor() {
        int selectedRow = professorTable.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(
                    this,
                    "Selecione um professor para excluir.");
            return;
        }

        int professorId = (int) tableModel.getValueAt(selectedRow, 0);
        String professorNome = (String) tableModel.getValueAt(selectedRow, 1);

        int option = JOptionPane.showConfirmDialog(
                this,
                "Deseja realmente excluir o professor " + professorNome + "?",
                "Confirmar exclusão",
                JOptionPane.YES_NO_OPTION);

        if (option == JOptionPane.YES_OPTION) {
            try {
                professorDAO.remover(professorId);
                carregarProfessores();

                JOptionPane.showMessageDialog(
                        this,
                        "Professor excluído com sucesso.");
            } catch (SQLException exception) {
                JOptionPane.showMessageDialog(
                        this,
                        "Erro ao excluir professor.",
                        "Erro",
                        JOptionPane.ERROR_MESSAGE);

                exception.printStackTrace();
            }
        }
    }
}