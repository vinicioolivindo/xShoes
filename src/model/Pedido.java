package model;

import java.time.LocalDate;
import java.util.List;

public class Pedido {
private int id;
private int idCliente;
private LocalDate dataPedido;
private double valorTotal;
private List<Produto> produtos;

public Pedido(){}

public Pedido(int idCliente, LocalDate dataPedido, double valorTotal, List<Produto> produtos) {
    this.idCliente = idCliente;
    this.dataPedido = dataPedido;
    this.valorTotal = valorTotal;
    this.produtos = produtos;
}

public int getId() {
    return id;
}

public void setId(int id) {
    this.id = id;
}

public int getIdCliente() {
    return idCliente;
}

public void setIdCliente(int idCliente) {
    this.idCliente = idCliente;
}

public LocalDate getDataPedido() {
    return dataPedido;
}

public void setDataPedido(LocalDate dataPedido) {
    this.dataPedido = dataPedido;
}

public double getValorTotal() {
    return valorTotal;
}

public void setValorTotal(double valorTotal) {
    this.valorTotal = valorTotal;
}

public List<Produto> getProdutos() {
    return produtos;
}

public void setProdutos(List<Produto> produtos) {
    this.produtos = produtos;
}
}
