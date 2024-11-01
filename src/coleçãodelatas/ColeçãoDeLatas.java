/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package coleçãodelatas;

/**
 *
 * @author m150886
 */
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.EOFException;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ColeçãoDeLatas {
    static ArrayList<Lata> colecaoLatas = new ArrayList<>();

    public static void main(String[] args) {
        Connection conexao = conectar();
        if (conexao != null) {
            // Feche a conexão após verificar que foi estabelecida
            try {
                conexao.close();
                System.out.println("Conexão fechada.");
            } catch (SQLException e) {
                System.out.println("Erro ao fechar a conexão: " + e.getMessage());
            }
        }
        Scanner scanner = new Scanner(System.in);
        int opcao = 0;
        carregarLatas();
        
        
        while(opcao != 5) {
            System.out.println("\n Menu de coleção de Latas");
            System.out.println("1. Adicionar Lata");
            System.out.println("2. Exibir Latas");
            System.out.println("3. Remover Lata");
            System.out.println("4. Liberar Espaço");
            System.out.println("5. Sair");
            
            opcao = scanner.nextInt();
            scanner.nextLine();
            
            switch(opcao){
                case 1:
                    System.out.println("Você escolheu: Adicionar Lata");
                    adicionarLata(scanner); // Chama o método para adicionar uma lata
                    salvarLata();
                    break;
                      case 2:
                    System.out.println("Você escolheu: Exibir Latas");
                    exibirLatas();
                    break;
                case 3:
                    System.out.println("Você escolheu: Remover Lata");
                    removerLata(scanner);
                    salvarLata();
                    break;
                case 4:
                    System.out.println("Você escolheu: Liberar Espaço");
                    manutencaoDeEspaço(scanner);
                    break;
                case 5:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
                    
            }
        }
        
        
        scanner.close();
       


    }
    public static void adicionarLata(Scanner scanner) {
        System.out.print("Digite o tipo da lata: ");
        String tipo = scanner.nextLine();

        boolean prioritaria = false;
        boolean entrada_valida_prioritaria = false;
        while (!entrada_valida_prioritaria){
            System.out.println("A Lata é prioritária? (s/n): ");
            String respostaPrioritaria = scanner.nextLine().trim().toLowerCase();
            if (respostaPrioritaria.equals("s")){
                prioritaria = true;
                entrada_valida_prioritaria = true;
            } else if (respostaPrioritaria.equals("n")){
                prioritaria = false;
                entrada_valida_prioritaria = true;
            } else{
                System.out.println("Resposta Inválida! Por Favor, insira 's' ou 'n'. ");
            }
        }

        boolean lataEdiçãoLimitada = false;
        boolean entrada_valida_edicao = false;
        while(!entrada_valida_edicao){
            System.out.println("A Lata é de Edição Limitada? (s/n): ");
            String resposta_sim_nao_edicao = scanner.nextLine().trim().toLowerCase();
            if (resposta_sim_nao_edicao.equals("s")){
                lataEdiçãoLimitada = true;
                entrada_valida_edicao = true;
        }else if (resposta_sim_nao_edicao.equals("n")){
                lataEdiçãoLimitada = false;
                entrada_valida_edicao = true;
                }else{
                    System.out.println("Resposta Inválida! Por Favor, insira 's' ou 'n'. ");
            }
        }
        
        System.out.print("Digite o nome da Lata: ");
        String marca = scanner.nextLine();

        System.out.print("Digite a descrição do sabor: ");
        String sabor = scanner.nextLine();

        System.out.print("Digite o código da lata: ");
        int codigo = scanner.nextInt();
        
        scanner.nextLine();

        int rank = -1;
        boolean entradaValida = false;
        while (!entradaValida) {
            System.out.println("Digite o Rank da Lata (De 0 à 5): ");
                if(scanner.hasNextInt()){
                    rank = scanner.nextInt();
                    if (rank >= 0 && rank <= 5){
                        entradaValida = true;
                        
                    }else{
                        System.out.println("Rank inserido inválido! Por Favor, insira um valor entre 0 e 5");
                    }
                }else{
                    System.out.println("Rank inserido inválido! Por favor, insira um valor inteiro.");
                    scanner.next();
                }
   
            }
                    
        // Criar um objeto Lata
        Lata lata = new Lata(tipo, prioritaria, lataEdiçãoLimitada, marca, sabor, codigo, rank);
        colecaoLatas.add(lata);
        System.out.println("Lata adicionada com sucesso!");
        
        // Aqui você pode armazenar a lata em uma lista, se desejar
        lata.exibirInformacoes(); // Exibe as informações da lata adicionada
    }
          public static void carregarLatas(){
           try (FileInputStream fileIn = new FileInputStream("latas.dat");
                ObjectInputStream in = new ObjectInputStream(fileIn)){
               colecaoLatas = (ArrayList<Lata>)in.readObject();
               System.out.println("Latas Carregadas");
           }catch(FileNotFoundException e){
               System.out.println("Nenhuma Lata cadastrada");
           }catch(EOFException e) {
               System.out.println("Arquivo das latas está vazio");
           } catch (IOException | ClassNotFoundException e){
               e.printStackTrace();
           }
        }
        public static void exibirLatas(){
            if(colecaoLatas.isEmpty()){
                System.out.println("Nenhuma Lata Cadastrada");
            }else{
                System.out.println("Exibindo Todas as latas Cadastradas");
                for(Lata lata : colecaoLatas){
                    lata.exibirInformacoes();
                    System.out.println("___________________");
                }
            }
        }
        public static void salvarLata(){
            try (FileOutputStream fileOut = new FileOutputStream("latas.dat");
                    ObjectOutputStream out = new ObjectOutputStream(fileOut)){
                    out.writeObject(colecaoLatas);
                    System.out.println("Coleção Atualizada!");
                } catch (IOException e){
                    e.printStackTrace();
                    System.out.println("Lata não salva");
                }
        }
    public static void removerLata(Scanner scanner){
        if(colecaoLatas.isEmpty()){
            System.out.println("Não há latas para serem removidas");
        }else{
            System.out.println("Escolha o número da Lata que deseja remover");
            for(int i = 0; i < colecaoLatas.size(); i++){
                System.out.println(i + 1 + ". " + colecaoLatas.get(i).getMarca()+ "- " + colecaoLatas.get(i).getSabor());
            }
            int indice = scanner.nextInt()-1;
            scanner.nextLine();
                if (indice >= 0 && indice < colecaoLatas.size()){
                    colecaoLatas.remove(indice);
                    System.out.println("Lata removida com sucesso!");
                    try(FileOutputStream fileOut = new FileOutputStream("latas.dat");
                            ObjectOutputStream out = new ObjectOutputStream(fileOut)){
                        out.writeObject(colecaoLatas);
                    }catch (IOException e){
                        e.printStackTrace();
                        System.out.println("Erro ao gravar o arquivo");
                    }
                }else{
                    System.out.println("Número inválido");
                }
        }
    }
    static final int MAX_LIMITE_LATAS = 400;
    
    public static void manutencaoDeEspaço(Scanner scaneer){
        if (colecaoLatas.size() <= MAX_LIMITE_LATAS){
            System.out.println("Há espaço para adicionar novas Latas :)");
            return; 
        }
        ArrayList<Lata> latasNaoLimitadas = new ArrayList<>();
        for (Lata lata : colecaoLatas){
            if(!lata.lataEdiçãoLimitada){
                latasNaoLimitadas.add(lata);
            }
        }
        latasNaoLimitadas.sort((l1, l2)-> {
        int rankComparasion = Integer.compare(l1.rank, l2.rank);
        if (rankComparasion != 0){
            return rankComparasion;
        }
        return l1.nomeDaMarca.compareToIgnoreCase(l2.nomeDaMarca);
    });
        int numLatasParaRemover = Math.min(5, latasNaoLimitadas.size());
        if(numLatasParaRemover == 0){
            System.out.println("Nenhuma lata possível para remoção");
            return;
        }
        System.out.println("As latas a seguir serão removidas: ");
        for(int i = 0; i < numLatasParaRemover; i++){
            Lata lata = latasNaoLimitadas.get(i);
            lata.exibirInformacoes();
        }
        for (int i = 0; i < numLatasParaRemover; i++ ){
            Lata lata = latasNaoLimitadas.get(i);
            colecaoLatas.remove(lata);
        }
        try (FileOutputStream fileOut = new FileOutputStream("latas.dat");
             ObjectOutputStream out = new ObjectOutputStream(fileOut)){
            out.writeObject(colecaoLatas);
            System.out.println("Espaço liberado com sucesso.");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erro ao atualizar o arquivo após a remoção.");
        }
    }
    public static Connection conectar(){
        String url = "jdbc:mysql://localhost:3306/Coleão de Latas";
        String user = "root";
        String password = "40028922";
        
        try {
            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println("Conexão com o Banco de Dados bem sucedida");
            return conn;
        }catch(SQLException e) {
            System.out.println("Erro ao tentar conectar com o banco de dados:" + e.getMessage());
            e.printStackTrace();
            return null;
            
        }
    };
       
    }
