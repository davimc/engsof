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
        try {
            cliente = repository.findByCpf(cliente.getCpf()).get();
        }catch (NoResultException e) {
            repository.save(cliente);
        }
        cliente = repository.findByCpf(cliente.getCpf()).get();
        return cliente;
    }
}
