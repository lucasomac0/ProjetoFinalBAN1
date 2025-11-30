package projetoban;


public class participacao {
    
    // Atributos privados
    private int matriculaCliente; 
    private int codigoTurma;     
    private int numeroChamada;    

    
    public participacao(int matriculaCliente, int codigoTurma, int numeroChamada) {
        this.matriculaCliente = matriculaCliente;
        this.codigoTurma = codigoTurma;
        this.numeroChamada = numeroChamada;
    }
    

    public int getMatriculaCliente() {
        return matriculaCliente;
    }

    public int getCodigoTurma() {
        return codigoTurma;
    }

    public int getNumeroChamada() {
        return numeroChamada;
    }


    public void setMatriculaCliente(int matriculaCliente) {
        this.matriculaCliente = matriculaCliente;
    }

    public void setCodigoTurma(int codigoTurma) {
        this.codigoTurma = codigoTurma;
    }
    
    public void setNumeroChamada(int numeroChamada) {
        this.numeroChamada = numeroChamada;
    }

    
    @Override
    public String toString() {
        return "Participacao{" +
                "matriculaCliente=" + matriculaCliente +
                ", codigoTurma=" + codigoTurma +
                ", numeroChamada=" + numeroChamada +
                '}';
    }
}