package service;

import dao.PedidoDAO;
import model.Pedido;
import model.Produto;

import java.time.LocalDate;
import java.util.List;

public class PedidoService {
    private PedidoDAO pedidoDAO;

    public PedidoService(PedidoDAO PedidoDAO2) {
        this.pedidoDAO = PedidoDAO2;
    }

    public void criarPedido(int idCliente, List<Produto> produtos) {
        double valorTotal = calcularValorTotal(produtos);
        Pedido pedido = new Pedido(idCliente, LocalDate.now(), valorTotal, produtos);
        pedidoDAO.inserir(pedido);
    }

    public List<Pedido> listarPedidos() {
        return pedidoDAO.listarTodos();
    }

    private double calcularValorTotal(List<Produto> produtos) {
        double total = 0.0;
        for (Produto produto : produtos) {
            total += produto.getPreco();
        }
        return total;
    }
}
