package dao;

import model.Pedido;
import model.Produto;
import java.sql.*;
import java.util.List;

public class PedidoDAO {
private Connection conn;
public PedidoDAO() {
    //this.conn = ConexaoDB.getConnection();
}

public void inserir(Pedido pedido) {
    String sqlPedido = "INSERT INTO Pedido (idCliente, dataPedido, valorTotal) VALUES (?, ?, ?)";
    try (PreparedStatement stmt = conn.prepareStatement(sqlPedido, Statement.RETURN_GENERATED_KEYS)) {
        stmt.setInt(1, pedido.getIdCliente());
        stmt.setString(2, pedido.getDataPedido().toString());
        stmt.setDouble(3, pedido.getValorTotal());
        stmt.executeUpdate();

        ResultSet rs = stmt.getGeneratedKeys();
        if (rs.next()) {
            int idPedido = rs.getInt(1);
            pedido.setId(idPedido);
            inserirProdutos(idPedido, pedido.getProdutos());
        }
    } catch (SQLException e) {
        throw new RuntimeException("Erro ao inserir pedido: " + e.getMessage());
    }
}

private void inserirProdutos(int idPedido, List<Produto> produtos) {
    String sql = "INSERT INTO Pedido_Produto (idPedido, idProduto) VALUES (?, ?)";
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        for (Produto p : produtos) {
            stmt.setInt(1, idPedido);
            stmt.setInt(2, p.getId());
            stmt.addBatch();
        }
        stmt.executeBatch();
    } catch (SQLException e) {
        throw new RuntimeException("Erro ao associar produtos ao pedido: " + e.getMessage());
    }
}
}