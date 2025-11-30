package projetoban;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class participacaoDao {

    
    public static void inserirParticipacao() {
        
        Scanner input = new Scanner(System.in);
        Connection con = null;
        
        int matriculaCliente = 0;
        int codigoTurma = 0;
        int numeroChamada = 0; 

        try {

            boolean inputMatriculaValida;
            do {
                try {
                    System.out.print("Matrícula do Cliente: ");
                    matriculaCliente = input.nextInt();
                    inputMatriculaValida = true;
                } catch (InputMismatchException e) {
                    System.out.println("❌ Erro: A matrícula deve ser um número inteiro. Tente novamente.");
                    input.nextLine(); 
                    inputMatriculaValida = false;
                }
            } while (!inputMatriculaValida);
            input.nextLine(); 

            boolean inputCodigoValido;
            do {
                try {
                    System.out.print("Código da Turma: ");
                    codigoTurma = input.nextInt();
                    inputCodigoValido = true;
                } catch (InputMismatchException e) {
                    System.out.println("❌ Erro: O código da turma deve ser um número inteiro. Tente novamente.");
                    input.nextLine(); 
                    inputCodigoValido = false;
                }
            } while (!inputCodigoValido);
            input.nextLine(); 
            
            boolean inputChamadaValida;
            do {
                try {
                    System.out.print("Número na Chamada da Turma: ");
                    numeroChamada = input.nextInt();
                    inputChamadaValida = true;
                } catch (InputMismatchException e) {
                    System.out.println("❌ Erro: O número da chamada deve ser um número inteiro. Tente novamente.");
                    input.nextLine(); 
                    inputChamadaValida = false;
                }
            } while (!inputChamadaValida);
            input.nextLine(); 
            
            
            con = conexao.getConnection();
            if (con == null) {
                System.out.println("❌ Falha crítica: Conexão não estabelecida.");
                return;
            }

            

            String checkClienteSql = "SELECT Matrícula FROM Cliente WHERE Matrícula = ?";
            if (!checarExistencia(con, checkClienteSql, String.valueOf(matriculaCliente))) {
                System.out.println("❌ FALHA: A Matrícula do Cliente (" + matriculaCliente + ") não está cadastrada.");
                return;
            }

            String checkTurmaSql = "SELECT Código FROM Turma WHERE Código = ?";
            if (!checarExistencia(con, checkTurmaSql, String.valueOf(codigoTurma))) {
                System.out.println("❌ FALHA: O Código da Turma (" + codigoTurma + ") não está cadastrado.");
                return;
            }
            
            if (participacaoExiste(con, matriculaCliente, codigoTurma)) {
                 System.out.println("❌ FALHA: O Cliente com Matrícula " + matriculaCliente + " já está cadastrado na Turma " + codigoTurma + ".");
                 return;
            }

           
            participacao novaParticipacao = new participacao(matriculaCliente, codigoTurma, numeroChamada);
            
            String insertSql = "INSERT INTO Participação (MatrículaCliente, CódigoTurma, NúmeroChamada) VALUES (?, ?, ?)";
            
            try (PreparedStatement stmt = con.prepareStatement(insertSql)) {
                
                stmt.setInt(1, novaParticipacao.getMatriculaCliente());
                stmt.setInt(2, novaParticipacao.getCodigoTurma());
                stmt.setInt(3, novaParticipacao.getNumeroChamada()); 
                
                int linhasAfetadas = stmt.executeUpdate();
                
                if (linhasAfetadas > 0) {
                    System.out.println("✅ INSERÇÃO BEM-SUCEDIDA! Cliente " + matriculaCliente + " adicionado à Turma " + codigoTurma + ".");
                } else {
                    System.out.println("❌ Falha na inserção. Nenhuma linha afetada.");
                }
            } 

        } catch (SQLException e) {
            System.out.println("❌ Erro ao executar a operação no banco de dados.");
            System.out.println("Detalhes do Erro: " + e.getMessage());
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException ex) {
                    System.out.println("Erro ao fechar a conexão: " + ex.getMessage());
                }
            }
            input.close(); 
        }
    }

    
    private static boolean checarExistencia(Connection con, String sql, String valor) throws SQLException {
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, valor); 
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }
    
   
     private static boolean participacaoExiste(Connection con, int matricula, int codigo) throws SQLException {
        String sql = "SELECT MatrículaCliente FROM Participação WHERE MatrículaCliente = ? AND CódigoTurma = ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, matricula);
            stmt.setInt(2, codigo);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }

    
    public static void removerParticipacao() {
        
        Scanner input = new Scanner(System.in);
        
        int matricula = -1;
        int codigo = -1;
        boolean inputValido;

        do {
            try {
                System.out.print("Insira a Matrícula do Cliente: ");
                matricula = input.nextInt();
                inputValido = true;
            } catch (InputMismatchException e) {
                System.out.println("❌ Erro: A matrícula deve ser um número inteiro. Tente novamente.");
                input.nextLine(); 
                inputValido = false;
            }
        } while (!inputValido);
        input.nextLine();
        
       
        do {
            try {
                System.out.print("Insira o Código da Turma: ");
                codigo = input.nextInt();
                inputValido = true;
            } catch (InputMismatchException e) {
                System.out.println("❌ Erro: O código deve ser um número inteiro. Tente novamente.");
                input.nextLine(); 
                inputValido = false;
            }
        } while (!inputValido);
        input.nextLine();
        
        String sql = "DELETE FROM Participação WHERE MatrículaCliente = ? AND CódigoTurma = ?";
        
        try (Connection con = conexao.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            if (con == null) {
                System.out.println("❌ Falha crítica: Conexão não estabelecida.");
                return;
            }

            stmt.setInt(1, matricula);
            stmt.setInt(2, codigo);
            
            int linhasAfetadas = stmt.executeUpdate();
            
            if (linhasAfetadas > 0) {
                System.out.println("✅ REMOÇÃO BEM-SUCEDIDA!");
                System.out.println("Participação do Cliente " + matricula + " na Turma " + codigo + " foi removida.");
            } else {
                System.out.println("❌ Falha na remoção. Participação não encontrada.");
            }

        } catch (SQLException e) {
            System.out.println("❌ Erro ao executar a remoção no banco de dados.");
            System.out.println("Detalhes do Erro: " + e.getMessage());
        }
    }
    
    
    public static void clienteRemovido(int matricula) {

        String sql = "DELETE FROM Participação WHERE MatrículaCliente = ?";
        
        try (Connection con = conexao.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            if (con == null) {
                System.out.println("❌ Falha crítica: Conexão não estabelecida.");
                return;
            }

            stmt.setInt(1, matricula);
            
            int linhasAfetadas = stmt.executeUpdate();
            
            if (linhasAfetadas > 0) {
                System.out.println("✅ REMOÇÃO AUXILIAR BEM-SUCEDIDA!");
                System.out.println("Foram removidas " + linhasAfetadas + " participações do Cliente Matrícula " + matricula + ".");
            } 

        } catch (SQLException e) {
            System.out.println("❌ Erro ao executar a remoção auxiliar no banco de dados.");
            System.out.println("Detalhes do Erro: " + e.getMessage());
        }
    }

    public static void turmaRemovida(int codigo) {

        String sql = "DELETE FROM Participação WHERE CódigoTurma = ?";
        
        try (Connection con = conexao.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            if (con == null) {
                System.out.println("❌ Falha crítica: Conexão não estabelecida.");
                return;
            }

            stmt.setInt(1, codigo);
            
            int linhasAfetadas = stmt.executeUpdate();
            
            if (linhasAfetadas > 0) {
                System.out.println("✅ REMOÇÃO AUXILIAR BEM-SUCEDIDA!");
                System.out.println("Foram removidas " + linhasAfetadas + " participações da Turma Código " + codigo + ".");
            }

        } catch (SQLException e) {
            System.out.println("❌ Erro ao executar a remoção auxiliar por turma no banco de dados.");
            System.out.println("Detalhes do Erro: " + e.getMessage());
        }
    }
    
    public static void listarParticipacoes() {
        
        
        String sql = "SELECT MatrículaCliente, CódigoTurma, NúmeroChamada FROM Participação ORDER BY MatrículaCliente, CódigoTurma";
        
        try (Connection con = conexao.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (con == null) {
                System.out.println("❌ Falha crítica: Conexão não estabelecida.");
                return;
            }

            if (!rs.isBeforeFirst()) { 
                System.out.println("Nenhuma participação encontrada na tabela.");
                return;
            }

            System.out.println("------------------------------------------------------------------");
            System.out.printf("| %-20s | %-15s | %s\n", "MATRÍCULA CLIENTE", "CÓDIGO TURMA", "NÚMERO CHAMADA");
            System.out.println("------------------------------------------------------------------");
            
            while (rs.next()) {
                participacao participacaoEncontrada = new participacao(
                    rs.getInt("MatrículaCliente"),
                    rs.getInt("CódigoTurma"),
                    rs.getInt("NúmeroChamada")
                );
                
                System.out.printf("| %-20d | %-15d | %d\n", 
                                  participacaoEncontrada.getMatriculaCliente(), 
                                  participacaoEncontrada.getCodigoTurma(),
                                  participacaoEncontrada.getNumeroChamada());
            }
            System.out.println("------------------------------------------------------------------");

        } catch (SQLException e) {
            System.out.println("❌ Erro ao listar participações no banco de dados.");
            System.out.println("Detalhes do Erro: " + e.getMessage());
        }
    }
}