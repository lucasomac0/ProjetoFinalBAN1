package projetoban;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;
import java.sql.ResultSet;
import java.util.InputMismatchException;

public class clientesDao {

    
    public static void inserirCliente() {
        
        Scanner input = new Scanner(System.in);
        Connection con = null;
        int matricula = 0;
        boolean inputValido;

        try {
            
            System.out.println("Insira os dados do novo cliente:");
            
            
            
            do {
            try {
                System.out.print("Matrícula (Numérico): ");
                matricula = input.nextInt(); 
                inputValido = true; 
            } catch (InputMismatchException e) {
                System.out.println("❌ Erro: A matrícula deve ser um valor numérico. Tente novamente.");
                input.nextLine(); 
                inputValido = false;
            }
        } while (!inputValido);
            
            
            
            input.nextLine(); 
            
            System.out.print("Nome: ");
            String nome = input.nextLine(); 
            
            System.out.print("CPF: ");
            String cpf = input.nextLine();
            
            System.out.print("Telefone: ");
            String telefone = input.nextLine(); 
            
            System.out.print("Email: ");
            String email = input.nextLine();

            clientes cliente = new clientes(matricula, nome, cpf, telefone, email);

            
            
            con = conexao.getConnection();
            if (con == null) {
                System.out.println("❌ Falha crítica: Conexão não estabelecida.");
                return;
            }
            
            String sql = "INSERT INTO Cliente (Matrícula, Nome, CPF, Telefone, Email) VALUES (?, ?, ?, ?, ?)";
            
            try (PreparedStatement stmt = con.prepareStatement(sql)) {
                
                stmt.setInt(1, cliente.getMatricula());
                stmt.setString(2, cliente.getNome());
                stmt.setString(3, cliente.getCpf());
                stmt.setString(4, cliente.getTelefone());
                stmt.setString(5, cliente.getEmail());
                
                int linhasAfetadas = stmt.executeUpdate();
                
                if (linhasAfetadas > 0) {
                    System.out.println("✅ INSERÇÃO BEM-SUCEDIDA!");
                    System.out.println("Cliente adicionado: " + cliente.getNome());
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
        }
    }
    
    public static void removerCliente(){
        Scanner input = new Scanner(System.in);
        
        System.out.print("Insira a Matrícula do Cliente a ser removido: ");
        int matricula = input.nextInt();
        
        participacaoDao.clienteRemovido(matricula);
        
        String sql = "DELETE FROM Cliente WHERE Matrícula = ?";
        
        try (Connection con = conexao.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            if (con == null) {
                System.out.println("❌ Falha crítica: Conexão não estabelecida.");
                return;
            }

            stmt.setInt(1, matricula);
            
            int linhasAfetadas = stmt.executeUpdate();
            
            if (linhasAfetadas > 0) {
                System.out.println("✅ REMOÇÃO BEM-SUCEDIDA!");
                System.out.println("Cliente com Matrícula " + matricula + " foi removido.");
            } else {
                System.out.println("❌ Falha na remoção. Cliente com Matrícula " + matricula + " não encontrado.");
            }

        } catch (SQLException e) {
            System.out.println("❌ Erro ao executar a remoção no banco de dados.");
            System.out.println("Detalhes do Erro: " + e.getMessage());
        }
    }

    public static void listarClientes() {
        
        
        String sql = "SELECT Matrícula, Nome, CPF, Telefone, Email FROM Cliente ORDER BY Nome";
        
        try (Connection con = conexao.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (con == null) {
                System.out.println("❌ Falha crítica: Conexão não estabelecida.");
                return;
            }

            if (!rs.isBeforeFirst()) { 
                System.out.println("Nenhum cliente encontrado na tabela.");
                return;
            }

            System.out.println("----------------------------------------------------------------------------------");
            System.out.printf("| %-10s | %-20s | %-15s | %s\n", "MATRÍCULA", "NOME", "CPF", "EMAIL");
            System.out.println("----------------------------------------------------------------------------------");
            
            while (rs.next()) {
                clientes cliente = new clientes(
                    rs.getInt("Matrícula"),
                    rs.getString("Nome"),
                    rs.getString("CPF"),
                    rs.getString("Telefone"),
                    rs.getString("Email")
                );
                
                System.out.printf("| %-10d | %-20s | %-15s | %s\n", 
                                  cliente.getMatricula(), 
                                  cliente.getNome(), 
                                  cliente.getCpf(), 
                                  cliente.getEmail());
            }
            System.out.println("----------------------------------------------------------------------------------");


        } catch (SQLException e) {
            System.out.println("❌ Erro ao listar clientes no banco de dados.");
            System.out.println("Detalhes do Erro: " + e.getMessage());
        }
    }
    
    public static void listarClientesComTurmas() {
        
        
        String sql = "SELECT " +
                     "C.Nome AS NomeCliente, " +
                     "T.Modalidade AS NomeTurma " +
                     "FROM Cliente C " +
                     "LEFT JOIN Participação P ON C.Matrícula = P.MatrículaCliente " +
                     "LEFT JOIN Turma T ON P.CódigoTurma = T.Código " +
                     "ORDER BY C.Nome, T.Modalidade";
        
        try (Connection con = conexao.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (con == null) {
                System.out.println("❌ Falha crítica: Conexão não estabelecida.");
                return;
            }

            if (!rs.isBeforeFirst()) { 
                System.out.println("Nenhum cliente cadastrado no sistema.");
                return;
            }

            System.out.println("-----------------------------------------------------");
            System.out.printf("| %-30s | %s\n", "NOME DO CLIENTE", "MODALIDADE (TURMA)");
            System.out.println("-----------------------------------------------------");
            
            while (rs.next()) {
                String nomeCliente = rs.getString("NomeCliente");
                String nomeTurma = rs.getString("NomeTurma");
                
                if (nomeTurma == null) {
                    nomeTurma = "Nenhuma";
                }
                
                System.out.printf("| %-30s | %s\n", nomeCliente, nomeTurma);
            }
            System.out.println("-----------------------------------------------------");

        } catch (SQLException e) {
            System.out.println("❌ Erro ao listar clientes e turmas no banco de dados.");
            System.out.println("Detalhes do Erro: " + e.getMessage());
        }
    }
    
}
