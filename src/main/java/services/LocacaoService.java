package services;

import models.Aluguel;
import models.Cliente;
import models.Imovel;
import models.Locacao;
import repositories.AluguelRepository;
import repositories.LocacaoRepository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LocacaoService {
    private LocacaoRepository locacaoRepository;
    private ClienteService clienteService;
    private ImovelService imovelService;
    private AluguelService aluguelService;

    public LocacaoService(EntityManager manager){
        locacaoRepository = new LocacaoRepository(manager);
        clienteService = new ClienteService(manager);
        imovelService = new ImovelService(manager);
        aluguelService = new AluguelService(manager);
    }

    public Locacao alocaImovel(Locacao locacao){

        Cliente cliente = clienteService.cadastraCliente(locacao.getCliente());

        Imovel imovel = imovelService.criaImovel(locacao.getImovel());

        Locacao locacaoEncontrada=null;
        try {
            locacaoEncontrada = locacaoRepository.findLocacao(cliente, imovel);
            if(locacaoEncontrada.isAtivo())
                throw new IllegalStateException("Há uma locação ainda ativa para este imovel");
            locacao = locacaoEncontrada;

        }catch(NoResultException e){

        }
        imovelService.locaImovel(imovel);

        locacao.setImovel(imovel);
        locacao.setCliente(cliente);
        locacaoRepository.save(locacao);
        locacao = locacaoRepository.findLocacao(cliente, imovel);

        return locacao;
    }
    public Aluguel novoAluguel(Locacao locacao,LocalDate dataVencimento, String obs){
        locacao.adicionaAluguel(new Aluguel(locacao,dataVencimento,obs));
        locacaoRepository.save(locacao);

        return aluguelService.encontraAluguel(locacao,dataVencimento);
    }
    public void pagaAluguel(Locacao locacao,LocalDate dataVencimento, LocalDate dataPagamento,double valor){
        double valorAluguel = calculaAluguel(locacao, dataVencimento,dataPagamento);
        if(valor<valorAluguel)
            throw new IllegalStateException("Não pode pagar com valor menor que o cobrado");
        locacao.getAlugueis().forEach(aluguel -> {
            if(aluguel.getDataVencimento().equals(dataVencimento)) {
                aluguel.setDataPagamento(dataPagamento);
                aluguel.setValorPago(valor);
            }
        });
        locacaoRepository.save(locacao);
    }
    public double calculaAluguel(Locacao locacao, LocalDate dataVencimento, LocalDate dataPagamento){
        int diasAtraso = aluguelService.calculaDiasAtraso(locacao,dataVencimento,dataPagamento);
        return diasAtraso>0?locacao.getValorAluguel()+((locacao.getValorAluguel()*locacao.getPorcentualMulta())*diasAtraso):locacao.getValorAluguel();
    }
    public List<Aluguel> listaAlugueisPagosEmAtraso(Locacao locacao){
        List<Aluguel> alugueisPagosAtrasado = new ArrayList<>();
        aluguelService.encontraAlugueis(locacao).forEach(aluguel->{
            if(aluguel.getDataVencimento().isBefore(LocalDate.now()))
                if(aluguel.getDataPagamento()!= null)
                    alugueisPagosAtrasado.add(aluguel);

        });
        return alugueisPagosAtrasado;
    }
}
