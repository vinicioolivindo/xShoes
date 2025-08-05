package util;

import java.sql.SQLException;
import java.sql.Statement;
import util.conexoes.ConexaoSQLite;

public class CriarBancoSQLite {

    private final ConexaoSQLite conexaoSQLite;

    public CriarBancoSQLite(ConexaoSQLite pConexaoSQLite) {
        this.conexaoSQLite = pConexaoSQLite;
    }

    public void criarTabelaProduto() {
        String sql = "CREATE TABLE IF NOT EXISTS tbProduto ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "nome TEXT NOT NULL,"
                + "descricao TEXT NOT NULL,"
                + "preco DOUBLE NOT NULL,"
                + "estoque INTEGER NOT NULL"
                + ");";

        try {
            Statement stmt = this.conexaoSQLite.criarStatement();
            if (stmt != null) {
                stmt.execute(sql);
                System.out.println("Tabela 'tbProduto' criada com sucesso.");
            } else {
                System.err.println("Erro ao criar Statement para 'tbProduto'.");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao criar tabela 'tbProduto': " + e.getMessage());
        }
    }

    public void criarTabelaCliente() {
        String sql = "CREATE TABLE IF NOT EXISTS tbCliente ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "nome TEXT NOT NULL,"
                + "email TEXT NOT NULL,"
                + "endereco TEXT NOT NULL,"
                + "telefone TEXT NOT NULL"
                + ");";

        try {
            Statement stmt = this.conexaoSQLite.criarStatement();
            if (stmt != null) {
                stmt.execute(sql);
                System.out.println("Tabela 'tbCliente' criada com sucesso.");
            } else {
                System.err.println("Erro ao criar Statement para 'tbCliente'.");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao criar tabela 'tbCliente': " + e.getMessage());
        }
    }


public void criarTabelaPedido() {
    String sql = "CREATE TABLE IF NOT EXISTS tbPedido ("
            + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
            + "idCliente INTEGER NOT NULL, "
            + "dataPedido TEXT NOT NULL, "
            + "valorTotal DOUBLE NOT NULL, "
            + "FOREIGN KEY(idCliente) REFERENCES tbCliente(id)"
            + ");";

    boolean conectou = false;

    try {
        conectou = this.conexaoSQLite.conectar();
        
        Statement stmt = this.conexaoSQLite.criarStatement();
        
       stmt.execute(sql);
        
        System.out.println("Tabela pedido criada");
    } catch (SQLException e) {
        System.out.println("Erro ao criar tabela pedido: " + e.getMessage());
    } finally {
        if (conectou) {
            this.conexaoSQLite.desconectar();
        }
    }
}

public void criarTabelaPedidoProduto() {
    String sql = "CREATE TABLE IF NOT EXISTS tbPedidoProduto ("
            + "idPedido INTEGER NOT NULL AUTOINCREMENT, "
            + "idProduto INTEGER NOT NULL, "
            + "quantidade INTEGER NOT NULL, "
            + "precoUnitario DOUBLE NOT NULL, "
            + "PRIMARY KEY(idPedido, idProduto), "
            + "FOREIGN KEY(idPedido) REFERENCES tbPedido(id), "
            + "FOREIGN KEY(idProduto) REFERENCES tbProduto(id)"
            + ");";

    boolean conectou = false;

    try {
        conectou = this.conexaoSQLite.conectar();
        Statement stmt = this.conexaoSQLite.criarStatement();
        stmt.execute(sql);
        System.out.println("Tabela pedido_produto criada");
    } catch (SQLException e) {
        System.out.println("Erro ao criar tabela pedido_produto: " + e.getMessage());
    } finally {
        if (conectou) {
            this.conexaoSQLite.desconectar();
            }
        }
    }
}
