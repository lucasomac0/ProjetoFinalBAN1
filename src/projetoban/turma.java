package projetoban;

/**
 * Classe de Modelo (Bean) para a entidade Turma.
 * (NúmeroSala removido conforme especificação do usuário)
 */
public class turma {
    
    // Atributos privados
    private int codigo;         // Numérico: Chave Primária
    private int qtdVagas;       // Numérico: Quantidade de vagas
    private String modalidade;  // Texto: Modalidade da Turma
    private String cpfInstrutor;// Texto: Chave Estrangeira para Instrutor
    // private int numeroSala; // REMOVIDO

    /**
     * Construtor da classe Turma.
     * @param codigo O identificador da Turma.
     * @param qtdVagas A quantidade de vagas disponíveis.
     * @param modalidade A modalidade da aula.
     * @param cpfInstrutor O CPF do instrutor responsável pela Turma.
     */
    public turma(int codigo, int qtdVagas, String modalidade, String cpfInstrutor) {
        this.codigo = codigo;
        this.qtdVagas = qtdVagas;
        this.modalidade = modalidade;
        this.cpfInstrutor = cpfInstrutor;
    }
    
    // --- GETTERS ---

    public int getCodigo() {
        return codigo;
    }

    public int getQtdVagas() {
        return qtdVagas;
    }

    public String getModalidade() {
        return modalidade;
    }

    public String getCpfInstrutor() {
        return cpfInstrutor;
    }

    // --- SETTERS ---

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public void setQtdVagas(int qtdVagas) {
        this.qtdVagas = qtdVagas;
    }

    public void setModalidade(String modalidade) {
        this.modalidade = modalidade;
    }

    public void setCpfInstrutor(String cpfInstrutor) {
        this.cpfInstrutor = cpfInstrutor;
    }

    // --- MÉTODO toString() ---
    
    @Override
    public String toString() {
        return "Turma{" +
                "codigo=" + codigo +
                ", qtdVagas=" + qtdVagas +
                ", modalidade='" + modalidade + '\'' +
                ", cpfInstrutor='" + cpfInstrutor + '\'' +
                '}';
    }
}