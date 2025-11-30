package projetoban;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class conexao {

    private static final String url = "jdbc:mysql://localhost:3306/udesc";
    private static final String usuario = "root";
    private static final String senha = "udesc";

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(url, usuario, senha);
        } catch (SQLException e) {
            System.out.println("Erro ao conectar: " + e.getMessage());
            return null;
        }
    }

    public static void main(String[] args) {
        Connection c = conexao.getConnection();

        if (c != null) {
            System.out.println("Conectado com sucesso!");
            try {
                c.close();
            } catch (SQLException e) {
                System.out.println("Erro ao fechar: " + e.getMessage());
            }
        }
    }
}
