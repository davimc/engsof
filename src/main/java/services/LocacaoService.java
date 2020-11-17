package services;

import models.Cliente;
import models.Imovel;
import models.Locacao;
import repositories.LocacaoRepository;

import javax.persistence.EntityManager;

public class LocacaoService {
    private LocacaoRepository locacaoRepository;
    private ClienteService clienteService;
    private ImovelService imovelService;

    public LocacaoService(EntityManager manager){
        locacaoRepository = new LocacaoRepository(manager);
    }

    public Locacao alocaImovel(Cliente cliente, Imovel imovel){
        cliente = clienteService.cadastraCliente(cliente);
        imovel = imovelService.criaImovel(imovel);

        Locacao locacao = locacaoRepository.findLocacao(cliente,imovel);

        //locacaoRepository.save();
        return null;
    }
}
