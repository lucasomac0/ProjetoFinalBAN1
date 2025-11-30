package projetoban;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class turmaDao {

    
    public static void inserirTurma() {
        
        Scanner input = new Scanner(System.in);
        Connection con = null;
        
        int codigo = 0;
        int qtdVagas = 0;
        String cpfInstrutor;

        try {
            System.out.println("Insira os dados da nova turma:");

            boolean inputCodigoValido;
            do {
                try {
                    System.out.print("Código da Turma (Chave Primária): ");
                    codigo = input.nextInt();
                    inputCodigoValido = true;
                } catch (InputMismatchException e) {
                    System.out.println("❌ Erro: O código deve ser um número inteiro. Tente novamente.");
                    input.nextLine();
                    inputCodigoValido = false;
                }
            } while (!inputCodigoValido);
            input.nextLine(); 

            boolean inputVagasValido;
            do {
                try {
                    System.out.print("Quantidade de Vagas: ");
                    qtdVagas = input.nextInt();
                    inputVagasValido = true;
                } catch (InputMismatchException e) {
                    System.out.println("❌ Erro: A quantidade de vagas deve ser um número inteiro. Tente novamente.");
                    input.nextLine();
                    inputVagasValido = false;
                }
            } while (!inputVagasValido);
            input.nextLine(); 

            System.out.print("Modalidade: ");
            String modalidade = input.nextLine();

            System.out.print("CPF do Instrutor: ");
            cpfInstrutor = input.nextLine(); 
            
            
            con = conexao.getConnection();
            if (con == null) {
                System.out.println("❌ Falha crítica: Conexão não estabelecida.");
                return;
            }

            
            String checkInstrutorSql = "SELECT CPF FROM Instrutor WHERE CPF = ?";
            if (!checarExistencia(con, checkInstrutorSql, cpfInstrutor, "Instrutor")) {
                System.out.println("❌ FALHA: O CPF do instrutor (" + cpfInstrutor + ") não está cadastrado como Instrutor.");
                return;
            }

            turma novaTurma = new turma(codigo, qtdVagas, modalidade, cpfInstrutor);
            
            String insertSql = "INSERT INTO Turma (Código, QtdVagas, Modalidade, CPFInstrutor) VALUES (?, ?, ?, ?)";
            
            try (PreparedStatement stmt = con.prepareStatement(insertSql)) {
                
                stmt.setInt(1, novaTurma.getCodigo());
                stmt.setInt(2, novaTurma.getQtdVagas());
                stmt.setString(3, novaTurma.getModalidade());
                stmt.setString(4, novaTurma.getCpfInstrutor());
                
                int linhasAfetadas = stmt.executeUpdate();
                
                if (linhasAfetadas > 0) {
                    System.out.println("✅ INSERÇÃO BEM-SUCEDIDA! Turma Código: " + novaTurma.getCodigo());
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

    
    private static boolean checarExistencia(Connection con, String sql, String valor, String tabela) throws SQLException {
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, valor);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }

    
    private static void removerTurmaAux(int codigo) {
        
        String sql = "DELETE FROM Turma WHERE Código = ?";
        
        try (Connection con = conexao.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            if (con == null) {
                return;
            }

            stmt.setInt(1, codigo);
            
            int linhasAfetadas = stmt.executeUpdate();
            
            if (linhasAfetadas > 0) {
                System.out.println("✅ Dependência resolvida: Turma " + codigo + " removida.");
            }

        } catch (SQLException e) {
            System.out.println("❌ Erro ao remover turma auxiliar no banco de dados: " + e.getMessage());
        }
    }
    
    
    public static void removerTurmasPorInstrutor(String cpfInstrutor) {
        
        String selectSql = "SELECT Código FROM Turma WHERE CPFInstrutor = ?";
        
        try (Connection con = conexao.getConnection();
             PreparedStatement selectStmt = con.prepareStatement(selectSql)) {

            if (con == null) {
                return;
            }

            selectStmt.setString(1, cpfInstrutor);
            
            try (ResultSet rs = selectStmt.executeQuery()) {
                
                while (rs.next()) {
                    int codigoTurma = rs.getInt("Código");
                    
                    
                    participacaoDao.turmaRemovida(codigoTurma);
                    
                    removerTurmaAux(codigoTurma);
                }
            }

        } catch (SQLException e) {
            System.out.println("❌ Erro ao resolver dependências de Turmas para o Instrutor " + cpfInstrutor + ": " + e.getMessage());
        }
    }

    
    public static void removerTurma() {
        
        Scanner input = new Scanner(System.in);
        
        int codigo = -1;
        boolean inputValido;

        do {
            try {
                System.out.print("Insira o Código da Turma a ser removida: ");
                codigo = input.nextInt();
                inputValido = true;
            } catch (InputMismatchException e) {
                System.out.println("❌ Erro: O código deve ser um número inteiro. Tente novamente.");
                input.nextLine(); 
                inputValido = false;
            }
        } while (!inputValido);
        
        
        participacaoDao.turmaRemovida(codigo);
        
        String sql = "DELETE FROM Turma WHERE Código = ?";
        
        try (Connection con = conexao.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            if (con == null) {
                System.out.println("❌ Falha crítica: Conexão não estabelecida.");
                return;
            }

            stmt.setInt(1, codigo);
            
            int linhasAfetadas = stmt.executeUpdate();
            
            if (linhasAfetadas > 0) {
                System.out.println("✅ REMOÇÃO BEM-SUCEDIDA!");
                System.out.println("Turma com Código " + codigo + " foi removida.");
            } else {
                System.out.println("❌ Falha na remoção. Turma com Código " + codigo + " não encontrada.");
            }

        } catch (SQLException e) {
            System.out.println("❌ Erro ao executar a remoção no banco de dados.");
            System.out.println("Detalhes do Erro: " + e.getMessage());
        }
    }

    
    public static void listarTurmas() {
        
        
        String sql = "SELECT Código, QtdVagas, Modalidade, CPFInstrutor FROM Turma ORDER BY Código";
        
        try (Connection con = conexao.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (con == null) {
                System.out.println("❌ Falha crítica: Conexão não estabelecida.");
                return;
            }

            if (!rs.isBeforeFirst()) { 
                System.out.println("Nenhuma turma encontrada na tabela.");
                return;
            }

            System.out.println("---------------------------------------------------------------------");
            System.out.printf("| %-8s | %-10s | %-20s | %s\n", "CÓDIGO", "VAGAS", "MODALIDADE", "CPF INSTRUTOR");
            System.out.println("---------------------------------------------------------------------");
            
            while (rs.next()) {
                
                
                System.out.printf("| %-8d | %-10d | %-20s | %s\n", 
                                  rs.getInt("Código"), 
                                  rs.getInt("QtdVagas"), 
                                  rs.getString("Modalidade"), 
                                  rs.getString("CPFInstrutor"));
            }
            System.out.println("---------------------------------------------------------------------");

        } catch (SQLException e) {
            System.out.println("❌ Erro ao listar turmas no banco de dados.");
            System.out.println("Detalhes do Erro: " + e.getMessage());
        }
    }
    
    public static void TurmasAcimaDaMedia() {
        
        
        
        
        String sql = "SELECT " +
                     "T.Código, T.Modalidade, COUNT(P.MatrículaCliente) AS TotalParticipantes " +
                     "FROM Turma T " +
                     "INNER JOIN Participação P ON T.Código = P.CódigoTurma " +
                     "GROUP BY T.Código, T.Modalidade " +
                     "HAVING COUNT(P.MatrículaCliente) > (" +
                         "SELECT AVG(NumParticipantes) " +
                         "FROM (" +
                             "SELECT COUNT(MatrículaCliente) AS NumParticipantes " +
                             "FROM Participação " +
                             "GROUP BY CódigoTurma" +
                         ") AS MediaParticipacao" +
                     ") " +
                     "ORDER BY TotalParticipantes DESC";
        
        Connection con = null;
        try {
            con = conexao.getConnection();
            if (con == null) {
                System.out.println("❌ Falha crítica: Conexão não estabelecida.");
                return;
            }
            
            try (PreparedStatement stmt = con.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {
            
                if (!rs.isBeforeFirst()) { 
                    System.out.println("Nenhuma turma encontrada com participantes acima da média.");
                    System.out.println("Isso pode ocorrer se houver poucas turmas ou se todas tiverem a mesma contagem de participantes.");
                    return;
                }

                System.out.println("------------------------------------------------------------------");
                System.out.printf("| %-8s | %-25s | %s\n", "CÓDIGO", "MODALIDADE", "PARTICIPANTES");
                System.out.println("------------------------------------------------------------------");
                
                while (rs.next()) {
                    int codigo = rs.getInt("Código");
                    String modalidade = rs.getString("Modalidade");
                    int totalParticipantes = rs.getInt("TotalParticipantes");
                    
                    System.out.printf("| %-8d | %-25s | %d\n", codigo, modalidade, totalParticipantes);
                }
                System.out.println("------------------------------------------------------------------");
            }

        } catch (SQLException e) {
            System.out.println("❌ Erro ao executar a consulta complexa no banco de dados.");
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
    
}