package br.edu.ufam.rit.model;

/**
 * Representa uma orientação realizada por um professor.
 *
 * <p>A orientação pode ser de TCC, iniciação científica, mestrado ou doutorado.</p>
 */
public class Orientacao {

    private int id;
    private int ritId;
    private String nomeAluno;
    private String tipo;
    private String tituloTrabalho;
    private String curso;

    /**
     * Construtor vazio.
     */
    public Orientacao() {
    }

    /**
     * Cria uma orientação sem id.
     *
     * @param ritId id do RIT relacionado
     * @param nomeAluno nome do aluno orientado
     * @param tipo tipo da orientação
     * @param tituloTrabalho título do trabalho orientado
     * @param curso curso do aluno
     */
    public Orientacao(int ritId, String nomeAluno, String tipo, String tituloTrabalho, String curso) {
        this.ritId = ritId;
        this.nomeAluno = nomeAluno;
        this.tipo = tipo;
        this.tituloTrabalho = tituloTrabalho;
        this.curso = curso;
    }

    /**
     * Cria uma orientação com id.
     *
     * @param id identificador da orientação
     * @param ritId id do RIT relacionado
     * @param nomeAluno nome do aluno orientado
     * @param tipo tipo da orientação
     * @param tituloTrabalho título do trabalho orientado
     * @param curso curso do aluno
     */
    public Orientacao(int id, int ritId, String nomeAluno, String tipo, String tituloTrabalho, String curso) {
        this.id = id;
        this.ritId = ritId;
        this.nomeAluno = nomeAluno;
        this.tipo = tipo;
        this.tituloTrabalho = tituloTrabalho;
        this.curso = curso;
    }

    public int getId() {
        return id;
    }

    public int getRitId() {
        return ritId;
    }

    public String getNomeAluno() {
        return nomeAluno;
    }

    public String getTipo() {
        return tipo;
    }

    public String getTituloTrabalho() {
        return tituloTrabalho;
    }

    public String getCurso() {
        return curso;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setRitId(int ritId) {
        this.ritId = ritId;
    }

    public void setNomeAluno(String nomeAluno) {
        this.nomeAluno = nomeAluno;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setTituloTrabalho(String tituloTrabalho) {
        this.tituloTrabalho = tituloTrabalho;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    @Override
    public String toString() {
        return nomeAluno + " - " + tipo;
    }
}