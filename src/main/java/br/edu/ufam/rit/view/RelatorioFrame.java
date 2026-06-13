package br.edu.ufam.rit.view;

import br.edu.ufam.rit.model.Artigo;
import br.edu.ufam.rit.model.AtividadeCoordenacao;
import br.edu.ufam.rit.model.Disciplina;
import br.edu.ufam.rit.model.Orientacao;
import br.edu.ufam.rit.model.Professor;
import br.edu.ufam.rit.model.RIT;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Insets;
import java.util.List;

/**
 * Tela responsável por exibir o Relatório Individual de Trabalho completo.
 *
 * <p>
 * Esta tela consolida os dados do professor, do RIT e das atividades
 * cadastradas em uma única visualização.
 * </p>
 */
public class RelatorioFrame extends JFrame {

    private Professor professor;
    private RIT rit;
    private List<Disciplina> disciplinas;
    private List<Orientacao> orientacoes;
    private List<Artigo> artigos;
    private List<AtividadeCoordenacao> coordenacoes;

    /**
     * Cria a tela de relatório.
     *
     * @param professor    professor relacionado ao relatório
     * @param rit          RIT relacionado ao relatório
     * @param disciplinas  disciplinas ministradas
     * @param orientacoes  orientações realizadas
     * @param artigos      artigos publicados
     * @param coordenacoes atividades de coordenação
     */
    public RelatorioFrame(
            Professor professor,
            RIT rit,
            List<Disciplina> disciplinas,
            List<Orientacao> orientacoes,
            List<Artigo> artigos,
            List<AtividadeCoordenacao> coordenacoes) {
        super("Relatório Individual de Trabalho");

        this.professor = professor;
        this.rit = rit;
        this.disciplinas = disciplinas;
        this.orientacoes = orientacoes;
        this.artigos = artigos;
        this.coordenacoes = coordenacoes;

        configurarJanela();
        criarComponentes();
    }

    /**
     * Configura as propriedades principais da janela.
     */
    private void configurarJanela() {
        setSize(900, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(16, 16));
        getContentPane().setBackground(AppTheme.BACKGROUND);
    }

    /**
     * Cria os componentes visuais da tela.
     */
    private void criarComponentes() {
        JPanel mainPanel = new JPanel(new BorderLayout(12, 12));
        mainPanel.setBackground(AppTheme.BACKGROUND);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel headerPanel = criarCabecalho();
        JPanel documentPanel = criarPainelDocumento();
        JPanel bottomPanel = criarPainelInferior();

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(documentPanel, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel, BorderLayout.CENTER);
    }

    /**
     * Cria o cabeçalho visual da tela de relatório.
     *
     * @return painel de cabeçalho
     */
    private JPanel criarCabecalho() {
        JPanel headerPanel = new JPanel(new BorderLayout(4, 4));
        headerPanel.setBackground(AppTheme.BACKGROUND);

        JLabel titleLabel = new JLabel("Relatório Individual de Trabalho");
        titleLabel.setFont(AppTheme.TITLE_FONT);
        titleLabel.setForeground(AppTheme.PRIMARY_DARK);

        JLabel subtitleLabel = new JLabel(
                "Visualização consolidada das atividades do professor no período selecionado.");
        subtitleLabel.setFont(AppTheme.SUBTITLE_FONT);
        subtitleLabel.setForeground(AppTheme.MUTED_TEXT);

        headerPanel.add(titleLabel, BorderLayout.NORTH);
        headerPanel.add(subtitleLabel, BorderLayout.SOUTH);

        return headerPanel;
    }

    /**
     * Cria o painel que exibe o texto do relatório.
     *
     * @return painel do documento
     */
    private JPanel criarPainelDocumento() {
        JPanel documentCard = UIUtils.createCardPanel();
        documentCard.setLayout(new BorderLayout(10, 10));

        JTextArea relatorioTextArea = new JTextArea();
        relatorioTextArea.setEditable(false);
        relatorioTextArea.setFont(new Font("Arial", Font.PLAIN, 14));
        relatorioTextArea.setForeground(AppTheme.TEXT);
        relatorioTextArea.setBackground(AppTheme.CARD_BACKGROUND);
        relatorioTextArea.setLineWrap(true);
        relatorioTextArea.setWrapStyleWord(true);
        relatorioTextArea.setMargin(new Insets(12, 12, 12, 12));
        relatorioTextArea.setText(montarTextoRelatorio());
        relatorioTextArea.setCaretPosition(0);

        JScrollPane scrollPane = new JScrollPane(relatorioTextArea);
        scrollPane.setBorder(BorderFactory.createLineBorder(AppTheme.BORDER));

        documentCard.add(scrollPane, BorderLayout.CENTER);

        return documentCard;
    }

    /**
     * Cria o painel inferior da tela.
     *
     * @return painel inferior
     */
    private JPanel criarPainelInferior() {
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(AppTheme.BACKGROUND);

        JButton closeButton = new JButton("Fechar");
        UIUtils.styleSecondaryButton(closeButton);

        closeButton.addActionListener(event -> dispose());

        bottomPanel.add(closeButton);

        return bottomPanel;
    }

    /**
     * Monta o texto completo do relatório.
     *
     * @return texto formatado do relatório
     */
    private String montarTextoRelatorio() {
        StringBuilder builder = new StringBuilder();

        builder.append("============================================================\n");
        builder.append("              RELATÓRIO INDIVIDUAL DE TRABALHO\n");
        builder.append("============================================================\n\n");

        builder.append("Professor: ").append(professor.getNome()).append("\n");
        builder.append("Email: ").append(valorOuVazio(professor.getEmail())).append("\n");
        builder.append("Departamento: ").append(valorOuVazio(professor.getDepartamento())).append("\n");
        builder.append("Semestre: ").append(rit.getSemestre()).append("\n");
        builder.append("Ano: ").append(rit.getAno()).append("\n\n");

        adicionarDisciplinas(builder);
        adicionarOrientacoes(builder);
        adicionarArtigos(builder);
        adicionarCoordenacoes(builder);

        return builder.toString();
    }

    /**
     * Adiciona as disciplinas ao texto do relatório.
     *
     * @param builder construtor do texto
     */
    private void adicionarDisciplinas(StringBuilder builder) {
        builder.append("1. DISCIPLINAS MINISTRADAS\n");
        builder.append("------------------------------------------------------------\n");

        if (disciplinas.isEmpty()) {
            builder.append("Nenhuma disciplina cadastrada.\n\n");
            return;
        }

        for (Disciplina disciplina : disciplinas) {
            builder.append("- ");
            builder.append(disciplina.getNome());

            if (!valorOuVazio(disciplina.getCodigo()).isEmpty()) {
                builder.append(" | Código: ").append(disciplina.getCodigo());
            }

            if (disciplina.getCargaHoraria() > 0) {
                builder.append(" | Carga horária: ").append(disciplina.getCargaHoraria()).append("h");
            }

            if (!valorOuVazio(disciplina.getCurso()).isEmpty()) {
                builder.append(" | Curso: ").append(disciplina.getCurso());
            }

            builder.append("\n");
        }

        builder.append("\n");
    }

    /**
     * Adiciona as orientações ao texto do relatório, separadas por tipo.
     *
     * @param builder construtor do texto
     */
    private void adicionarOrientacoes(StringBuilder builder) {
        builder.append("2. ALUNOS ORIENTADOS\n");
        builder.append("------------------------------------------------------------\n\n");

        adicionarOrientacoesPorTipo(builder, "2.1 Alunos de graduação - projeto final/TCC", "Projeto final/TCC");
        adicionarOrientacoesPorTipo(builder, "2.2 Alunos de graduação - iniciação científica", "Iniciação científica");
        adicionarOrientacoesPorTipo(builder, "2.3 Alunos de mestrado", "Mestrado");
        adicionarOrientacoesPorTipo(builder, "2.4 Alunos de doutorado", "Doutorado");
    }

    /**
     * Adiciona orientações de um tipo específico ao relatório.
     *
     * @param builder construtor do texto
     * @param titulo  titulo da subseção
     * @param tipo    tipo de orientação
     */
    private void adicionarOrientacoesPorTipo(StringBuilder builder, String titulo, String tipo) {
        builder.append(titulo).append("\n");

        boolean encontrou = false;

        for (Orientacao orientacao : orientacoes) {
            if (tipo.equals(orientacao.getTipo())) {
                encontrou = true;

                builder.append("- ");
                builder.append(orientacao.getNomeAluno());

                if (!valorOuVazio(orientacao.getTituloTrabalho()).isEmpty()) {
                    builder.append(" | Trabalho: ").append(orientacao.getTituloTrabalho());
                }

                if (!valorOuVazio(orientacao.getCurso()).isEmpty()) {
                    builder.append(" | Curso: ").append(orientacao.getCurso());
                }

                builder.append("\n");
            }
        }

        if (!encontrou) {
            builder.append("Nenhuma orientação cadastrada nesta categoria.\n");
        }

        builder.append("\n");
    }

    /**
     * Adiciona os artigos ao texto do relatório.
     *
     * @param builder construtor do texto
     */
    private void adicionarArtigos(StringBuilder builder) {
        builder.append("3. ARTIGOS PUBLICADOS\n");
        builder.append("------------------------------------------------------------\n");

        if (artigos.isEmpty()) {
            builder.append("Nenhum artigo cadastrado.\n\n");
            return;
        }

        for (Artigo artigo : artigos) {
            builder.append("- ");
            builder.append(artigo.getTitulo());

            if (!valorOuVazio(artigo.getAutores()).isEmpty()) {
                builder.append(" | Autores: ").append(artigo.getAutores());
            }

            if (!valorOuVazio(artigo.getNomeEventoOuRevista()).isEmpty()) {
                builder.append(" | Evento/Revista: ").append(artigo.getNomeEventoOuRevista());
            }

            if (artigo.getAnoPublicacao() > 0) {
                builder.append(" | Ano: ").append(artigo.getAnoPublicacao());
            }

            builder.append("\n");
        }

        builder.append("\n");
    }

    /**
     * Adiciona as atividades de coordenação ao texto do relatório.
     *
     * @param builder construtor do texto
     */
    private void adicionarCoordenacoes(StringBuilder builder) {
        builder.append("4. ATIVIDADES DE COORDENAÇÃO\n");
        builder.append("------------------------------------------------------------\n");

        if (coordenacoes.isEmpty()) {
            builder.append("Nenhuma atividade de coordenação cadastrada.\n\n");
            return;
        }

        for (AtividadeCoordenacao atividade : coordenacoes) {
            builder.append("- ");

            if (!valorOuVazio(atividade.getCargo()).isEmpty()) {
                builder.append(atividade.getCargo()).append(" | ");
            }

            builder.append(atividade.getDescricao());

            if (atividade.getCargaHoraria() > 0) {
                builder.append(" | Carga horária: ").append(atividade.getCargaHoraria()).append("h");
            }

            builder.append("\n");
        }

        builder.append("\n");
    }

    /**
     * Retorna uma string vazia quando o valor é nulo.
     *
     * @param valor valor que será verificado
     * @return valor original ou string vazia
     */
    private String valorOuVazio(String valor) {
        if (valor == null) {
            return "";
        }

        return valor;
    }
}