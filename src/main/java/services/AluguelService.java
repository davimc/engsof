package services;

import models.Aluguel;
import models.Locacao;
import repositories.AluguelRepository;

import javax.management.InstanceAlreadyExistsException;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.time.LocalDate;
import java.util.DuplicateFormatFlagsException;
import java.util.List;

public class AluguelService {
    private AluguelRepository repository;

    public AluguelService(EntityManager manager) {
        repository = new AluguelRepository(manager);
    }
    public List<Aluguel> encontraAlugueis(Locacao locacao){
        return repository.findByLocacao(locacao);
    }
    public Aluguel encontraAluguel(Locacao locacao, LocalDate dataVencimento)throws NoResultException {
        return repository.findByDataVencimento(locacao,dataVencimento);
    }
    public int calculaDiasAtraso(Locacao locacao, LocalDate dataVencimento, LocalDate dataPagamento){
        Aluguel aluguel = repository.findByDataVencimento(locacao,dataVencimento);
        return dataPagamento.compareTo(dataVencimento);
    }

}

/*public Aluguel cadastraAluguel(Locacao locacao, LocalDate dataValidade, String obs){
        Aluguel aluguel = new Aluguel(locacao, dataValidade,obs);
        locacao.getAlugueis().forEach(aluguelCadastrado-> {
            if(aluguelCadastrado.getDataVencimento().equals(dataValidade))
                throw new IllegalArgumentException("Aluguel já existe");
        });

        locacao = locacaoService.novoAluguel(locacao,aluguel);
        aluguel.setLocacao(locacao);



        aluguel = repository.findByDataVencimento(locacao,dataValidade);

        return aluguel;
    }
    public void pagaAluguel(){

    }*//*

}
*/
