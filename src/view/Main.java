package view;

import java.util.List;
import java.util.Scanner;

import dao.ClienteDAO;
import dao.ProdutoDAO;
import model.Cliente;
import model.Produto;
import service.ClienteService;
import service.PedidoService;
import service.ProdutoService;
import util.CriarBancoSQLite;
import util.conexoes.ConexaoSQLite;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // 1. Conectar ao banco
        ConexaoSQLite conexaoSQLite = new ConexaoSQLite();
        boolean conectado = conexaoSQLite.conectar();

        if (!conectado) {
            System.out.println("Erro ao conectar no banco de dados.");
            return;
        }

        // 2. Criar tabelas
        CriarBancoSQLite criador = new CriarBancoSQLite(conexaoSQLite);
        criador.criarTabelaCliente();
        criador.criarTabelaProduto();

        // 3. Instanciar DAOs com conexão
        ClienteDAO clienteDAO = new ClienteDAO(conexaoSQLite.getConexao());
        ProdutoDAO produtoDAO = new ProdutoDAO(conexaoSQLite.getConexao());

        // 4. Injetar os DAOs nos Services
        ClienteService clienteService = new ClienteService(clienteDAO);
        ProdutoService produtoService = new ProdutoService(produtoDAO);
        PedidoService pedidoService = new PedidoService(); // ainda não implementado

        int opcao;
        do {
            System.out.println(
                    "\n1. Cadastrar produto\n2. Listar produtos\n3. Cadastrar cliente\n4. Listar clientes\n5. Atualizar cliente\n6. Remover cliente\n0. Sair");
            System.out.print("Opção: ");
            opcao = sc.nextInt();
            sc.nextLine(); // limpar buffer do nextInt

            switch (opcao) {
            case 1:
                System.out.println("Nome do produto:");
                String nomeProd = sc.nextLine();

                System.out.println("Descrição:");
                String descricao = sc.nextLine();

                System.out.println("Preço:");
                String precoStr = sc.nextLine().replace(",", ".");
                double preco;
                try {
                    preco = Double.parseDouble(precoStr);
                } catch (NumberFormatException e) {
                    System.out.println("Preço inválido.");
                    break;
                }

                System.out.println("Estoque:");
                int estoque = sc.nextInt();
                sc.nextLine(); // limpar buffer

                Produto novoProduto = new Produto(nomeProd, descricao, preco, estoque);
                if (produtoService.inserirProduto(novoProduto)) {
                    System.out.println("Produto cadastrado com sucesso!");
                } else {
                    System.out.println("Erro ao cadastrar produto.");
                }
                break;

                case 2:
                     List<Produto> produtos = produtoService.listarProdutos();
                     System.out.println("\nLista de Produtos:");
                     if (produtos.isEmpty()) {
                         System.out.println("Nenhum produto cadastrado.");
                     } else {
                         produtos.forEach(System.out::println);
                     }
                     break;

                case 3:
                    System.out.println("Nome:");
                    String nome = sc.nextLine();
                    System.out.println("Email:");
                    String email = sc.nextLine();
                    System.out.println("Endereço:");
                    String endereco = sc.nextLine();
                    System.out.println("Telefone:");
                    String telefone = sc.nextLine();

                    Cliente novo = new Cliente(nome, email, endereco, telefone);
                    clienteService.cadastrarCliente(novo);
                    System.out.println("Cliente cadastrado!");
                    break;

                case 4:
                    List<Cliente> clientes = clienteService.listarClientes();
                    if (clientes.isEmpty()) {
                        System.out.println("Nenhum cliente encontrado.");
                    } else {
                        clientes.forEach(System.out::println);
                    }
                    break;

                case 5:
                    System.out.println("ID do cliente a atualizar:");
                    int idUpdate = sc.nextInt();
                    sc.nextLine();
                    Cliente clienteExistente = clienteService.buscarCliente(idUpdate);
                    if (clienteExistente == null) {
                        System.out.println("Cliente não encontrado!");
                    } else {
                        System.out.println("Novo nome:");
                        clienteExistente.setNome(sc.nextLine());
                        System.out.println("Novo email:");
                        clienteExistente.setEmail(sc.nextLine());
                        System.out.println("Novo endereço:");
                        clienteExistente.setEndereco(sc.nextLine());
                        System.out.println("Novo telefone:");
                        clienteExistente.setTelefone(sc.nextLine());

                        clienteService.atualizarCliente(clienteExistente);
                        System.out.println("Cliente atualizado!");
                    }
                    break;

                case 6:
                    System.out.println("ID do cliente a remover:");
                    int idDelete = sc.nextInt();
                    sc.nextLine();
                    clienteService.removerCliente(idDelete);
                    System.out.println("Cliente removido.");
                    break;

                case 0:
                    System.out.println("Saindo...");
                    break;

                default:
                    System.out.println("Opção inválida.");
            }
        } while (opcao != 0);

        conexaoSQLite.desconectar();
        sc.close();
    }
}
