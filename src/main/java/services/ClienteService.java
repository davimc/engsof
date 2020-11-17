package services;

import models.Cliente;
import repositories.ClienteRepository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

public class ClienteService {
    private ClienteRepository repository;

    public ClienteService(EntityManager manager){
        repository = new ClienteRepository(manager);
    }
    public Cliente cadastraCliente(Cliente cliente){
        Cliente clienteCadastrado = null;
        try {
            cliente = repository.findByCpf(cliente.getCpf()).get();
        }catch (NoResultException e) {
            repository.save(cliente);
        }
        return cliente;
    }
}
