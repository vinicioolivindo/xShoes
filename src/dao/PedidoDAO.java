package dao;

import model.Pedido;
import model.Produto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PedidoDAO {

    private Connection conn;

    public PedidoDAO(Connection conn) {
        this.conn = conn;
    }

    // 1 a 4: Criar pedido, associar cliente, adicionar produtos, calcular total e registrar
    public void inserir(Pedido pedido) {
        String sqlPedido = "INSERT INTO tbPedido (idCliente, dataPedido, valorTotal) VALUES (?, ?, ?)";
        String sqlAssocProdutos = "INSERT INTO tbPedidoProduto (idPedido, idProduto, quantidade) VALUES (?, ?, ?)";

        try (PreparedStatement stmtPedido = conn.prepareStatement(sqlPedido, Statement.RETURN_GENERATED_KEYS)) {
            // Calcular valor total automaticamente
            double valorTotal = calcularTotal(pedido.getProdutos());
            pedido.setValorTotal(valorTotal);

            // Inserir pedido
            stmtPedido.setInt(1, pedido.getIdCliente());
            stmtPedido.setDate(2, Date.valueOf(pedido.getDataPedido()));
            stmtPedido.setDouble(3, pedido.getValorTotal());
            stmtPedido.executeUpdate();

            ResultSet rs = stmtPedido.getGeneratedKeys();
            if (rs.next()) {
                int idPedidoGerado = rs.getInt(1);
                pedido.setId(idPedidoGerado);

                // Associar produtos ao pedido, incluindo quantidade
                if (pedido.getProdutos() != null && !pedido.getProdutos().isEmpty()) {
                    try (PreparedStatement stmtAssoc = conn.prepareStatement(sqlAssocProdutos)) {
                        for (Produto p : pedido.getProdutos()) {
                            stmtAssoc.setInt(1, idPedidoGerado);
                            stmtAssoc.setInt(2, p.getId());
                            // Define quantidade; se não existir, assume 1
                            int quantidade = (p.getQuantidade() > 0) ? p.getQuantidade() : 1;
                            stmtAssoc.setInt(3, quantidade);
                            stmtAssoc.addBatch();
                        }
                        stmtAssoc.executeBatch();
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir pedido: " + e.getMessage());
        }
    }

    // 5: Listar todos os pedidos realizados (sem ou com produtos, como preferir)
    public List<Pedido> listarTodos() {
        List<Pedido> pedidos = new ArrayList<>();
        String sql = "SELECT * FROM tbPedido";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Pedido pedido = new Pedido();
                pedido.setId(rs.getInt("id"));
                pedido.setIdCliente(rs.getInt("idCliente"));
                pedido.setDataPedido(rs.getDate("dataPedido").toLocalDate());
                pedido.setValorTotal(rs.getDouble("valorTotal"));

                // Opcional: carregar produtos do pedido
                pedido.setProdutos(buscarProdutosPorPedidoId(pedido.getId()));

                pedidos.add(pedido);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar pedidos: " + e.getMessage());
        }

        return pedidos;
    }

    // Auxiliar: buscar produtos associados a um pedido
    private List<Produto> buscarProdutosPorPedidoId(int idPedido) {
        List<Produto> produtos = new ArrayList<>();
        String sql = "SELECT p.id, p.nome, p.descricao, p.preco, p.estoque FROM tbProduto p " +
                     "INNER JOIN tbPedidoProduto pp ON p.id = pp.idProduto " +
                     "WHERE pp.idPedido = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idPedido);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Produto p = new Produto();
                p.setId(rs.getInt("id"));
                p.setNome(rs.getString("nome"));
                p.setDescricao(rs.getString("descricao"));
                p.setPreco(rs.getDouble("preco"));
                p.setEstoque(rs.getInt("estoque"));
                produtos.add(p);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar produtos do pedido: " + e.getMessage());
        }

        return produtos;
    }

    // Auxiliar: calcula total de um pedido com base nos preços dos produtos
    private double calcularTotal(List<Produto> produtos) {
        double total = 0;
        if (produtos != null) {
            for (Produto p : produtos) {
                // Se existir quantidade, multiplica pelo preço
                int qtd = (p.getQuantidade() > 0) ? p.getQuantidade() : 1;
                total += p.getPreco() * qtd;
            }
        }
        return total;
    }
}