package view;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import dao.ClienteDAO;
import dao.PedidoDAO;
import dao.ProdutoDAO;
import model.Cliente;
import model.Pedido;
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
        criador.criarTabelaPedido();
        criador.criarTabelaPedidoProduto();

        // 3. Instanciar DAOs com conexão
        ClienteDAO clienteDAO = new ClienteDAO(conexaoSQLite.getConexao());
        ProdutoDAO produtoDAO = new ProdutoDAO(conexaoSQLite.getConexao());
        PedidoDAO  pedidoDAO  = new PedidoDAO(conexaoSQLite.getConexao());

        // 4. Injetar os DAOs nos Services
        ClienteService clienteService = new ClienteService(clienteDAO);
        ProdutoService produtoService = new ProdutoService(produtoDAO);
        PedidoService pedidoService = new PedidoService(pedidoDAO); 

        int opcao;
        do {
            System.out.println(
                "\n1. Cadastrar produto\n2. Listar produtos\n3. Cadastrar cliente\n4. Listar clientes\n5. Atualizar cliente\n6. Remover cliente\n7. Atualizar Produto\n8. Remover Produto\n9. Busca por nome\n10. Busca por preço\n11. Criar Pedido\n12. Listar Pedidos\n0. Sair");
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

                case 7:
                    System.out.println("ID do produto a atualizar:");
                    int idProdUpdate = sc.nextInt();
                    sc.nextLine(); // limpar buffer
                    Produto prodExistente = produtoService.buscarProduto(idProdUpdate);
                    if (prodExistente == null) {
                        System.out.println("Produto não encontrado!");
                    } else {
                        System.out.println("Novo nome:");
                        prodExistente.setNome(sc.nextLine());

                        System.out.println("Nova descrição:");
                        prodExistente.setDescricao(sc.nextLine());

                        System.out.println("Novo preço:");
                        String novoPrecoStr = sc.nextLine().replace(",", ".");
                        try {
                            prodExistente.setPreco(Double.parseDouble(novoPrecoStr));
                        } catch (NumberFormatException e) {
                            System.out.println("Preço inválido.");
                            break;
                        }

                        System.out.println("Novo estoque:");
                        try {
                            prodExistente.setEstoque(sc.nextInt());
                            sc.nextLine();
                        } catch (Exception e) {
                            System.out.println("Estoque inválido.");
                            sc.nextLine(); // limpar buffer
                            break;
                        }

                        produtoService.atualizarProduto(prodExistente);
                        System.out.println("Produto atualizado!");
                    }
                    break;

                case 8:
                    System.out.println("ID do produto a remover:");
                    int idProdDelete = sc.nextInt();
                    sc.nextLine();
                    produtoService.removerProduto(idProdDelete);
                    System.out.println("Produto removido.");
                    break;

                case 9:
                    System.out.print("Digite o nome do produto para buscar: ");
                    String nomeBusca = sc.nextLine();
                    List<Produto> encontradosPorNome = produtoService.buscarPorNome(nomeBusca);
                    if (encontradosPorNome.isEmpty()) {
                        System.out.println("Nenhum produto encontrado com esse nome.");
                    } else {
                        encontradosPorNome.forEach(System.out::println);
                    }
                    break;

                case 10:
                    try {
                        System.out.print("Digite o preço mínimo: ");
                        double precoMin = Double.parseDouble(sc.nextLine().replace(",", "."));

                        System.out.print("Digite o preço máximo: ");
                        double precoMax = Double.parseDouble(sc.nextLine().replace(",", "."));

                        List<Produto> encontradosPorPreco = produtoService.buscarPorFaixaDePreco(precoMin, precoMax);
                        if (encontradosPorPreco.isEmpty()) {
                            System.out.println("Nenhum produto encontrado na faixa de preço.");
                        } else {
                            encontradosPorPreco.forEach(System.out::println);
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Preço inválido. Tente novamente.");
                    }
                    break;

                case 11:
                    System.out.print("ID do cliente: ");
                    int idClientePedido = sc.nextInt();
                    sc.nextLine();

                    Cliente clientePedido = clienteService.buscarCliente(idClientePedido);
                    if (clientePedido == null) {
                        System.out.println("Cliente não encontrado!");
                        break;
                    }

                    List<Produto> produtosPedido = new ArrayList<>();
                    while (true) {
                        System.out.print("Digite o ID do produto para adicionar (ou 0 para finalizar): ");
                        int idProduto = sc.nextInt();
                        sc.nextLine();

                        if (idProduto == 0) break;

                        Produto produto = produtoService.buscarProduto(idProduto);
                        if (produto == null) {
                            System.out.println("Produto não encontrado.");
                        } else {
                            produtosPedido.add(produto);
                            System.out.println("Produto adicionado.");
                        }
                    }

                    if (produtosPedido.isEmpty()) {
                        System.out.println("Nenhum produto adicionado. Pedido cancelado.");
                    } else {
                        pedidoService.criarPedido(idClientePedido, produtosPedido);
                        System.out.println("Pedido criado com sucesso!");
                    }
                    break;

                case 12:
                    List<Pedido> pedidos = pedidoService.listarPedidos();
                    if (pedidos.isEmpty()) {
                        System.out.println("Nenhum pedido encontrado.");
                    } else {
                        System.out.println("Pedidos Realizados:");
                        for (Pedido pedido : pedidos) {
                            System.out.println(pedido);
                        }
                    }
                    break;

                case 0:
                    System.out.println("Saindo...");
                    conexaoSQLite.desconectar(); // ← Fecha a conexão aqui!
                    sc.close(); // ← Fecha o scanner aqui!
                    break;

                default:
                    System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }
}