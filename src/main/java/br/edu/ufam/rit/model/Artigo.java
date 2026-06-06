package br.edu.ufam.rit.model;

/**
 * Representa um artigo publicado por um professor em determinado RIT.
 */
public class Artigo {

    private int id;
    private int ritId;
    private String titulo;
    private String autores;
    private String nomeEventoOuRevista;
    private int anoPublicacao;

    /**
     * Construtor vazio.
     */
    public Artigo() {
    }

    /**
     * Cria um artigo sem id.
     *
     * @param ritId id do RIT relacionado
     * @param titulo título do artigo
     * @param autores autores do artigo
     * @param nomeEventoOuRevista nome do evento ou revista
     * @param anoPublicacao ano de publicação
     */
    public Artigo(int ritId, String titulo, String autores, String nomeEventoOuRevista, int anoPublicacao) {
        this.ritId = ritId;
        this.titulo = titulo;
        this.autores = autores;
        this.nomeEventoOuRevista = nomeEventoOuRevista;
        this.anoPublicacao = anoPublicacao;
    }

    /**
     * Cria um artigo com id.
     *
     * @param id identificador do artigo
     * @param ritId id do RIT relacionado
     * @param titulo título do artigo
     * @param autores autores do artigo
     * @param nomeEventoOuRevista nome do evento ou revista
     * @param anoPublicacao ano de publicação
     */
    public Artigo(int id, int ritId, String titulo, String autores, String nomeEventoOuRevista, int anoPublicacao) {
        this.id = id;
        this.ritId = ritId;
        this.titulo = titulo;
        this.autores = autores;
        this.nomeEventoOuRevista = nomeEventoOuRevista;
        this.anoPublicacao = anoPublicacao;
    }

    public int getId() {
        return id;
    }

    public int getRitId() {
        return ritId;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getAutores() {
        return autores;
    }

    public String getNomeEventoOuRevista() {
        return nomeEventoOuRevista;
    }

    public int getAnoPublicacao() {
        return anoPublicacao;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setRitId(int ritId) {
        this.ritId = ritId;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setAutores(String autores) {
        this.autores = autores;
    }

    public void setNomeEventoOuRevista(String nomeEventoOuRevista) {
        this.nomeEventoOuRevista = nomeEventoOuRevista;
    }

    public void setAnoPublicacao(int anoPublicacao) {
        this.anoPublicacao = anoPublicacao;
    }

    @Override
    public String toString() {
        return titulo;
    }
}