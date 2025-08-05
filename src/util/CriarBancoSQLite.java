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
                + "id INTEGER PRIMARY KEY,"
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
                + "id INTEGER PRIMARY KEY,"
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
}
