package service;

import dao.PedidoDAO;
import model.Pedido;
import model.Produto;

import java.time.LocalDate;
import java.util.List;

public class PedidoService {
private PedidoDAO pedidoDAO;
public PedidoService() {
    this.pedidoDAO = new PedidoDAO();
}

public void criarPedido(int idCliente, List<Produto> produtos) {
    double total = calcularTotal(produtos);
    Pedido pedido = new Pedido(idCliente, LocalDate.now(), total, produtos);
    pedidoDAO.inserir(pedido);
}

private double calcularTotal(List<Produto> produtos) {
    double total = 0;
    for (Produto p : produtos) {
        total += p.getPreco();
    }
    return total;
}
}