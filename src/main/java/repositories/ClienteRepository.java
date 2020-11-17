package repositories;

import models.Cliente;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class ClienteRepository extends DAO<Cliente> {

    public ClienteRepository(EntityManager manager) {
        super("Cliente",manager);
    }



    public List<Cliente> findByName(String nome) {
        return getManager().
                createQuery("from Cliente where nome=:nome").setParameter("nome", nome)
                .getResultList();
    }
    public Optional<Cliente> findByCpf(String cpf){
        return Optional.of((Cliente)getManager().createQuery("from Cliente where cpf=:cpf").setParameter("cpf",cpf).getSingleResult());
    }


}