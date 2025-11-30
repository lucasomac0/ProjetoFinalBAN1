package projetoban;


public class clientes {
    
    private int matricula;
    private String nome;
    private String cpf;
    private String telefone;
    private String email;

    
    public clientes(int matricula, String nome, String cpf, String telefone, String email) {
        this.matricula = matricula;
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
        this.email = email;
    }
    

    
    public int getMatricula() {
        return matricula;
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getEmail() {
        return email;
    }


    public void setMatricula(int matricula) {
        this.matricula = matricula;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    
    
    @Override
    public String toString() {
        return "ClienteBean{" +
                "matricula=" + matricula +
                ", nome='" + nome + '\'' +
                ", cpf='" + cpf + '\'' +
                ", telefone='" + telefone + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}