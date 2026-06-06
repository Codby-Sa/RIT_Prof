package br.edu.ufam.rit.model;

/**
 * Representa uma disciplina ministrada por um professor em um RIT.
 */
public class Disciplina {

    private int id;
    private int ritId;
    private String nome;
    private String codigo;
    private int cargaHoraria;
    private String curso;

    /**
     * Construtor vazio.
     */
    public Disciplina() {
    }

    /**
     * Cria uma disciplina sem id.
     *
     * @param ritId id do RIT relacionado
     * @param nome nome da disciplina
     * @param codigo código da disciplina
     * @param cargaHoraria carga horária da disciplina
     * @param curso curso ao qual a disciplina pertence
     */
    public Disciplina(int ritId, String nome, String codigo, int cargaHoraria, String curso) {
        this.ritId = ritId;
        this.nome = nome;
        this.codigo = codigo;
        this.cargaHoraria = cargaHoraria;
        this.curso = curso;
    }

    /**
     * Cria uma disciplina com id.
     *
     * @param id identificador da disciplina
     * @param ritId id do RIT relacionado
     * @param nome nome da disciplina
     * @param codigo código da disciplina
     * @param cargaHoraria carga horária da disciplina
     * @param curso curso ao qual a disciplina pertence
     */
    public Disciplina(int id, int ritId, String nome, String codigo, int cargaHoraria, String curso) {
        this.id = id;
        this.ritId = ritId;
        this.nome = nome;
        this.codigo = codigo;
        this.cargaHoraria = cargaHoraria;
        this.curso = curso;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public int getRitId() {
        return ritId;
    }

    public void setRitId(int ritId) {
        this.ritId = ritId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }


    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }


    public int getCargaHoraria() {
        return cargaHoraria;
    }

    public void setCargaHoraria(int cargaHoraria) {
        this.cargaHoraria = cargaHoraria;
    }


    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    @Override
    public String toString() {
        return nome;
    }
}