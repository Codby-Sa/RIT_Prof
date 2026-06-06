package br.edu.ufam.rit.model;

/**
 * Representa uma atividade de coordenação realizada por um professor.
 */
public class AtividadeCoordenacao {

    private int id;
    private int ritId;
    private String descricao;
    private String cargo;
    private int cargaHoraria;

    /**
     * Construtor vazio.
     */
    public AtividadeCoordenacao() {
    }

    /**
     * Cria uma atividade de coordenação sem id.
     *
     * @param ritId id do RIT relacionado
     * @param descricao descrição da atividade
     * @param cargo cargo ou função exercida
     * @param cargaHoraria carga horária da atividade
     */
    public AtividadeCoordenacao(int ritId, String descricao, String cargo, int cargaHoraria) {
        this.ritId = ritId;
        this.descricao = descricao;
        this.cargo = cargo;
        this.cargaHoraria = cargaHoraria;
    }

    /**
     * Cria uma atividade de coordenação com id.
     *
     * @param id identificador da atividade de coordenação
     * @param ritId id do RIT relacionado
     * @param descricao descrição da atividade
     * @param cargo cargo ou função exercida
     * @param cargaHoraria carga horária da atividade
     */
    public AtividadeCoordenacao(int id, int ritId, String descricao, String cargo, int cargaHoraria) {
        this.id = id;
        this.ritId = ritId;
        this.descricao = descricao;
        this.cargo = cargo;
        this.cargaHoraria = cargaHoraria;
    }

    public int getId() {
        return id;
    }

    public int getRitId() {
        return ritId;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getCargo() {
        return cargo;
    }

    public int getCargaHoraria() {
        return cargaHoraria;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setRitId(int ritId) {
        this.ritId = ritId;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public void setCargaHoraria(int cargaHoraria) {
        this.cargaHoraria = cargaHoraria;
    }

    @Override
    public String toString() {
        return cargo + " - " + descricao;
    }
}