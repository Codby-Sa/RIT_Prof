package br.edu.ufam.rit.view;

import br.edu.ufam.rit.model.Professor;

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
 * <p>Esta tela exibe os dados do professor selecionado e organiza
 * as atividades do relatório em abas.</p>
 */
public class RitFrame extends JFrame {

    private Professor professor;

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

        configurarJanela();
        criarComponentes();
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

        tabbedPane.addTab("Disciplinas", criarPainelTemporario("Aqui ficará o cadastro de disciplinas ministradas."));
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
    private void gerarRelatorio() {
        String semestre = semestreField.getText().trim();
        String ano = anoField.getText().trim();

        javax.swing.JOptionPane.showMessageDialog(
            this,
            "Relatório do professor " + professor.getNome()
                + "\nSemestre: " + semestre
                + "\nAno: " + ano
        );
    }
}