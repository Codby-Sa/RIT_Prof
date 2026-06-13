package br.edu.ufam.rit.view;

import br.edu.ufam.rit.dao.ArtigoDAO;
import br.edu.ufam.rit.model.Artigo;

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
 * Janela de formulário usada para cadastrar ou editar artigos publicados.
 */
public class ArtigoFormDialog extends JDialog {

    private JTextField tituloField;
    private JTextField autoresField;
    private JTextField eventoRevistaField;
    private JTextField anoPublicacaoField;

    private Artigo artigo;
    private ArtigoDAO artigoDAO;
    private int ritId;
    private boolean saved;

    /**
     * Cria o formulário de artigo.
     *
     * @param parent janela principal
     * @param ritId id do RIT relacionado
     * @param artigo artigo para edição, ou null para cadastro
     */
    public ArtigoFormDialog(JFrame parent, int ritId, Artigo artigo) {
        super(parent, true);

        this.ritId = ritId;
        this.artigo = artigo;
        this.artigoDAO = new ArtigoDAO();
        this.saved = false;

        configurarJanela();
        criarComponentes();
        preencherCamposSeEdicao();
    }

    /**
     * Configura a janela.
     */
    private void configurarJanela() {
        if (artigo == null) {
            setTitle("Adicionar Artigo");
        } else {
            setTitle("Editar Artigo");
        }

        setSize(550, 300);
        setLocationRelativeTo(getParent());
        setLayout(new BorderLayout(10, 10));
    }

    /**
     * Cria os componentes visuais do formulário.
     */
    private void criarComponentes() {
        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));

        tituloField = new JTextField();
        autoresField = new JTextField();
        eventoRevistaField = new JTextField();
        anoPublicacaoField = new JTextField();

        formPanel.add(new JLabel("Título:"));
        formPanel.add(tituloField);

        formPanel.add(new JLabel("Autores:"));
        formPanel.add(autoresField);

        formPanel.add(new JLabel("Evento ou revista:"));
        formPanel.add(eventoRevistaField);

        formPanel.add(new JLabel("Ano de publicação:"));
        formPanel.add(anoPublicacaoField);

        JPanel buttonPanel = new JPanel();

        JButton saveButton = new JButton("Salvar");
        JButton cancelButton = new JButton("Cancelar");

        saveButton.addActionListener(event -> salvarArtigo());
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
        if (artigo != null) {
            tituloField.setText(artigo.getTitulo());
            autoresField.setText(artigo.getAutores());
            eventoRevistaField.setText(artigo.getNomeEventoOuRevista());
            anoPublicacaoField.setText(String.valueOf(artigo.getAnoPublicacao()));
        }
    }

    /**
     * Valida e salva o artigo no banco.
     */
    private void salvarArtigo() {
        String titulo = tituloField.getText().trim();
        String autores = autoresField.getText().trim();
        String eventoRevista = eventoRevistaField.getText().trim();
        String anoPublicacaoTexto = anoPublicacaoField.getText().trim();

        if (titulo.isEmpty()) {
            JOptionPane.showMessageDialog(
                this,
                "O título do artigo é obrigatório.",
                "Validação",
                JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        int anoPublicacao = 0;

        if (!anoPublicacaoTexto.isEmpty()) {
            try {
                anoPublicacao = Integer.parseInt(anoPublicacaoTexto);
            } catch (NumberFormatException exception) {
                JOptionPane.showMessageDialog(
                    this,
                    "O ano de publicação deve ser um número inteiro.",
                    "Validação",
                    JOptionPane.WARNING_MESSAGE
                );
                return;
            }
        }

        try {
            if (artigo == null) {
                Artigo novoArtigo = new Artigo(
                    ritId,
                    titulo,
                    autores,
                    eventoRevista,
                    anoPublicacao
                );

                artigoDAO.inserir(novoArtigo);
            } else {
                artigo.setTitulo(titulo);
                artigo.setAutores(autores);
                artigo.setNomeEventoOuRevista(eventoRevista);
                artigo.setAnoPublicacao(anoPublicacao);

                artigoDAO.atualizar(artigo);
            }

            saved = true;
            dispose();

        } catch (SQLException exception) {
            JOptionPane.showMessageDialog(
                this,
                "Erro ao salvar artigo.",
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