package projetoban;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Projetoban {

    
    public static void exibirMenu() {
        System.out.println("\n=======================================================");
        System.out.println("SISTEMA DE GERENCIAMENTO DA ACADEMIA️");
        System.out.println("=======================================================");
        
        System.out.println("--- GESTÃO DE CLIENTES ---");
        System.out.println(" 1. Inserir Novo Cliente ");
        System.out.println(" 2. Remover Cliente ");
        System.out.println(" 3. Listar Clientes ");
        System.out.println("16. Listar Todos os Clientes e suas Turmas ");
        
        System.out.println("\n--- GESTÃO DE FUNCIONÁRIOS ---");
        System.out.println(" 4. Inserir Novo Funcionário ");
        System.out.println(" 5. Remover Funcionário ");
        System.out.println(" 6. Listar Funcionários ");
        
        System.out.println("\n--- GESTÃO DE INSTRUTORES ---");
        System.out.println(" 7. Inserir Novo Instrutor ");
        System.out.println(" 8. Remover Instrutor ");
        System.out.println(" 9. Listar Instrutores ");
        
        System.out.println("\n--- GESTÃO DE TURMAS ---");
        System.out.println("10. Inserir Nova Turma ");
        System.out.println("11. Remover Turma ");
        System.out.println("12. Listar Turmas ");
        System.out.println("17. Listar Turmas Acima da Média de Participantes ");

        System.out.println("\n--- GESTÃO DE PARTICIPAÇÃO ---");
        System.out.println("13. Matricular Cliente em Turma");
        System.out.println("14. Desmatricular Cliente de Turma");
        System.out.println("15. Listar Todas as Matriculas em Turmas");
        
        System.out.println("\n--- SAIR ---");
        System.out.println("99. Sair do Sistema");
        System.out.println("=======================================================");
        System.out.print("Digite a opção desejada: ");
    }

    
    public static void main(String[] args) {
        try (Scanner input = new Scanner(System.in)) {
            int entrada;
            boolean continuar = true;
            
            while (continuar) {
                exibirMenu();
                
                try {
                    entrada = input.nextInt();
                    input.nextLine(); 
                    
                    switch (entrada) {
                        case 1 -> clientesDao.inserirCliente();
                        case 2 -> clientesDao.removerCliente();
                        case 3 -> clientesDao.listarClientes();
                        
                        case 4 -> funcionariosDao.inserirFuncionario();
                        case 5 -> funcionariosDao.removerFuncionario();
                        case 6 -> funcionariosDao.listarFuncionarios();
                        
                        case 7 -> instrutorDao.inserirInstrutor();
                        case 8 -> instrutorDao.removerInstrutor();
                        case 9 -> instrutorDao.listarInstrutores();
                        
                        case 10 -> turmaDao.inserirTurma();
                        case 11 -> turmaDao.removerTurma();
                        case 12 -> turmaDao.listarTurmas();
                        
                        case 13 -> participacaoDao.inserirParticipacao();
                        case 14 -> participacaoDao.removerParticipacao();
                        case 15 -> participacaoDao.listarParticipacoes();
                        
                        case 16 -> clientesDao.listarClientesComTurmas();
                        case 17 -> turmaDao.TurmasAcimaDaMedia();
                        
                        case 99 -> {
                            continuar = false;
                            System.out.println("\n👋 Encerrando o sistema.");
                        }
                        default -> System.out.println("❌Opção inválida. Digite um número do menu.");
                    }

                } catch (InputMismatchException e) {
                    System.out.println("❌Erro: Entrada inválida. Por favor, digite um número.");
                    input.nextLine(); 
                }
            }
        }
    }
}