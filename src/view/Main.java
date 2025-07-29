package view;

import java.util.List;
import java.util.Scanner;

import model.Cliente;
import service.ClienteService;
import service.PedidoService;
import service.ProdutoService;

// Main.java
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ProdutoService produtoService = new ProdutoService();
        ClienteService clienteService = new ClienteService();
        PedidoService pedidoService = new PedidoService();

        int opcao;
        do {
            System.out.println(
                    "\n1. Cadastrar produto\n2. Listar produtos\n3. Cadastrar cliente\n4. Fazer pedido\n0. Sair");
            opcao = sc.nextInt();
            switch (opcao) {
                case 1:
                    // solicitar dados e chamar produtoService
                    break;
                case 2:
                    // produtoService.listar()
                    break;
                case 3:
                    System.out.println("Nome:");
                    String nome = sc.next();
                    System.out.println("Email:");
                    String email = sc.next();
                    System.out.println("Endereço:");
                    String endereco = sc.next();
                    System.out.println("Telefone:");
                    String telefone = sc.next();

                    Cliente novo = new Cliente(nome, email, endereco, telefone);
                    clienteService.cadastrarCliente(novo);
                    System.out.println("Cliente cadastrado!");
                    break;

                case 4:
                    List<Cliente> clientes = clienteService.listarClientes();
                    clientes.forEach(System.out::println);
                    break;

                case 5:
                    System.out.println("ID do cliente a atualizar:");
                    int idUpdate = sc.nextInt();
                    Cliente clienteExistente = clienteService.buscarCliente(idUpdate);
                    if (clienteExistente == null) {
                        System.out.println("Cliente não encontrado!");
                    } else {
                        System.out.println("Novo nome:");
                        clienteExistente.setNome(sc.next());
                        System.out.println("Novo email:");
                        clienteExistente.setEmail(sc.next());
                        System.out.println("Novo endereço:");
                        clienteExistente.setEndereco(sc.next());
                        System.out.println("Novo telefone:");
                        clienteExistente.setTelefone(sc.next());

                        clienteService.atualizarCliente(clienteExistente);
                        System.out.println("Cliente atualizado!");
                    }
                    break;

                case 6:
                    System.out.println("ID do cliente a remover:");
                    int idDelete = sc.nextInt();
                    clienteService.removerCliente(idDelete);
                    System.out.println("Cliente removido.");
                    break;

            }
        } while (opcao != 0);
    }
}
