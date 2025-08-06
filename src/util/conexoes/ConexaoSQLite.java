package util.conexoes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConexaoSQLite {
    private Connection conexao = null;

    // Conecta ao banco (e cria se não existir)
    public boolean conectar() {
        try {
            if (conexao == null || conexao.isClosed()) {
                String url = "jdbc:sqlite:banco_de_dados/banco_sqlite.db";
                this.conexao = DriverManager.getConnection(url);
                System.out.println("Conectou com sucesso!");
            }
            return true;
        } catch (SQLException e) {
            System.err.println("Erro ao conectar: " + e.getMessage());
            return false;
        }
    }

    // Desconecta apenas se estiver aberta
    public boolean desconectar() {
        try {
            if (this.conexao != null && !this.conexao.isClosed()) {
                this.conexao.close();
                System.out.println("Conexão encerrada");
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Erro ao desconectar: " + e.getMessage());
        }
        return false;
    }

    // Cria um Statement básico
    public Statement criarStatement() {
        try {
            return this.getConexao().createStatement();
        } catch (SQLException e) {
            System.err.println("Erro ao criar Statement: " + e.getMessage());
            return null;
        }
    }

    // Retorna a conexão, garantindo que está aberta
    public Connection getConexao() {
        try {
            if (this.conexao == null || this.conexao.isClosed()) {
                conectar(); // tenta reconectar automaticamente
            }
        } catch (SQLException e) {
            System.err.println("Erro ao verificar conexão: " + e.getMessage());
        }
        return this.conexao;
    }
}
