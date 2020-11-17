package services;

import models.Endereco;
import models.Imovel;
import repositories.EnderecoRepository;
import repositories.ImovelRepository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.List;

public class ImovelService {
    private ImovelRepository repository;
    private EnderecoRepository enderecoRepository;

    public ImovelService(EntityManager manager){
        repository = new ImovelRepository(manager);
        enderecoRepository = new EnderecoRepository(manager);
    }
    public Imovel criaImovel(Imovel imovel){
        Endereco endereco= null;
        try{
            endereco = enderecoRepository.findEndereco(imovel.getEndereco().getRua(),imovel.getEndereco().getNumero(),imovel.getEndereco().getBairro(),imovel.getEndereco().getCep()).get();
            System.out.println("foifoifoif");
            imovel = repository.findByEndereco(endereco.getId()).equals(imovel)?imovel:repository.findByEndereco(endereco.getId());
            System.out.println("foifoifoif");
        }catch(NoResultException e){
            if(endereco ==null) {
            System.out.println("okokok");
                enderecoRepository.save(imovel.getEndereco());
                endereco = enderecoRepository.findEndereco(imovel.getEndereco().getRua(), imovel.getEndereco().getNumero(), imovel.getEndereco().getBairro(), imovel.getEndereco().getCep()).get();
            }
        }
            imovel.setEndereco(endereco);
            repository.save(imovel);

            return imovel;
    }
    public List<Imovel> encontraImovelPorBairro(String bairro){
        List<Imovel> imoveisBairro = new ArrayList<>();
        enderecoRepository.findEnderecoByBairro("Centro").forEach(endereco -> {
            imoveisBairro.add(repository.findByEndereco(endereco.getId()));
        });

        return imoveisBairro;
    }
}
