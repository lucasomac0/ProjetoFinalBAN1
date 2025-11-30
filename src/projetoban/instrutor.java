package projetoban;


public class instrutor {
    
    private String cpf; 
    private String cref; 
    private String especialidade;
    private String horarios; 

    
    public instrutor(String cpf, String cref, String especialidade, String horarios) {
        this.cpf = cpf;
        this.cref = cref;
        this.especialidade = especialidade;
        this.horarios = horarios; 
    }
    

    public String getCpf() {
        return cpf;
    }

    public String getCref() {
        return cref;
    }

    public String getEspecialidade() {
        return especialidade;
    }
    
    public String getHorarios() {
        return horarios;
    }


    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setCref(String cref) {
        this.cref = cref;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }
    
    public void setHorarios(String horarios) {
        this.horarios = horarios;
    }
    
    
    @Override
    public String toString() {
        return "Instrutor{" +
                "cpf='" + cpf + '\'' +
                ", cref='" + cref + '\'' +
                ", especialidade='" + especialidade + '\'' +
                ", horarios='" + horarios + '\'' +
                '}';
    }
}