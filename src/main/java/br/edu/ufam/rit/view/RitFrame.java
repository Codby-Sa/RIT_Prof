package br.edu.ufam.rit.view;

import br.edu.ufam.rit.model.Professor;
import br.edu.ufam.rit.dao.RITDAO;
import br.edu.ufam.rit.model.RIT;
import br.edu.ufam.rit.dao.DisciplinaDAO;
import br.edu.ufam.rit.model.Disciplina;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.ListSelectionModel;
import java.util.List;

import javax.swing.JOptionPane;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.BorderFactory;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;

/**
 * Tela responsável por gerenciar o RIT de um professor.
 *
 * <p>
 * Esta tela exibe os dados do professor selecionado e organiza
 * as atividades do relatório em abas.
 * </p>
 */
public class RitFrame extends JFrame {

    private Professor professor;
    private RIT rit;
    private RITDAO ritDAO;
    private DisciplinaDAO disciplinaDAO;
    private JTable disciplinaTable;
    private DefaultTableModel disciplinaTableModel;

    private JTextField semestreField;
    private JTextField anoField;

    /**
     * Cria a tela de gerenciamento de RIT.
     *
     * @param professor professor selecionado na tela inicial
     */
    public RitFrame(Professor professor) {
        super("Gerenciamento de RIT");

        this.professor = professor;
        this.ritDAO = new RITDAO();
        this.disciplinaDAO = new DisciplinaDAO();

        configurarJanela();
        criarComponentes();
        carregarRITExistente();

    }

    /**
     * Configura as propriedades principais da janela.
     */
    private void configurarJanela() {
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
    }

    /**
     * Cria e organiza os componentes principais da tela.
     */
    private void criarComponentes() {
        criarCabecalho();
        criarAbas();
        criarPainelInferior();
    }

    /**
     * Cria o cabeçalho com os dados do professor e os campos do semestre.
     */
    private void criarCabecalho() {
        JPanel headerPanel = new JPanel(new BorderLayout(10, 10));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 10, 15));

        JLabel titleLabel = new JLabel("Relatório Individual de Trabalho", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));

        JPanel infoPanel = new JPanel(new GridLayout(5, 2, 8, 8));

        JLabel nomeLabel = new JLabel("Professor:");
        JLabel nomeValueLabel = new JLabel(professor.getNome());

        JLabel emailLabel = new JLabel("Email:");
        JLabel emailValueLabel = new JLabel(professor.getEmail());

        JLabel departamentoLabel = new JLabel("Departamento:");
        JLabel departamentoValueLabel = new JLabel(professor.getDepartamento());

        JLabel semestreLabel = new JLabel("Semestre:");
        semestreField = new JTextField("2026/1");

        JLabel anoLabel = new JLabel("Ano:");
        anoField = new JTextField("2026");

        infoPanel.add(nomeLabel);
        infoPanel.add(nomeValueLabel);

        infoPanel.add(emailLabel);
        infoPanel.add(emailValueLabel);

        infoPanel.add(departamentoLabel);
        infoPanel.add(departamentoValueLabel);

        infoPanel.add(semestreLabel);
        infoPanel.add(semestreField);

        infoPanel.add(anoLabel);
        infoPanel.add(anoField);

        headerPanel.add(titleLabel, BorderLayout.NORTH);
        headerPanel.add(infoPanel, BorderLayout.CENTER);

        add(headerPanel, BorderLayout.NORTH);
    }

    /**
     * Cria as abas de atividades do RIT.
     */
    private void criarAbas() {
        JTabbedPane tabbedPane = new JTabbedPane();

        tabbedPane.addTab("Disciplinas", criarPainelDisciplinas());
        tabbedPane.addTab("Orientações", criarPainelTemporario("Aqui ficará o cadastro de orientações."));
        tabbedPane.addTab("Artigos", criarPainelTemporario("Aqui ficará o cadastro de artigos publicados."));
        tabbedPane.addTab("Coordenação", criarPainelTemporario("Aqui ficará o cadastro de atividades de coordenação."));

        add(tabbedPane, BorderLayout.CENTER);
    }

    /**
     * Cria um painel temporário para uma aba.
     *
     * @param mensagem mensagem exibida no painel
     * @return painel criado
     */
    private JPanel criarPainelTemporario(String mensagem) {
        JPanel panel = new JPanel(new BorderLayout());

        JLabel label = new JLabel(mensagem, SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.PLAIN, 16));

        panel.add(label, BorderLayout.CENTER);

        return panel;
    }

    /**
     * Busca um RIT existente para o professor, semestre e ano informados.
     * Caso não exista, cria um novo RIT no banco.
     *
     * @return true se o RIT foi obtido ou criado com sucesso, false caso contrário
     */
    private boolean obterOuCriarRIT() {
        String semestre = semestreField.getText().trim();
        String anoTexto = anoField.getText().trim();

        if (semestre.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "O semestre é obrigatório.",
                    "Validação",
                    JOptionPane.WARNING_MESSAGE);
            return false;
        }

        if (anoTexto.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "O ano é obrigatório.",
                    "Validação",
                    JOptionPane.WARNING_MESSAGE);
            return false;
        }

        int ano;

        try {
            ano = Integer.parseInt(anoTexto);
        } catch (NumberFormatException exception) {
            JOptionPane.showMessageDialog(
                    this,
                    "O ano deve ser um número inteiro.",
                    "Validação",
                    JOptionPane.WARNING_MESSAGE);
            return false;
        }

        try {
            RIT ritEncontrado = ritDAO.buscarPorProfessorSemestreAno(
                    professor.getId(),
                    semestre,
                    ano);

            if (ritEncontrado != null) {
                this.rit = ritEncontrado;
                return true;
            }

            RIT novoRIT = new RIT(
                    professor.getId(),
                    semestre,
                    ano);

            int novoId = ritDAO.inserir(novoRIT);
            novoRIT.setId(novoId);

            this.rit = novoRIT;

            return true;

        } catch (SQLException exception) {
            JOptionPane.showMessageDialog(
                    this,
                    "Erro ao obter ou criar RIT.",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);

            exception.printStackTrace();
            return false;
        }
    }

    /**
     * Cria o painel inferior com botões de ação da tela.
     */
    private void criarPainelInferior() {
        JPanel bottomPanel = new JPanel();

        JButton reportButton = new JButton("Gerar Relatório");
        JButton backButton = new JButton("Voltar");

        reportButton.addActionListener(event -> gerarRelatorio());
        backButton.addActionListener(event -> dispose());

        bottomPanel.add(reportButton);
        bottomPanel.add(backButton);

        add(bottomPanel, BorderLayout.SOUTH);
    }

    /**
     * Ação temporária para geração do relatório.
     */
    /**
     * Gera uma prévia temporária do relatório do professor.
     *
     * <p>
     * Antes de gerar o relatório, garante que exista um RIT salvo no banco.
     * </p>
     */
    private void gerarRelatorio() {
        boolean ritDisponivel = obterOuCriarRIT();

        if (!ritDisponivel) {
            return;
        }
        carregarDisciplinas();

        JOptionPane.showMessageDialog(
                this,
                "RIT pronto para o professor " + professor.getNome()
                        + "\nID do RIT: " + rit.getId()
                        + "\nSemestre: " + rit.getSemestre()
                        + "\nAno: " + rit.getAno());
    }

    /**
     * Cria o painel da aba de disciplinas.
     *
     * @return painel de disciplinas
     */
    private JPanel criarPainelDisciplinas() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] columns = { "ID", "Nome", "Código", "Carga horária", "Curso" };

        disciplinaTableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        disciplinaTable = new JTable(disciplinaTableModel);
        disciplinaTable.setRowHeight(26);
        disciplinaTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        disciplinaTable.setRowSelectionAllowed(true);
        disciplinaTable.setColumnSelectionAllowed(false);

        disciplinaTable.getColumnModel().getColumn(0).setMinWidth(0);
        disciplinaTable.getColumnModel().getColumn(0).setMaxWidth(0);
        disciplinaTable.getColumnModel().getColumn(0).setWidth(0);

        JScrollPane scrollPane = new JScrollPane(disciplinaTable);

        JPanel buttonPanel = new JPanel();

        JButton addButton = new JButton("Adicionar");
        JButton editButton = new JButton("Editar");
        JButton deleteButton = new JButton("Excluir");

        addButton.addActionListener(event -> adicionarDisciplina());
        editButton.addActionListener(event -> editarDisciplina());
        deleteButton.addActionListener(event -> excluirDisciplina());

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);

        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    /**
     * Carrega as disciplinas do RIT atual na tabela.
     */
    private void carregarDisciplinas() {
        if (rit == null) {
            disciplinaTableModel.setRowCount(0);
            return;
        }

        try {
            disciplinaTableModel.setRowCount(0);

            List<Disciplina> disciplinas = disciplinaDAO.listarPorRit(rit.getId());

            for (Disciplina disciplina : disciplinas) {
                Object[] row = {
                        disciplina.getId(),
                        disciplina.getNome(),
                        disciplina.getCodigo(),
                        disciplina.getCargaHoraria(),
                        disciplina.getCurso()
                };

                disciplinaTableModel.addRow(row);
            }

        } catch (SQLException exception) {
            JOptionPane.showMessageDialog(
                    this,
                    "Erro ao carregar disciplinas.",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);

            exception.printStackTrace();
        }
    }

    /**
     * Abre o formulário para adicionar uma disciplina ao RIT atual.
     */
    private void adicionarDisciplina() {
        boolean ritDisponivel = obterOuCriarRIT();

        if (!ritDisponivel) {
            return;
        }

        DisciplinaFormDialog dialog = new DisciplinaFormDialog(this, rit.getId(), null);
        dialog.setVisible(true);

        if (dialog.isSaved()) {
            carregarDisciplinas();

            JOptionPane.showMessageDialog(
                    this,
                    "Disciplina cadastrada com sucesso.");
        }
    }

    /**
     * Abre o formulário para editar a disciplina selecionada.
     */
    private void editarDisciplina() {
        boolean ritDisponivel = obterOuCriarRIT();

        if (!ritDisponivel) {
            return;
        }

        int selectedRow = disciplinaTable.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(
                    this,
                    "Selecione uma disciplina para editar.");
            return;
        }

        int disciplinaId = (int) disciplinaTableModel.getValueAt(selectedRow, 0);

        try {
            Disciplina disciplina = disciplinaDAO.buscarPorId(disciplinaId);

            if (disciplina == null) {
                JOptionPane.showMessageDialog(
                        this,
                        "Disciplina não encontrada.",
                        "Erro",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            DisciplinaFormDialog dialog = new DisciplinaFormDialog(this, rit.getId(), disciplina);
            dialog.setVisible(true);

            if (dialog.isSaved()) {
                carregarDisciplinas();

                JOptionPane.showMessageDialog(
                        this,
                        "Disciplina atualizada com sucesso.");
            }

        } catch (SQLException exception) {
            JOptionPane.showMessageDialog(
                    this,
                    "Erro ao buscar disciplina.",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);

            exception.printStackTrace();
        }
    }

    /**
     * Remove a disciplina selecionada.
     */
    private void excluirDisciplina() {
        boolean ritDisponivel = obterOuCriarRIT();

        if (!ritDisponivel) {
            return;
        }

        int selectedRow = disciplinaTable.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(
                    this,
                    "Selecione uma disciplina para excluir.");
            return;
        }

        int disciplinaId = (int) disciplinaTableModel.getValueAt(selectedRow, 0);
        String nomeDisciplina = (String) disciplinaTableModel.getValueAt(selectedRow, 1);

        int option = JOptionPane.showConfirmDialog(
                this,
                "Deseja realmente excluir a disciplina " + nomeDisciplina + "?",
                "Confirmar exclusão",
                JOptionPane.YES_NO_OPTION);

        if (option == JOptionPane.YES_OPTION) {
            try {
                disciplinaDAO.remover(disciplinaId);
                carregarDisciplinas();

                JOptionPane.showMessageDialog(
                        this,
                        "Disciplina excluída com sucesso.");

            } catch (SQLException exception) {
                JOptionPane.showMessageDialog(
                        this,
                        "Erro ao excluir disciplina.",
                        "Erro",
                        JOptionPane.ERROR_MESSAGE);

                exception.printStackTrace();
            }
        }
    }

    /**
     * Busca um RIT já existente para o professor, semestre e ano informados na
     * tela.
     *
     * <p>
     * Esse método é usado ao abrir a tela para carregar automaticamente
     * as informações já cadastradas, sem criar um novo RIT caso ele ainda não
     * exista.
     * </p>
     */
    private void carregarRITExistente() {
        String semestre = semestreField.getText().trim();
        String anoTexto = anoField.getText().trim();

        if (semestre.isEmpty() || anoTexto.isEmpty()) {
            return;
        }

        int ano;

        try {
            ano = Integer.parseInt(anoTexto);
        } catch (NumberFormatException exception) {
            return;
        }

        try {
            RIT ritEncontrado = ritDAO.buscarPorProfessorSemestreAno(
                    professor.getId(),
                    semestre,
                    ano);

            if (ritEncontrado != null) {
                this.rit = ritEncontrado;
                carregarDisciplinas();
            }

        } catch (SQLException exception) {
            JOptionPane.showMessageDialog(
                    this,
                    "Erro ao carregar RIT existente.",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);

            exception.printStackTrace();
        }
    }
}