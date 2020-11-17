package repositories;

import models.Endereco;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class EnderecoRepository extends DAO<Endereco> {

    public EnderecoRepository(EntityManager manager) {
        super("Endereco", manager);
    }
    public Optional<Endereco> findEndereco(String rua, String num, String bairro, String cep){


        Optional<Endereco> endereco = Optional.of((Endereco) getManager().createQuery("from Endereco where rua=:rua and numero =:num and bairro=:bairro and cep=:cep")
                .setParameter("rua",rua)
                .setParameter("num",num)
                .setParameter("bairro",bairro)
                .setParameter("cep",cep)
                .getSingleResult());
        System.out.println(endereco.isPresent());
        return endereco;
    }
    public List<Endereco> findEnderecoByBairro(String bairro){
        return getManager().createQuery("from Endereco where bairro=:bairro").setParameter("bairro",bairro).getResultList();
    }
}