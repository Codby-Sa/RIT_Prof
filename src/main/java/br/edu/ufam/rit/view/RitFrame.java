package br.edu.ufam.rit.view;

import br.edu.ufam.rit.model.Professor;
import br.edu.ufam.rit.dao.RITDAO;
import br.edu.ufam.rit.model.RIT;
import br.edu.ufam.rit.dao.DisciplinaDAO;
import br.edu.ufam.rit.model.Disciplina;
import br.edu.ufam.rit.dao.OrientacaoDAO;
import br.edu.ufam.rit.model.Orientacao;
import br.edu.ufam.rit.dao.ArtigoDAO;
import br.edu.ufam.rit.model.Artigo;
import br.edu.ufam.rit.dao.AtividadeCoordenacaoDAO;
import br.edu.ufam.rit.model.AtividadeCoordenacao;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.util.List;

import javax.swing.JOptionPane;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.BorderFactory;
import java.awt.BorderLayout;
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
    private ArtigoDAO artigoDAO;
    private JTable artigoTable;
    private DefaultTableModel artigoTableModel;
    private AtividadeCoordenacaoDAO atividadeCoordenacaoDAO;
    private JTable coordenacaoTable;
    private DefaultTableModel coordenacaoTableModel;

    private JTextField semestreField;
    private JTextField anoField;
    private OrientacaoDAO orientacaoDAO;
    private JTable orientacaoTable;
    private DefaultTableModel orientacaoTableModel;

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
        this.orientacaoDAO = new OrientacaoDAO();
        this.artigoDAO = new ArtigoDAO();
        this.atividadeCoordenacaoDAO = new AtividadeCoordenacaoDAO();

        configurarJanela();
        criarComponentes();
        carregarRITExistente();

    }

    /**
     * Configura as propriedades principais da janela.
     */
    private void configurarJanela() {
        setSize(1000, 750);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(16, 16));
        getContentPane().setBackground(AppTheme.BACKGROUND);
    }

    /**
     * Cria e organiza os componentes principais da tela.
     */
    private void criarComponentes() {
        JPanel mainPanel = new JPanel(new BorderLayout(12, 12));
        mainPanel.setBackground(AppTheme.BACKGROUND);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        mainPanel.add(criarCabecalho(), BorderLayout.NORTH);
        mainPanel.add(criarAbas(), BorderLayout.CENTER);
        mainPanel.add(criarPainelInferior(), BorderLayout.SOUTH);

        add(mainPanel, BorderLayout.CENTER);
    }

    /**
     * Cria o cabeçalho com os dados do professor e os campos do semestre.
     *
     * @return painel de cabeçalho
     */
    private JPanel criarCabecalho() {
        JPanel headerCard = UIUtils.createCardPanel();
        headerCard.setLayout(new BorderLayout(10, 14));

        JLabel titleLabel = new JLabel("Relatório Individual de Trabalho");
        titleLabel.setFont(AppTheme.TITLE_FONT);
        titleLabel.setForeground(AppTheme.PRIMARY_DARK);

        JLabel subtitleLabel = new JLabel("Gerenciamento das atividades do professor no período selecionado.");
        subtitleLabel.setFont(AppTheme.SUBTITLE_FONT);
        subtitleLabel.setForeground(AppTheme.MUTED_TEXT);

        JPanel titlePanel = new JPanel(new BorderLayout(4, 4));
        titlePanel.setBackground(AppTheme.CARD_BACKGROUND);
        titlePanel.add(titleLabel, BorderLayout.NORTH);
        titlePanel.add(subtitleLabel, BorderLayout.SOUTH);

        JPanel infoPanel = new JPanel(new GridLayout(5, 2, 8, 8));
        infoPanel.setBackground(AppTheme.CARD_BACKGROUND);

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

        estilizarLabel(nomeLabel);
        estilizarLabel(emailLabel);
        estilizarLabel(departamentoLabel);
        estilizarLabel(semestreLabel);
        estilizarLabel(anoLabel);

        estilizarValor(nomeValueLabel);
        estilizarValor(emailValueLabel);
        estilizarValor(departamentoValueLabel);

        estilizarCampo(semestreField);
        estilizarCampo(anoField);

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

        headerCard.add(titlePanel, BorderLayout.NORTH);
        headerCard.add(infoPanel, BorderLayout.CENTER);

        return headerCard;
    }

    /**
     * Aplica estilo nos rótulos dos campos.
     *
     * @param label rótulo
     */
    private void estilizarLabel(JLabel label) {
        label.setFont(AppTheme.BUTTON_FONT);
        label.setForeground(AppTheme.TEXT);
    }

    /**
     * Aplica estilo nos valores exibidos no cabeçalho.
     *
     * @param label valor
     */
    private void estilizarValor(JLabel label) {
        label.setFont(AppTheme.BODY_FONT);
        label.setForeground(AppTheme.MUTED_TEXT);
    }

    /**
     * Aplica estilo nos campos de texto.
     *
     * @param field campo de texto
     */
    private void estilizarCampo(JTextField field) {
        field.setFont(AppTheme.BODY_FONT);
        field.setForeground(AppTheme.TEXT);
        field.setBackground(AppTheme.CARD_BACKGROUND);
        field.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(AppTheme.BORDER),
                        BorderFactory.createEmptyBorder(6, 8, 6, 8)));
    }

    /**
     * Cria as abas de atividades do RIT.
     *
     * @return painel com abas
     */
    private JPanel criarAbas() {
        JPanel tabsCard = UIUtils.createCardPanel();
        tabsCard.setLayout(new BorderLayout(10, 10));

        JLabel sectionTitle = new JLabel("Atividades do RIT");
        sectionTitle.setFont(AppTheme.SECTION_TITLE_FONT);
        sectionTitle.setForeground(AppTheme.TEXT);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(AppTheme.BUTTON_FONT);
        tabbedPane.setBackground(AppTheme.CARD_BACKGROUND);
        tabbedPane.setForeground(AppTheme.TEXT);

        tabbedPane.addTab("Disciplinas", criarPainelDisciplinas());
        tabbedPane.addTab("Orientações", criarPainelOrientacoes());
        tabbedPane.addTab("Artigos", criarPainelArtigos());
        tabbedPane.addTab("Coordenação", criarPainelCoordenacoes());

        tabsCard.add(sectionTitle, BorderLayout.NORTH);
        tabsCard.add(tabbedPane, BorderLayout.CENTER);

        return tabsCard;
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
     *
     * @return painel inferior
     */
    private JPanel criarPainelInferior() {
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(AppTheme.BACKGROUND);

        JButton reportButton = new JButton("Visualizar Relatório");
        JButton backButton = new JButton("Fechar");

        UIUtils.stylePrimaryButton(reportButton);
        UIUtils.styleSecondaryButton(backButton);

        reportButton.addActionListener(event -> gerarRelatorio());
        backButton.addActionListener(event -> dispose());

        bottomPanel.add(reportButton);
        bottomPanel.add(backButton);

        return bottomPanel;
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
    /**
     * Gera e abre a tela final do relatório do professor.
     */
    private void gerarRelatorio() {
        boolean ritDisponivel = obterOuCriarRIT();

        if (!ritDisponivel) {
            return;
        }

        try {
            List<Disciplina> disciplinas = disciplinaDAO.listarPorRit(rit.getId());
            List<Orientacao> orientacoes = orientacaoDAO.listarPorRit(rit.getId());
            List<Artigo> artigos = artigoDAO.listarPorRit(rit.getId());
            List<AtividadeCoordenacao> coordenacoes = atividadeCoordenacaoDAO.listarPorRit(rit.getId());

            carregarDisciplinas();
            carregarOrientacoes();
            carregarArtigos();
            carregarCoordenacoes();

            RelatorioFrame relatorioFrame = new RelatorioFrame(
                    professor,
                    rit,
                    disciplinas,
                    orientacoes,
                    artigos,
                    coordenacoes);

            relatorioFrame.setVisible(true);

        } catch (SQLException exception) {
            JOptionPane.showMessageDialog(
                    this,
                    "Erro ao gerar relatório.",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);

            exception.printStackTrace();
        }
    }

    /**
     * Cria o painel da aba de disciplinas.
     *
     * @return painel de disciplinas
     */
    private JPanel criarPainelDisciplinas() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setBackground(AppTheme.CARD_BACKGROUND);

        String[] columns = { "ID", "Nome", "Código", "Carga horária", "Curso" };

        disciplinaTableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        disciplinaTable = new JTable(disciplinaTableModel);
        UIUtils.styleTable(disciplinaTable);
        UIUtils.hideColumn(disciplinaTable, 0);
        JScrollPane scrollPane = new JScrollPane(disciplinaTable);

        JPanel buttonPanel = new JPanel();

        JButton addButton = new JButton("Adicionar Disciplina");
        JButton editButton = new JButton("Editar Disciplina");
        JButton deleteButton = new JButton("Excluir Disciplina");

        UIUtils.stylePrimaryButton(addButton);
        UIUtils.styleSecondaryButton(editButton);
        UIUtils.styleDangerButton(deleteButton);

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
                carregarOrientacoes();
                carregarArtigos();
                carregarCoordenacoes();
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

    /**
     * Cria o painel da aba de orientações.
     *
     * @return painel de orientações
     */
    private JPanel criarPainelOrientacoes() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setBackground(AppTheme.CARD_BACKGROUND);

        String[] columns = { "ID", "Aluno", "Tipo", "Título do trabalho", "Curso" };

        orientacaoTableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        orientacaoTable = new JTable(orientacaoTableModel);
        UIUtils.styleTable(orientacaoTable);
        UIUtils.hideColumn(orientacaoTable, 0);

        orientacaoTable.getColumnModel().getColumn(0).setMaxWidth(0);
        orientacaoTable.getColumnModel().getColumn(0).setWidth(0);

        JScrollPane scrollPane = new JScrollPane(orientacaoTable);

        JPanel buttonPanel = new JPanel();

        JButton addButton = new JButton("Adicionar Orientação");
        JButton editButton = new JButton("Editar Orientação");
        JButton deleteButton = new JButton("Excluir Orientação");

        UIUtils.stylePrimaryButton(addButton);
        UIUtils.styleSecondaryButton(editButton);
        UIUtils.styleDangerButton(deleteButton);
        addButton.addActionListener(event -> adicionarOrientacao());
        editButton.addActionListener(event -> editarOrientacao());
        deleteButton.addActionListener(event -> excluirOrientacao());

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);

        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    /**
     * Carrega as orientações do RIT atual na tabela.
     */
    private void carregarOrientacoes() {
        if (rit == null) {
            orientacaoTableModel.setRowCount(0);
            return;
        }

        try {
            orientacaoTableModel.setRowCount(0);

            List<Orientacao> orientacoes = orientacaoDAO.listarPorRit(rit.getId());

            for (Orientacao orientacao : orientacoes) {
                Object[] row = {
                        orientacao.getId(),
                        orientacao.getNomeAluno(),
                        orientacao.getTipo(),
                        orientacao.getTituloTrabalho(),
                        orientacao.getCurso()
                };

                orientacaoTableModel.addRow(row);
            }

        } catch (SQLException exception) {
            JOptionPane.showMessageDialog(
                    this,
                    "Erro ao carregar orientações.",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);

            exception.printStackTrace();
        }
    }

    /**
     * Abre o formulário para adicionar uma orientação ao RIT atual.
     */
    private void adicionarOrientacao() {
        boolean ritDisponivel = obterOuCriarRIT();

        if (!ritDisponivel) {
            return;
        }

        OrientacaoFormDialog dialog = new OrientacaoFormDialog(this, rit.getId(), null);
        dialog.setVisible(true);

        if (dialog.isSaved()) {
            carregarOrientacoes();

            JOptionPane.showMessageDialog(
                    this,
                    "Orientação cadastrada com sucesso.");
        }
    }

    /**
     * Abre o formulário para editar a orientação selecionada.
     */
    private void editarOrientacao() {
        boolean ritDisponivel = obterOuCriarRIT();

        if (!ritDisponivel) {
            return;
        }

        int selectedRow = orientacaoTable.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(
                    this,
                    "Selecione uma orientação para editar.");
            return;
        }

        int orientacaoId = (int) orientacaoTableModel.getValueAt(selectedRow, 0);

        try {
            Orientacao orientacao = orientacaoDAO.buscarPorId(orientacaoId);

            if (orientacao == null) {
                JOptionPane.showMessageDialog(
                        this,
                        "Orientação não encontrada.",
                        "Erro",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            OrientacaoFormDialog dialog = new OrientacaoFormDialog(this, rit.getId(), orientacao);
            dialog.setVisible(true);

            if (dialog.isSaved()) {
                carregarOrientacoes();

                JOptionPane.showMessageDialog(
                        this,
                        "Orientação atualizada com sucesso.");
            }

        } catch (SQLException exception) {
            JOptionPane.showMessageDialog(
                    this,
                    "Erro ao buscar orientação.",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);

            exception.printStackTrace();
        }
    }

    /**
     * Remove a orientação selecionada.
     */
    private void excluirOrientacao() {
        boolean ritDisponivel = obterOuCriarRIT();

        if (!ritDisponivel) {
            return;
        }

        int selectedRow = orientacaoTable.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(
                    this,
                    "Selecione uma orientação para excluir.");
            return;
        }

        int orientacaoId = (int) orientacaoTableModel.getValueAt(selectedRow, 0);
        String nomeAluno = (String) orientacaoTableModel.getValueAt(selectedRow, 1);

        int option = JOptionPane.showConfirmDialog(
                this,
                "Deseja realmente excluir a orientação do aluno " + nomeAluno + "?",
                "Confirmar exclusão",
                JOptionPane.YES_NO_OPTION);

        if (option == JOptionPane.YES_OPTION) {
            try {
                orientacaoDAO.remover(orientacaoId);
                carregarOrientacoes();

                JOptionPane.showMessageDialog(
                        this,
                        "Orientação excluída com sucesso.");

            } catch (SQLException exception) {
                JOptionPane.showMessageDialog(
                        this,
                        "Erro ao excluir orientação.",
                        "Erro",
                        JOptionPane.ERROR_MESSAGE);

                exception.printStackTrace();
            }
        }
    }

    /**
     * Cria o painel da aba de artigos.
     *
     * @return painel de artigos
     */
    private JPanel criarPainelArtigos() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setBackground(AppTheme.CARD_BACKGROUND);

        String[] columns = { "ID", "Título", "Autores", "Evento/Revista", "Ano" };

        artigoTableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        artigoTable = new JTable(artigoTableModel);
        UIUtils.styleTable(artigoTable);
        UIUtils.hideColumn(artigoTable, 0);
        JScrollPane scrollPane = new JScrollPane(artigoTable);

        JPanel buttonPanel = new JPanel();

        JButton addButton = new JButton("Adicionar Artigo");
        JButton editButton = new JButton("Editar Artigo");
        JButton deleteButton = new JButton("Excluir Artigo");

        UIUtils.stylePrimaryButton(addButton);
        UIUtils.styleSecondaryButton(editButton);
        UIUtils.styleDangerButton(deleteButton);
        addButton.addActionListener(event -> adicionarArtigo());
        editButton.addActionListener(event -> editarArtigo());
        deleteButton.addActionListener(event -> excluirArtigo());

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);

        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    /**
     * Carrega os artigos do RIT atual na tabela.
     */
    private void carregarArtigos() {
        if (rit == null) {
            artigoTableModel.setRowCount(0);
            return;
        }

        try {
            artigoTableModel.setRowCount(0);

            List<Artigo> artigos = artigoDAO.listarPorRit(rit.getId());

            for (Artigo artigo : artigos) {
                Object[] row = {
                        artigo.getId(),
                        artigo.getTitulo(),
                        artigo.getAutores(),
                        artigo.getNomeEventoOuRevista(),
                        artigo.getAnoPublicacao()
                };

                artigoTableModel.addRow(row);
            }

        } catch (SQLException exception) {
            JOptionPane.showMessageDialog(
                    this,
                    "Erro ao carregar artigos.",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);

            exception.printStackTrace();
        }
    }

    /**
     * Abre o formulário para adicionar um artigo ao RIT atual.
     */
    private void adicionarArtigo() {
        boolean ritDisponivel = obterOuCriarRIT();

        if (!ritDisponivel) {
            return;
        }

        ArtigoFormDialog dialog = new ArtigoFormDialog(this, rit.getId(), null);
        dialog.setVisible(true);

        if (dialog.isSaved()) {
            carregarArtigos();

            JOptionPane.showMessageDialog(
                    this,
                    "Artigo cadastrado com sucesso.");
        }
    }

    /**
     * Abre o formulário para editar o artigo selecionado.
     */
    private void editarArtigo() {
        boolean ritDisponivel = obterOuCriarRIT();

        if (!ritDisponivel) {
            return;
        }

        int selectedRow = artigoTable.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(
                    this,
                    "Selecione um artigo para editar.");
            return;
        }

        int artigoId = (int) artigoTableModel.getValueAt(selectedRow, 0);

        try {
            Artigo artigo = artigoDAO.buscarPorId(artigoId);

            if (artigo == null) {
                JOptionPane.showMessageDialog(
                        this,
                        "Artigo não encontrado.",
                        "Erro",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            ArtigoFormDialog dialog = new ArtigoFormDialog(this, rit.getId(), artigo);
            dialog.setVisible(true);

            if (dialog.isSaved()) {
                carregarArtigos();

                JOptionPane.showMessageDialog(
                        this,
                        "Artigo atualizado com sucesso.");
            }

        } catch (SQLException exception) {
            JOptionPane.showMessageDialog(
                    this,
                    "Erro ao buscar artigo.",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);

            exception.printStackTrace();
        }
    }

    /**
     * Remove o artigo selecionado.
     */
    private void excluirArtigo() {
        boolean ritDisponivel = obterOuCriarRIT();

        if (!ritDisponivel) {
            return;
        }

        int selectedRow = artigoTable.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(
                    this,
                    "Selecione um artigo para excluir.");
            return;
        }

        int artigoId = (int) artigoTableModel.getValueAt(selectedRow, 0);
        String tituloArtigo = (String) artigoTableModel.getValueAt(selectedRow, 1);

        int option = JOptionPane.showConfirmDialog(
                this,
                "Deseja realmente excluir o artigo " + tituloArtigo + "?",
                "Confirmar exclusão",
                JOptionPane.YES_NO_OPTION);

        if (option == JOptionPane.YES_OPTION) {
            try {
                artigoDAO.remover(artigoId);
                carregarArtigos();

                JOptionPane.showMessageDialog(
                        this,
                        "Artigo excluído com sucesso.");

            } catch (SQLException exception) {
                JOptionPane.showMessageDialog(
                        this,
                        "Erro ao excluir artigo.",
                        "Erro",
                        JOptionPane.ERROR_MESSAGE);

                exception.printStackTrace();
            }
        }
    }

    /**
     * Cria o painel da aba de atividades de coordenação.
     *
     * @return painel de atividades de coordenação
     */
    private JPanel criarPainelCoordenacoes() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setBackground(AppTheme.CARD_BACKGROUND);

        String[] columns = { "ID", "Descrição", "Cargo/Função", "Carga horária" };

        coordenacaoTableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        coordenacaoTable = new JTable(coordenacaoTableModel);
        UIUtils.styleTable(coordenacaoTable);
        UIUtils.hideColumn(coordenacaoTable, 0);
        JScrollPane scrollPane = new JScrollPane(coordenacaoTable);

        JPanel buttonPanel = new JPanel();

        JButton addButton = new JButton("Adicionar Atividade");
        JButton editButton = new JButton("Editar Atividade");
        JButton deleteButton = new JButton("Excluir Atividade");

        UIUtils.stylePrimaryButton(addButton);
        UIUtils.styleSecondaryButton(editButton);
        UIUtils.styleDangerButton(deleteButton);
        addButton.addActionListener(event -> adicionarCoordenacao());
        editButton.addActionListener(event -> editarCoordenacao());
        deleteButton.addActionListener(event -> excluirCoordenacao());

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);

        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    /**
     * Carrega as atividades de coordenação do RIT atual na tabela.
     */
    private void carregarCoordenacoes() {
        if (rit == null) {
            coordenacaoTableModel.setRowCount(0);
            return;
        }

        try {
            coordenacaoTableModel.setRowCount(0);

            List<AtividadeCoordenacao> atividades = atividadeCoordenacaoDAO.listarPorRit(rit.getId());

            for (AtividadeCoordenacao atividade : atividades) {
                Object[] row = {
                        atividade.getId(),
                        atividade.getDescricao(),
                        atividade.getCargo(),
                        atividade.getCargaHoraria()
                };

                coordenacaoTableModel.addRow(row);
            }

        } catch (SQLException exception) {
            JOptionPane.showMessageDialog(
                    this,
                    "Erro ao carregar atividades de coordenação.",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);

            exception.printStackTrace();
        }
    }

    /**
     * Abre o formulário para adicionar uma atividade de coordenação ao RIT atual.
     */
    private void adicionarCoordenacao() {
        boolean ritDisponivel = obterOuCriarRIT();

        if (!ritDisponivel) {
            return;
        }

        CoordenacaoFormDialog dialog = new CoordenacaoFormDialog(this, rit.getId(), null);
        dialog.setVisible(true);

        if (dialog.isSaved()) {
            carregarCoordenacoes();

            JOptionPane.showMessageDialog(
                    this,
                    "Atividade de coordenação cadastrada com sucesso.");
        }
    }

    /**
     * Abre o formulário para editar a atividade de coordenação selecionada.
     */
    private void editarCoordenacao() {
        boolean ritDisponivel = obterOuCriarRIT();

        if (!ritDisponivel) {
            return;
        }

        int selectedRow = coordenacaoTable.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(
                    this,
                    "Selecione uma atividade de coordenação para editar.");
            return;
        }

        int atividadeId = (int) coordenacaoTableModel.getValueAt(selectedRow, 0);

        try {
            AtividadeCoordenacao atividade = atividadeCoordenacaoDAO.buscarPorId(atividadeId);

            if (atividade == null) {
                JOptionPane.showMessageDialog(
                        this,
                        "Atividade de coordenação não encontrada.",
                        "Erro",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            CoordenacaoFormDialog dialog = new CoordenacaoFormDialog(this, rit.getId(), atividade);
            dialog.setVisible(true);

            if (dialog.isSaved()) {
                carregarCoordenacoes();

                JOptionPane.showMessageDialog(
                        this,
                        "Atividade de coordenação atualizada com sucesso.");
            }

        } catch (SQLException exception) {
            JOptionPane.showMessageDialog(
                    this,
                    "Erro ao buscar atividade de coordenação.",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);

            exception.printStackTrace();
        }
    }

    /**
     * Remove a atividade de coordenação selecionada.
     */
    private void excluirCoordenacao() {
        boolean ritDisponivel = obterOuCriarRIT();

        if (!ritDisponivel) {
            return;
        }

        int selectedRow = coordenacaoTable.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(
                    this,
                    "Selecione uma atividade de coordenação para excluir.");
            return;
        }

        int atividadeId = (int) coordenacaoTableModel.getValueAt(selectedRow, 0);
        String descricao = (String) coordenacaoTableModel.getValueAt(selectedRow, 1);

        int option = JOptionPane.showConfirmDialog(
                this,
                "Deseja realmente excluir a atividade " + descricao + "?",
                "Confirmar exclusão",
                JOptionPane.YES_NO_OPTION);

        if (option == JOptionPane.YES_OPTION) {
            try {
                atividadeCoordenacaoDAO.remover(atividadeId);
                carregarCoordenacoes();

                JOptionPane.showMessageDialog(
                        this,
                        "Atividade de coordenação excluída com sucesso.");

            } catch (SQLException exception) {
                JOptionPane.showMessageDialog(
                        this,
                        "Erro ao excluir atividade de coordenação.",
                        "Erro",
                        JOptionPane.ERROR_MESSAGE);

                exception.printStackTrace();
            }
        }
    }
}