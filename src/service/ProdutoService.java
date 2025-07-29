package service;

import dao.ProdutoDAO;
import model.Produto;

import java.util.List;

public class ProdutoService {

    private ProdutoDAO produtoDAO;

    public ProdutoService() {
        this.produtoDAO = new ProdutoDAO();
    }

    public boolean inserirProduto(Produto produto) {
        if (validarProduto(produto)) {
            produtoDAO.inserir(produto);
            return true;
        }
        return false;
    }

    public List<Produto> listarProdutos() {
        return produtoDAO.listarTodos();
    }

    public boolean atualizarProduto(Produto produto) {
        if (produto.getId() <= 0) {
            System.out.println("ID inválido para atualização.");
            return false;
        }
        if (validarProduto(produto)) {
            produtoDAO.atualizar(produto);
            return true;
        }
        return false;
    }

    public boolean removerProduto(int id) {
        if (id <= 0) {
            System.out.println("ID inválido para remoção.");
            return false;
        }
        produtoDAO.remover(id);
        return true;
    }

    public List<Produto> buscarPorNome(String nome) {
        return produtoDAO.buscarPorNome(nome);
    }

    public List<Produto> buscarPorFaixaDePreco(double precoMin, double precoMax) {
        return produtoDAO.buscarPorFaixaDePreco(precoMin, precoMax);
    }

    // Regras de validação
    private boolean validarProduto(Produto produto) {
        if (produto.getNome() == null || produto.getNome().trim().isEmpty()) {
            System.out.println("Nome do produto é obrigatório.");
            return false;
        }
        if (produto.getPreco() < 0) {
            System.out.println("Preço não pode ser negativo.");
            return false;
        }
        if (produto.getEstoque() < 0) {
            System.out.println("Estoque não pode ser negativo.");
            return false;
        }
        return true;
    }
}
