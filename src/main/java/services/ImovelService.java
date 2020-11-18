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

            imovel = repository.findByEndereco(endereco.getId()).equals(imovel.getEndereco().getId())?imovel:repository.findByEndereco(endereco.getId());
            return imovel;
        }catch(NoResultException e){
            if(endereco ==null) {
                enderecoRepository.save(imovel.getEndereco());
                endereco = enderecoRepository.findEndereco(imovel.getEndereco().getRua(), imovel.getEndereco().getNumero(), imovel.getEndereco().getBairro(), imovel.getEndereco().getCep()).get();
            }
        }
            imovel.setEndereco(endereco);
            repository.save(imovel);
            imovel = repository.findByEndereco(endereco.getId());

            return imovel;
    }
    public Imovel locaImovel(Imovel imovel){
        if(imovel.isAtivo())
            throw new IllegalStateException("Imovel já está em uso");
        imovel.setAtivo(true);
        repository.save(imovel);
        return imovel;
    }
    public List<Imovel> encontraImoveisDisponivelPorBairro(String bairro){
        List<Imovel> imoveisBairro = new ArrayList<>();
        repository.findByAtivo(false).forEach(imovel -> {

            if(imovel.getEndereco().getBairro()=="Centro")
                imoveisBairro.add(imovel);
        });

        return imoveisBairro;
    }
    public List<Imovel> encontraImoveisDisponiveisPorPreco(double precoMax){
        List<Imovel> imoveisPreco = new ArrayList<>();
        repository.findByAtivo(false).forEach(imovel->{
            if(imovel.getAluguelSugerido()<=precoMax)
                imoveisPreco.add(imovel);
        });
        return imoveisPreco;
    }
}
