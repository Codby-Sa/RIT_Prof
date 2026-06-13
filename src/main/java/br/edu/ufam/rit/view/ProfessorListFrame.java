package br.edu.ufam.rit.view;

import br.edu.ufam.rit.dao.ProfessorDAO;
import br.edu.ufam.rit.model.Professor;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
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
    /*
     * private void configurarJanela() {
     * setSize(800, 500);
     * setLocationRelativeTo(null);
     * setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
     * setLayout(new BorderLayout(10, 10));
     * }
     */
    /**
     * Configura as propriedades principais da janela.
     */
    private void configurarJanela() {
        setSize(900, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(16, 16));
        getContentPane().setBackground(AppTheme.BACKGROUND);
    }

    /**
     * Cria e organiza os componentes visuais da tela.
     */
    /**
 * Cria e organiza os componentes visuais da tela.
 */
private void criarComponentes() {
    JPanel mainPanel = new JPanel(new BorderLayout(12, 12));
    mainPanel.setBackground(AppTheme.BACKGROUND);
    mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

    JPanel headerPanel = criarCabecalho();

    JPanel contentCard = UIUtils.createCardPanel();
    contentCard.setLayout(new BorderLayout(10, 10));

    JLabel tableTitleLabel = new JLabel("Professores cadastrados");
    tableTitleLabel.setFont(AppTheme.SECTION_TITLE_FONT);
    tableTitleLabel.setForeground(AppTheme.TEXT);

    criarTabela();

    contentCard.add(tableTitleLabel, BorderLayout.NORTH);
    contentCard.add(new JScrollPane(professorTable), BorderLayout.CENTER);

    mainPanel.add(headerPanel, BorderLayout.NORTH);
    mainPanel.add(contentCard, BorderLayout.CENTER);
    mainPanel.add(criarPainelBotoes(), BorderLayout.SOUTH);

    add(mainPanel, BorderLayout.CENTER);
}
/**
 * Cria o cabeçalho da tela inicial.
 *
 * @return painel do cabeçalho
 */
private JPanel criarCabecalho() {
    JPanel headerPanel = new JPanel(new BorderLayout(4, 4));
    headerPanel.setBackground(AppTheme.BACKGROUND);

    JLabel titleLabel = new JLabel("Sistema de Gerenciamento de RIT");
    titleLabel.setFont(AppTheme.TITLE_FONT);
    titleLabel.setForeground(AppTheme.PRIMARY_DARK);

    JLabel subtitleLabel = new JLabel("Gerencie professores e seus relatórios individuais de trabalho.");
    subtitleLabel.setFont(AppTheme.SUBTITLE_FONT);
    subtitleLabel.setForeground(AppTheme.MUTED_TEXT);

    headerPanel.add(titleLabel, BorderLayout.NORTH);
    headerPanel.add(subtitleLabel, BorderLayout.SOUTH);

    return headerPanel;
}

    /**
     * Cria a tabela responsável por exibir os professores.
     */
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
    UIUtils.styleTable(professorTable);
    UIUtils.hideColumn(professorTable, 0);
}

    /**
     * Cria o painel inferior com os botões principais.
     */
    /**
 * Cria o painel inferior com os botões principais.
 *
 * @return painel de botões
 */
private JPanel criarPainelBotoes() {
    JPanel buttonPanel = new JPanel();
    buttonPanel.setBackground(AppTheme.BACKGROUND);

    JButton addButton = new JButton("Novo Professor");
    JButton manageButton = new JButton("Abrir RIT");
    JButton editButton = new JButton("Editar Professor");
    JButton deleteButton = new JButton("Excluir Professor");

    UIUtils.stylePrimaryButton(addButton);
    UIUtils.stylePrimaryButton(manageButton);
    UIUtils.styleSecondaryButton(editButton);
    UIUtils.styleDangerButton(deleteButton);

    addButton.addActionListener(event -> adicionarProfessor());
    manageButton.addActionListener(event -> gerenciarRIT());
    editButton.addActionListener(event -> editarProfessor());
    deleteButton.addActionListener(event -> excluirProfessor());

    buttonPanel.add(addButton);
    buttonPanel.add(manageButton);
    buttonPanel.add(editButton);
    buttonPanel.add(deleteButton);

    return buttonPanel;
}

    /**
     * Abre o gerenciamento de RIT do professor selecionado.
     *
     * <p>
     * Por enquanto, esta ação apenas confirma o professor selecionado.
     * Na próxima etapa, abrirá a tela de RIT.
     * </p>
     */
    /**
     * Abre o gerenciamento de RIT do professor selecionado.
     */
    private void gerenciarRIT() {
        int selectedRow = professorTable.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(
                    this,
                    "Selecione um professor para gerenciar o RIT.");
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
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            RitFrame ritFrame = new RitFrame(professor);
            ritFrame.setVisible(true);

        } catch (SQLException exception) {
            JOptionPane.showMessageDialog(
                    this,
                    "Erro ao abrir gerenciamento de RIT.",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);

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
                    "Professor cadastrado com sucesso.");
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
                    "Selecione um professor para editar.");
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
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            ProfessorFormDialog dialog = new ProfessorFormDialog(this, professor);
            dialog.setVisible(true);

            if (dialog.isSaved()) {
                carregarProfessores();

                JOptionPane.showMessageDialog(
                        this,
                        "Professor atualizado com sucesso.");
            }

        } catch (SQLException exception) {
            JOptionPane.showMessageDialog(
                    this,
                    "Erro ao buscar professor.",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);

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