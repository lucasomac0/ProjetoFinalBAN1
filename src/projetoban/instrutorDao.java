package projetoban;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class instrutorDao {

    
    public static void inserirInstrutor() {
        
        Scanner input = new Scanner(System.in);
        Connection con = null;
        String cpf;

        try {
            
            System.out.println("--- 7. INSERÇÃO DE NOVO INSTRUTOR (CREATE) ---");
            System.out.println("Insira os dados do instrutor (O CPF DEVE existir em Funcionario):");
            
            System.out.print("CPF do Funcionário a ser Instrutor: ");
            cpf = input.nextLine(); 
            
            System.out.print("CREF (Registro Profissional): ");
            String cref = input.nextLine(); 
            
            System.out.print("Especialidade: ");
            String especialidade = input.nextLine();
            
            System.out.print("Horários (Ex: 08:00:00): ");
            String horarios = input.nextLine(); 
            
            con = conexao.getConnection();
            if (con == null) {
                System.out.println("❌ Falha crítica: Conexão não estabelecida.");
                return;
            }
            
            String selectSql = "SELECT CPF FROM Funcionario WHERE CPF = ?";
            boolean funcionarioExiste = false;

            try (PreparedStatement checkStmt = con.prepareStatement(selectSql)) {
                checkStmt.setString(1, cpf);
                try (ResultSet rs = checkStmt.executeQuery()) {
                    if (rs.next()) {
                        funcionarioExiste = true;
                    }
                }
            }
            
            if (!funcionarioExiste) {
                System.out.println("\n❌ FALHA NA INSERÇÃO!");
                System.out.println("Funcionário com CPF " + cpf + " não encontrado na tabela Funcionario.");
                System.out.println("O instrutor deve ser um funcionário previamente cadastrado.");
                return;
            }

            instrutor novoInstrutor = new instrutor(cpf, cref, especialidade, horarios);
            String insertSql = "INSERT INTO Instrutor (CPF, CREF, Especialidade, Horários) VALUES (?, ?, ?, ?)";
            
            try (PreparedStatement insertStmt = con.prepareStatement(insertSql)) {
                
                insertStmt.setString(1, novoInstrutor.getCpf());
                insertStmt.setString(2, novoInstrutor.getCref());
                insertStmt.setString(3, novoInstrutor.getEspecialidade());
                insertStmt.setString(4, novoInstrutor.getHorarios());
                
                int linhasAfetadas = insertStmt.executeUpdate();
                
                if (linhasAfetadas > 0) {
                    System.out.println("✅ INSERÇÃO BEM-SUCEDIDA! Instrutor CPF: " + novoInstrutor.getCpf());
                } else {
                    System.out.println("❌ Falha na inserção do instrutor. Nenhuma linha afetada.");
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
        }
    }

    
    public static void removerInstrutor() {
        
        Scanner input = new Scanner(System.in);
        
        System.out.print("Insira o CPF do Instrutor a ser removido: ");
        String cpf = input.nextLine(); 
        
        removerInstrutorAux(cpf);
        
        
    }


    public static boolean instrutorExiste(String cpf) {
        String sql = "SELECT CPF FROM Instrutor WHERE CPF = ?";
        
        try (Connection con = conexao.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            if (con == null) {
                System.out.println("❌ Falha crítica: Conexão não estabelecida.");
                return false;
            }

            stmt.setString(1, cpf);
            
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next(); 
            }

        } catch (SQLException e) {
            System.out.println("❌ Erro ao verificar a existência do instrutor no banco de dados.");
            System.out.println("Detalhes do Erro: " + e.getMessage());
            return false; 
        }
    }


    
    public static void removerInstrutorAux(String cpf) {
        
        turmaDao.removerTurmasPorInstrutor(cpf);
        
        String sql = "DELETE FROM Instrutor WHERE CPF = ?";
        
        try (Connection con = conexao.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            if (con == null) {
                return;
            }

            stmt.setString(1, cpf);            
            
            int linhasAfetadas = stmt.executeUpdate();
            
            if (linhasAfetadas > 0) {
                System.out.println("✅ REMOÇÃO DE INSTRUTOR BEM-SUCEDIDA!");
            }
            

        } catch (SQLException e) {
            System.out.println("❌ Erro ao executar a remoção do instrutor no banco de dados.");
            System.out.println("Detalhes do Erro: " + e.getMessage());
        }
    }
    
    
    public static void listarInstrutores() {
        
        String sql = "SELECT CPF, CREF, Especialidade, Horários FROM Instrutor ORDER BY CPF";
        
        try (Connection con = conexao.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (con == null) {
                System.out.println("❌ Falha crítica: Conexão não estabelecida.");
                return;
            }

            if (!rs.isBeforeFirst()) { 
                System.out.println("Nenhum instrutor encontrado na tabela.");
                return;
            }

            System.out.println("-------------------------------------------------------------------------------");
            System.out.printf("| %-15s | %-10s | %-20s | %s\n", "CPF", "CREF", "ESPECIALIDADE", "HORÁRIO");
            System.out.println("-------------------------------------------------------------------------------");
            
            while (rs.next()) {
                instrutor instrutorEncontrado = new instrutor(
                    rs.getString("CPF"),
                    rs.getString("CREF"),
                    rs.getString("Especialidade"),
                    rs.getString("Horários") 
                );
                
                System.out.printf("| %-15s | %-10s | %-20s | %s\n", 
                                  instrutorEncontrado.getCpf(), 
                                  instrutorEncontrado.getCref(), 
                                  instrutorEncontrado.getEspecialidade(), 
                                  instrutorEncontrado.getHorarios());
            }
            System.out.println("-------------------------------------------------------------------------------");

        } catch (SQLException e) {
            System.out.println("❌ Erro ao listar instrutores no banco de dados.");
            System.out.println("Detalhes do Erro: " + e.getMessage());
        }
    }
}