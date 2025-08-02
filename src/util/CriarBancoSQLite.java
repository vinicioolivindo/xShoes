
package util;

import conexoes.ConexaoSQLite;
import java.sql.SQLException;
import java.sql.Statement;

public class CriarBancoSQLite {
    
    private final ConexaoSQLite conexaoSQLite;
    
    public CriarBancoSQLite(ConexaoSQLite pConexaoSQLite) {
        this.conexaoSQLite = pConexaoSQLite;
    }
    
    public void criarTabelaProduto(){
        String sql = "CREATE TABLE IF NOT EXISTS tbProduto"
                + "("
                + "id interger primary key,"
                +"nome text NOT NULL,"
                +"descricao text NOT NULL,"
                + "preco double NOT NULL,"
                + "estoque interger NOT NULL"
                +");";
        
        //executar o sql de criar tabelas
        
        boolean conectou = false;
        
        try{
            conectou = this.conexaoSQLite.conectar();
            
            Statement stmt = this.conexaoSQLite.criarStatement();
            
            stmt.execute(sql);
            
            System.out.println("Tabela produto criada");
            
        }catch(SQLException e){
            //mensagem de erro na criação da tabela
        } finally {
            if(conectou) {
                this.conexaoSQLite.desconectar();
             }
        }
    }

    
    public void criarTabelaCliente(){
        String sql = "CREATE TABLE IF NOT EXISTS tbCliente"
                + "("
                + "id interger primary key,"
                +"nome text NOT NULL,"
                +"email text NOT NULL,"
                + "endereco text NOT NULL,"
                + "telefone text NOT NULL"
                +");";
        
        //executar o sql de criar tabelas
        
        boolean conectou = false;
        
        try{
            conectou = this.conexaoSQLite.conectar();
            
            Statement stmt = this.conexaoSQLite.criarStatement();
            
            stmt.execute(sql);
            
            System.out.println("Tabela cliente criada");
            
        }catch(SQLException e){
            //mensagem de erro na criação da tabela
        } finally {
            if(conectou) {
                this.conexaoSQLite.desconectar();
             }
        }
    }
}
