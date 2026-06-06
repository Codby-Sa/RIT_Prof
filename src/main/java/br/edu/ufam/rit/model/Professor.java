package br.edu.ufam.rit.model;

/**
 * Representa um professor cadastrado no sistema.
 *
 * <p>Um professor pode possuir um ou mais Relatórios Individuais de Trabalho,
 * cada um referente a um determinado semestre.</p>
 */
public class Professor {

    private int id;
    private String nome;
    private String email;
    private String departamento;

    /**
     * Construtor vazio.
     *
     * <p>É útil para criar um objeto Professor e preencher os dados depois
     * usando os métodos setters.</p>
     */
    public Professor() {
    }

    /**
     * Cria um professor sem id.
     *
     * <p>Esse construtor será usado principalmente antes de salvar um novo
     * professor no banco, porque o id será gerado automaticamente.</p>
     *
     * @param nome nome do professor
     * @param email e-mail do professor
     * @param departamento departamento do professor
     */
    public Professor(String nome, String email, String departamento) {
        this.nome = nome;
        this.email = email;
        this.departamento = departamento;
    }

    /**
     * Cria um professor com id.
     *
     * <p>Esse construtor será usado principalmente quando recuperarmos
     * professores já salvos no banco de dados.</p>
     *
     * @param id identificador do professor
     * @param nome nome do professor
     * @param email e-mail do professor
     * @param departamento departamento do professor
     */
    public Professor(int id, String nome, String email, String departamento) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.departamento = departamento;
    }

    /**
     * Retorna o identificador do professor.
     *
     * @return id do professor
     */
    public int getId() {
        return id;
    }

    /**
     * Define o identificador do professor.
     *
     * @param id id do professor
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Retorna o nome do professor.
     *
     * @return nome do professor
     */
    public String getNome() {
        return nome;
    }

    /**
     * Define o nome do professor.
     *
     * @param nome nome do professor
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Retorna o e-mail do professor.
     *
     * @return e-mail do professor
     */
    public String getEmail() {
        return email;
    }

    /**
     * Define o e-mail do professor.
     *
     * @param email e-mail do professor
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Retorna o departamento do professor.
     *
     * @return departamento do professor
     */
    public String getDepartamento() {
        return departamento;
    }

    /**
     * Define o departamento do professor.
     *
     * @param departamento departamento do professor
     */
    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    /**
     * Retorna uma representação textual do professor.
     *
     * @return nome do professor
     */
    @Override
    public String toString() {
        return nome;
    }
}