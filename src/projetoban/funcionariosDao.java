package projetoban;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class funcionariosDao {

    
    public static void inserirFuncionario() {
        
        Scanner input = new Scanner(System.in);
        Connection con = null;
        double salario = 0;
        int idUnidade = 0;
        boolean inputSalarioValido;
        boolean inputUnidadeValido;

        try {
            
            System.out.println("Insira os dados do novo funcionário:");
            
            System.out.print("CPF (Chave Primária): ");
            String cpf = input.nextLine(); 
            
            System.out.print("Nome: ");
            String nome = input.nextLine(); 
            
            System.out.print("Telefone: ");
            String telefone = input.nextLine();
            
            do {
                try {
                    System.out.print("Salário: ");
                    salario = input.nextDouble();
                    inputSalarioValido = true; 
                } catch (InputMismatchException e) {
                    System.out.println("❌ Erro de input: O salário deve ser um valor numérico (ex: 3500,50). Tente novamente.");
                    input.nextLine(); 
                    inputSalarioValido = false;
                }
            } while (!inputSalarioValido);
            
            input.nextLine();
            
            do {
                try {
                    System.out.print("ID da Unidade (Número): ");
                    idUnidade = input.nextInt();
                    inputUnidadeValido = true; 
                } catch (InputMismatchException e) {
                    System.out.println("❌ Erro de input: O ID da Unidade deve ser um número inteiro. Tente novamente.");
                    input.nextLine(); 
                    inputUnidadeValido = false;
                }
            } while (!inputUnidadeValido);
            
            
            input.nextLine();
            

            funcionarios funcionario = new funcionarios(cpf, nome, telefone, salario, idUnidade);
            
            String sql = "INSERT INTO Funcionario (CPF, Nome, Telefone, Salário, IdUnidade) VALUES (?, ?, ?, ?, ?)";
            
            con = conexao.getConnection();
            if (con == null) {
                System.out.println("❌ Falha crítica: Conexão não estabelecida.");
                return;
            }
            
            try (PreparedStatement stmt = con.prepareStatement(sql)) {
                
                stmt.setString(1, funcionario.getCpf());
                stmt.setString(2, funcionario.getNome());
                stmt.setString(3, funcionario.getTelefone());
                stmt.setDouble(4, funcionario.getSalario());
                stmt.setInt(5, funcionario.getIdUnidade()); 
                
                int linhasAfetadas = stmt.executeUpdate();
                
                if (linhasAfetadas > 0) {
                    System.out.println("✅ INSERÇÃO BEM-SUCEDIDA! Funcionário: " + funcionario.getNome());
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

    
    public static void removerFuncionario() {
        
        Scanner input = new Scanner(System.in);
        
        System.out.print("Insira o CPF do Funcionário a ser removido: ");
        String cpf = input.nextLine(); 
        
        if(instrutorDao.instrutorExiste(cpf)){
            instrutorDao.removerInstrutorAux(cpf);
        }
        
        
        String sql = "DELETE FROM Funcionario WHERE CPF = ?";
        
        try (Connection con = conexao.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            if (con == null) {
                System.out.println("❌ Falha crítica: Conexão não estabelecida.");
                return;
            }

            stmt.setString(1, cpf);
            
            int linhasAfetadas = stmt.executeUpdate();
            
            if (linhasAfetadas > 0) {
                System.out.println("✅ REMOÇÃO BEM-SUCEDIDA!");
                System.out.println("Funcionário com CPF " + cpf + " foi removido.");
            } else {
                System.out.println("❌ Falha na remoção. Funcionário com CPF " + cpf + " não encontrado.");
            }

        } catch (SQLException e) {
            System.out.println("❌ Erro ao executar a remoção no banco de dados.");
            System.out.println("Detalhes do Erro: " + e.getMessage());
        }
    }

    
    public static void listarFuncionarios() {
        
        
        String sql = "SELECT CPF, Nome, Telefone, Salário, IdUnidade FROM Funcionario ORDER BY Nome";
        
        try (Connection con = conexao.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (con == null) {
                System.out.println("❌ Falha crítica: Conexão não estabelecida.");
                return;
            }

            if (!rs.isBeforeFirst()) { 
                System.out.println("Nenhum funcionário encontrado na tabela.");
                return;
            }

            System.out.println("-------------------------------------------------------------------------------------------------");
            System.out.printf("| %-15s | %-25s | %-15s | %-10s | %s\n", "CPF", "NOME", "TELEFONE", "SALÁRIO", "ID UNIDADE");
            System.out.println("-------------------------------------------------------------------------------------------------");
            
            while (rs.next()) {
                funcionarios funcionario = new funcionarios(
                    rs.getString("CPF"),
                    rs.getString("Nome"),
                    rs.getString("Telefone"),
                    rs.getDouble("Salário"),
                    rs.getInt("IdUnidade") 
                );
                
                
                System.out.printf("| %-15s | %-25s | %-15s | R$ %.2f | %-10d\n", 
                                  funcionario.getCpf(), 
                                  funcionario.getNome(), 
                                  funcionario.getTelefone(), 
                                  funcionario.getSalario(),
                                  funcionario.getIdUnidade());
            }   
            System.out.println("-------------------------------------------------------------------------------------------------");

        } catch (SQLException e) {
            System.out.println("❌ Erro ao listar funcionários no banco de dados.");
            System.out.println("Detalhes do Erro: " + e.getMessage());
        }
    }
}
