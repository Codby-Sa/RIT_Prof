package br.edu.ufam.rit.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Classe responsável por criar as tabelas do banco de dados
 * e inserir dados iniciais de demonstração.
 */
public class DatabaseInitializer {

    /**
     * Inicializa o banco de dados da aplicação.
     *
     * @throws SQLException caso ocorra erro durante a criação das tabelas
     */
    public static void initialize() throws SQLException {
        try (Connection connection = ConnectionFactory.getConnection();
                Statement statement = connection.createStatement()) {

            statement.execute("""
                        CREATE TABLE IF NOT EXISTS professores (
                            id INTEGER PRIMARY KEY AUTOINCREMENT,
                            nome TEXT NOT NULL,
                            email TEXT,
                            departamento TEXT
                        );
                    """);

            statement.execute("""
                        CREATE TABLE IF NOT EXISTS rits (
                            id INTEGER PRIMARY KEY AUTOINCREMENT,
                            professor_id INTEGER NOT NULL,
                            semestre TEXT NOT NULL,
                            ano INTEGER NOT NULL,
                            FOREIGN KEY (professor_id) REFERENCES professores(id)
                        );
                    """);

            statement.execute("""
                        CREATE TABLE IF NOT EXISTS disciplinas (
                            id INTEGER PRIMARY KEY AUTOINCREMENT,
                            rit_id INTEGER NOT NULL,
                            nome TEXT NOT NULL,
                            codigo TEXT,
                            carga_horaria INTEGER,
                            curso TEXT,
                            FOREIGN KEY (rit_id) REFERENCES rits(id)
                        );
                    """);

            statement.execute("""
                        CREATE TABLE IF NOT EXISTS orientacoes (
                            id INTEGER PRIMARY KEY AUTOINCREMENT,
                            rit_id INTEGER NOT NULL,
                            nome_aluno TEXT NOT NULL,
                            tipo TEXT NOT NULL,
                            titulo_trabalho TEXT,
                            curso TEXT,
                            FOREIGN KEY (rit_id) REFERENCES rits(id)
                        );
                    """);

            statement.execute("""
                        CREATE TABLE IF NOT EXISTS artigos (
                            id INTEGER PRIMARY KEY AUTOINCREMENT,
                            rit_id INTEGER NOT NULL,
                            titulo TEXT NOT NULL,
                            autores TEXT,
                            nome_evento_ou_revista TEXT,
                            ano_publicacao INTEGER,
                            FOREIGN KEY (rit_id) REFERENCES rits(id)
                        );
                    """);

            statement.execute("""
                        CREATE TABLE IF NOT EXISTS atividades_coordenacao (
                            id INTEGER PRIMARY KEY AUTOINCREMENT,
                            rit_id INTEGER NOT NULL,
                            descricao TEXT NOT NULL,
                            cargo TEXT,
                            carga_horaria INTEGER,
                            FOREIGN KEY (rit_id) REFERENCES rits(id)
                        );
                    """);
        }
    }

    /**
     * Insere dados iniciais de demonstração no banco.
     *
     * <p>
     * O método evita duplicar professores usando o e-mail como referência.
     * </p>
     *
     * @throws SQLException caso ocorra erro ao inserir os dados
     */
    public static void insertInitialData() throws SQLException {
        if (existeAlgumProfessor()) {
            return;
        }
        criarProfessorCompleto(
                "Ana Beatriz Silva",
                "ana.beatriz@ufam.edu.br",
                "Computação",
                "2026/1",
                2026,
                new String[][] {
                        { "Estrutura de Dados", "ICC001", "60", "Engenharia da Computação" },
                        { "Programação Orientada a Objetos", "ICC002", "60", "Ciência da Computação" }
                },
                new String[][] {
                        { "Maria Souza", "Projeto final/TCC", "Sistema de Gerenciamento Acadêmico",
                                "Engenharia da Computação" },
                        { "João Lima", "Iniciação científica", "Aplicação de IA em Sistemas Educacionais",
                                "Ciência da Computação" },
                        { "Carla Mendes", "Mestrado", "Modelagem de Dados para Sistemas Acadêmicos", "Informática" },
                        { "Pedro Santos", "Doutorado", "Arquiteturas Distribuídas para Gestão Universitária",
                                "Informática" }
                },
                new String[][] {
                        { "Uso de Sistemas de Informação na Gestão Acadêmica", "Ana Beatriz Silva, Maria Souza",
                                "Congresso Brasileiro de Computação", "2026" },
                        { "Aplicações de Inteligência Artificial na Educação", "Ana Beatriz Silva, João Lima",
                                "Revista de Tecnologia Educacional", "2026" }
                },
                new String[][] {
                        { "Coordenação de atividades acadêmicas do curso", "Coordenadora de Curso", "20" },
                        { "Organização de projetos de extensão e pesquisa", "Membro de Comissão", "10" }
                });

        criarProfessorCompleto(
                "Bruno Henrique Costa",
                "bruno.costa@ufam.edu.br",
                "Sistemas Embarcados",
                "2026/1",
                2026,
                new String[][] {
                        { "Sistemas Embarcados", "SEM101", "60", "Engenharia da Computação" },
                        { "Arquitetura de Computadores", "ARQ201", "60", "Engenharia Elétrica" }
                },
                new String[][] {
                        { "Lucas Rocha", "Projeto final/TCC", "Monitoramento Ambiental com Sensores IoT",
                                "Engenharia da Computação" },
                        { "Beatriz Nunes", "Iniciação científica", "Protótipo de Dispositivo de Baixo Consumo",
                                "Engenharia Elétrica" }
                },
                new String[][] {
                        { "Dispositivos IoT de Baixo Consumo para Ambientes Amazônicos", "Bruno Costa, Lucas Rocha",
                                "Simpósio de Sistemas Embarcados", "2026" }
                },
                new String[][] {
                        { "Gestão do laboratório de prototipagem eletrônica", "Coordenador de Laboratório", "12" }
                });

        criarProfessorCompleto(
                "Camila Torres Almeida",
                "camila.almeida@ufam.edu.br",
                "Inteligência Artificial",
                "2026/1",
                2026,
                new String[][] {
                        { "Inteligência Artificial", "IA301", "60", "Ciência da Computação" },
                        { "Aprendizado de Máquina", "IA401", "60", "Engenharia da Computação" }
                },
                new String[][] {
                        { "Rafaela Martins", "Projeto final/TCC", "Classificação de Imagens Médicas com Redes Neurais",
                                "Ciência da Computação" },
                        { "Igor Batista", "Mestrado", "Modelos Preditivos para Dados Educacionais", "Informática" }
                },
                new String[][] {
                        { "Redes Neurais Aplicadas à Análise de Dados Acadêmicos", "Camila Almeida, Igor Batista",
                                "Revista Brasileira de IA", "2026" },
                        { "Classificação Automática de Imagens com CNNs", "Camila Almeida, Rafaela Martins",
                                "Workshop de Aprendizado Profundo", "2026" }
                },
                new String[][] {
                        { "Coordenação do grupo de estudos em inteligência artificial", "Líder de Grupo de Pesquisa",
                                "8" }
                });

        criarProfessorCompleto(
                "Daniel Ferreira Gomes",
                "daniel.gomes@ufam.edu.br",
                "Redes de Computadores",
                "2026/1",
                2026,
                new String[][] {
                        { "Redes de Computadores", "RED101", "60", "Ciência da Computação" },
                        { "Segurança de Redes", "SEG202", "60", "Sistemas de Informação" }
                },
                new String[][] {
                        { "Fernanda Reis", "Projeto final/TCC", "Análise de Segurança em Redes Wi-Fi Institucionais",
                                "Sistemas de Informação" },
                        { "Mateus Oliveira", "Doutorado", "Protocolos Seguros para Ambientes Distribuídos",
                                "Informática" }
                },
                new String[][] {
                        { "Segurança em Redes Acadêmicas", "Daniel Gomes, Fernanda Reis",
                                "Conferência Nacional de Redes", "2026" }
                },
                new String[][] {
                        { "Administração acadêmica da infraestrutura de redes do laboratório", "Responsável Técnico",
                                "15" }
                });

        criarProfessorCompleto(
                "Elisa Moreira Santos",
                "elisa.santos@ufam.edu.br",
                "Banco de Dados",
                "2026/1",
                2026,
                new String[][] {
                        { "Banco de Dados", "BD101", "60", "Sistemas de Informação" },
                        { "Projeto de Banco de Dados", "BD202", "60", "Engenharia da Computação" }
                },
                new String[][] {
                        { "Gabriel Castro", "Projeto final/TCC",
                                "Sistema de Controle de Estoque para Pequenos Comércios", "Sistemas de Informação" },
                        { "Larissa Freitas", "Iniciação científica", "Otimização de Consultas em Bancos Relacionais",
                                "Ciência da Computação" }
                },
                new String[][] {
                        { "Modelagem de Dados para Sistemas de Gestão", "Elisa Santos, Gabriel Castro",
                                "Encontro de Banco de Dados", "2026" }
                },
                new String[][] {
                        { "Participação na comissão de atualização curricular", "Membro de Comissão", "6" }
                });

        criarProfessorCompleto(
                "Felipe Augusto Lima",
                "felipe.lima@ufam.edu.br",
                "Engenharia de Software",
                "2026/1",
                2026,
                new String[][] {
                        { "Engenharia de Software", "ES101", "60", "Engenharia da Computação" },
                        { "Qualidade de Software", "ES202", "45", "Sistemas de Informação" }
                },
                new String[][] {
                        { "Natália Alves", "Projeto final/TCC", "Aplicativo Mobile para Organização de Estudos",
                                "Engenharia da Computação" },
                        { "Thiago Pereira", "Mestrado", "Avaliação de Qualidade em Aplicações Web", "Informática" }
                },
                new String[][] {
                        { "Boas Práticas de Desenvolvimento em Projetos Acadêmicos", "Felipe Lima, Natália Alves",
                                "Simpósio de Engenharia de Software", "2026" }
                },
                new String[][] {
                        { "Supervisão de projetos integradores", "Coordenador de Projetos", "10" }
                });

        criarProfessorCompleto(
                "Gabriela Cunha Ribeiro",
                "gabriela.ribeiro@ufam.edu.br",
                "Interação Humano-Computador",
                "2026/1",
                2026,
                new String[][] {
                        { "Interação Humano-Computador", "IHC101", "60", "Sistemas de Informação" },
                        { "Design de Interfaces", "IHC202", "45", "Ciência da Computação" }
                },
                new String[][] {
                        { "Paula Andrade", "Projeto final/TCC", "Avaliação de Usabilidade em Sistemas Acadêmicos",
                                "Sistemas de Informação" },
                        { "Renan Cardoso", "Iniciação científica", "Acessibilidade em Interfaces Educacionais",
                                "Ciência da Computação" }
                },
                new String[][] {
                        { "Acessibilidade Digital em Sistemas Universitários", "Gabriela Ribeiro, Renan Cardoso",
                                "Revista de IHC", "2026" }
                },
                new String[][] {
                        { "Organização de oficinas de acessibilidade digital", "Coordenadora de Extensão", "8" }
                });

        criarProfessorCompleto(
                "Henrique Barros Nascimento",
                "henrique.nascimento@ufam.edu.br",
                "Computação Gráfica",
                "2026/1",
                2026,
                new String[][] {
                        { "Computação Gráfica", "CG101", "60", "Ciência da Computação" },
                        { "Processamento de Imagens", "IMG202", "60", "Engenharia da Computação" }
                },
                new String[][] {
                        { "Sofia Martins", "Projeto final/TCC", "Visualização 3D de Ambientes Educacionais",
                                "Ciência da Computação" },
                        { "Vitor Hugo", "Mestrado", "Processamento de Imagens Aplicado à Biodiversidade",
                                "Informática" }
                },
                new String[][] {
                        { "Visualização Computacional para Ambientes Virtuais", "Henrique Nascimento, Sofia Martins",
                                "Workshop de Computação Gráfica", "2026" }
                },
                new String[][] {
                        { "Coordenação do laboratório de mídia digital", "Coordenador de Laboratório", "12" }
                });

        criarProfessorCompleto(
                "Isabela Rocha Monteiro",
                "isabela.monteiro@ufam.edu.br",
                "Matemática Computacional",
                "2026/1",
                2026,
                new String[][] {
                        { "Cálculo Numérico", "MAT201", "60", "Engenharia da Computação" },
                        { "Matemática Discreta", "MAT101", "60", "Ciência da Computação" }
                },
                new String[][] {
                        { "Eduardo Silva", "Projeto final/TCC", "Simulação Numérica de Sistemas Físicos",
                                "Engenharia da Computação" },
                        { "Mariana Lopes", "Doutorado", "Métodos Numéricos para Otimização", "Informática" }
                },
                new String[][] {
                        { "Métodos Numéricos Aplicados à Computação Científica", "Isabela Monteiro, Mariana Lopes",
                                "Revista de Matemática Aplicada", "2026" }
                },
                new String[][] {
                        { "Apoio à comissão de acompanhamento discente", "Membro de Comissão", "6" }
                });

        criarProfessorCompleto(
                "João Victor Azevedo",
                "joao.azevedo@ufam.edu.br",
                "Sistemas Distribuídos",
                "2026/1",
                2026,
                new String[][] {
                        { "Sistemas Distribuídos", "SD101", "60", "Ciência da Computação" },
                        { "Computação em Nuvem", "NUV202", "45", "Sistemas de Informação" }
                },
                new String[][] {
                        { "Clara Fernandes", "Projeto final/TCC", "Aplicação Distribuída para Controle de Relatórios",
                                "Sistemas de Informação" },
                        { "André Moreira", "Iniciação científica", "Avaliação de Desempenho em Serviços Cloud",
                                "Engenharia da Computação" }
                },
                new String[][] {
                        { "Computação em Nuvem para Aplicações Acadêmicas", "João Azevedo, André Moreira",
                                "Congresso de Sistemas Distribuídos", "2026" }
                },
                new String[][] {
                        { "Coordenação de infraestrutura para projetos em nuvem", "Coordenador Técnico", "10" }
                });
    }

    /**
     * Cria um professor completo com RIT e atividades, caso o e-mail ainda não
     * exista.
     */
    private static void criarProfessorCompleto(
            String nome,
            String email,
            String departamento,
            String semestre,
            int ano,
            String[][] disciplinas,
            String[][] orientacoes,
            String[][] artigos,
            String[][] coordenacoes) throws SQLException {
        Integer professorIdExistente = buscarProfessorIdPorEmail(email);

        if (professorIdExistente != null) {
            return;
        }

        int professorId = inserirProfessor(nome, email, departamento);
        int ritId = inserirRIT(professorId, semestre, ano);

        for (String[] disciplina : disciplinas) {
            inserirDisciplina(
                    ritId,
                    disciplina[0],
                    disciplina[1],
                    Integer.parseInt(disciplina[2]),
                    disciplina[3]);
        }

        for (String[] orientacao : orientacoes) {
            inserirOrientacao(
                    ritId,
                    orientacao[0],
                    orientacao[1],
                    orientacao[2],
                    orientacao[3]);
        }

        for (String[] artigo : artigos) {
            inserirArtigo(
                    ritId,
                    artigo[0],
                    artigo[1],
                    artigo[2],
                    Integer.parseInt(artigo[3]));
        }

        for (String[] coordenacao : coordenacoes) {
            inserirCoordenacao(
                    ritId,
                    coordenacao[0],
                    coordenacao[1],
                    Integer.parseInt(coordenacao[2]));
        }
    }

    /**
     * Busca o id de um professor pelo e-mail.
     */
    private static Integer buscarProfessorIdPorEmail(String email) throws SQLException {
        String sql = """
                    SELECT id
                    FROM professores
                    WHERE email = ?
                """;

        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, email);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("id");
                }
            }
        }

        return null;
    }

    /**
     * Insere um professor e retorna o id gerado.
     */
    private static int inserirProfessor(String nome, String email, String departamento) throws SQLException {
        String sql = """
                    INSERT INTO professores (nome, email, departamento)
                    VALUES (?, ?, ?)
                """;

        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, nome);
            statement.setString(2, email);
            statement.setString(3, departamento);

            statement.executeUpdate();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                }
            }
        }

        throw new SQLException("Erro ao obter id do professor inserido.");
    }

    /**
     * Insere um RIT e retorna o id gerado.
     */
    private static int inserirRIT(int professorId, String semestre, int ano) throws SQLException {
        String sql = """
                    INSERT INTO rits (professor_id, semestre, ano)
                    VALUES (?, ?, ?)
                """;

        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            statement.setInt(1, professorId);
            statement.setString(2, semestre);
            statement.setInt(3, ano);

            statement.executeUpdate();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                }
            }
        }

        throw new SQLException("Erro ao obter id do RIT inserido.");
    }

    private static void inserirDisciplina(
            int ritId,
            String nome,
            String codigo,
            int cargaHoraria,
            String curso) throws SQLException {
        String sql = """
                    INSERT INTO disciplinas (rit_id, nome, codigo, carga_horaria, curso)
                    VALUES (?, ?, ?, ?, ?)
                """;

        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, ritId);
            statement.setString(2, nome);
            statement.setString(3, codigo);
            statement.setInt(4, cargaHoraria);
            statement.setString(5, curso);

            statement.executeUpdate();
        }
    }

    private static void inserirOrientacao(
            int ritId,
            String nomeAluno,
            String tipo,
            String tituloTrabalho,
            String curso) throws SQLException {
        String sql = """
                    INSERT INTO orientacoes (rit_id, nome_aluno, tipo, titulo_trabalho, curso)
                    VALUES (?, ?, ?, ?, ?)
                """;

        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, ritId);
            statement.setString(2, nomeAluno);
            statement.setString(3, tipo);
            statement.setString(4, tituloTrabalho);
            statement.setString(5, curso);

            statement.executeUpdate();
        }
    }

    private static void inserirArtigo(
            int ritId,
            String titulo,
            String autores,
            String eventoOuRevista,
            int anoPublicacao) throws SQLException {
        String sql = """
                    INSERT INTO artigos (rit_id, titulo, autores, nome_evento_ou_revista, ano_publicacao)
                    VALUES (?, ?, ?, ?, ?)
                """;

        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, ritId);
            statement.setString(2, titulo);
            statement.setString(3, autores);
            statement.setString(4, eventoOuRevista);
            statement.setInt(5, anoPublicacao);

            statement.executeUpdate();
        }
    }

    private static void inserirCoordenacao(
            int ritId,
            String descricao,
            String cargo,
            int cargaHoraria) throws SQLException {
        String sql = """
                    INSERT INTO atividades_coordenacao (rit_id, descricao, cargo, carga_horaria)
                    VALUES (?, ?, ?, ?)
                """;

        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, ritId);
            statement.setString(2, descricao);
            statement.setString(3, cargo);
            statement.setInt(4, cargaHoraria);

            statement.executeUpdate();
        }
    }

    /**
     * Verifica se já existe algum professor cadastrado no banco.
     *
     * @return true se já existe pelo menos um professor, false caso contrário
     * @throws SQLException caso ocorra erro na consulta
     */
    private static boolean existeAlgumProfessor() throws SQLException {
        String sql = "SELECT COUNT(*) AS total FROM professores";

        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()) {

            if (resultSet.next()) {
                return resultSet.getInt("total") > 0;
            }
        }

        return false;
    }
}