package br.edu.ufam.rit.model;

/**
 * Representa um Relatório Individual de Trabalho de um professor.
 *
 * <p>O RIT armazena o semestre e o ano referentes às atividades
 * realizadas por um professor.</p>
 */
public class RIT {

    private int id;
    private int professorId;
    private String semestre;
    private int ano;

    /**
     * Construtor vazio.
     */
    public RIT() {
    }

    /**
     * Cria um RIT sem id.
     *
     * @param professorId id do professor relacionado ao RIT
     * @param semestre semestre do relatório
     * @param ano ano do relatório
     */
    public RIT(int professorId, String semestre, int ano) {
        this.professorId = professorId;
        this.semestre = semestre;
        this.ano = ano;
    }

    /**
     * Cria um RIT com id.
     *
     * @param id identificador do RIT
     * @param professorId id do professor relacionado ao RIT
     * @param semestre semestre do relatório
     * @param ano ano do relatório
     */
    public RIT(int id, int professorId, String semestre, int ano) {
        this.id = id;
        this.professorId = professorId;
        this.semestre = semestre;
        this.ano = ano;
    }

    /**
     * Retorna o identificador do RIT.
     *
     * @return id do RIT
     */
    public int getId() {
        return id;
    }

    /**
     * Define o identificador do RIT.
     *
     * @param id id do RIT
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Retorna o id do professor relacionado ao RIT.
     *
     * @return id do professor
     */
    public int getProfessorId() {
        return professorId;
    }

    /**
     * Define o id do professor relacionado ao RIT.
     *
     * @param professorId id do professor
     */
    public void setProfessorId(int professorId) {
        this.professorId = professorId;
    }

    /**
     * Retorna o semestre do RIT.
     *
     * @return semestre do relatório
     */
    public String getSemestre() {
        return semestre;
    }

    /**
     * Define o semestre do RIT.
     *
     * @param semestre semestre do relatório
     */
    public void setSemestre(String semestre) {
        this.semestre = semestre;
    }

    /**
     * Retorna o ano do RIT.
     *
     * @return ano do relatório
     */
    public int getAno() {
        return ano;
    }

    /**
     * Define o ano do RIT.
     *
     * @param ano ano do relatório
     */
    public void setAno(int ano) {
        this.ano = ano;
    }

    /**
     * Retorna uma representação textual do RIT.
     *
     * @return semestre e ano do RIT
     */
    @Override
    public String toString() {
        return semestre + "/" + ano;
    }
}