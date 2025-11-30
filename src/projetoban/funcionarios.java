package projetoban;


public class funcionarios {
    
    private String cpf;
    private String nome;
    private String telefone;
    private double salario;
    private int idUnidade; 

    
    public funcionarios(String cpf, String nome, String telefone, double salario, int idUnidade) {
        this.cpf = cpf;
        this.nome = nome;
        this.telefone = telefone;
        this.salario = salario;
        this.idUnidade = idUnidade; 
    }
    

    public String getCpf() {
        return cpf;
    }

    public String getNome() {
        return nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public double getSalario() {
        return salario;
    }

    public int getIdUnidade() {
        return idUnidade;
    }


    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }
    
    public void setIdUnidade(int idUnidade) {
        this.idUnidade = idUnidade;
    }
    
    
    
    @Override
    public String toString() {
        return "Funcionarios{" +
                "cpf='" + cpf + '\'' +
                ", nome='" + nome + '\'' +
                ", telefone='" + telefone + '\'' +
                ", salario=" + salario +
                // 🚨 Novo atributo no toString
                ", idUnidade=" + idUnidade +
                '}';
    }
}