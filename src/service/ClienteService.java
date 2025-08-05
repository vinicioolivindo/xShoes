package service;

import dao.ClienteDAO;
import model.Cliente;

import java.util.List;

public class ClienteService {
    private ClienteDAO dao;

    public ClienteService(ClienteDAO dao) {
        this.dao = dao;
    }

    public void cadastrarCliente(Cliente cliente) {
        if (cliente.getNome() == null || cliente.getEmail() == null || cliente.getEndereco() == null) {
            throw new IllegalArgumentException("Todos os campos são obrigatórios.");
        }
        dao.inserir(cliente);
    }

    public List<Cliente> listarClientes() {
        return dao.listarTodos();
    }

    public void atualizarCliente(Cliente cliente) {
        dao.atualizar(cliente);
    }

    public void removerCliente(int id) {
        dao.remover(id);
    }

    public Cliente buscarCliente(int id) {
        return dao.buscarPorId(id);
    }
}
