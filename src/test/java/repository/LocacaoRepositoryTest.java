package repository;

import builder.ClienteBuilder;
import builder.ImovelBuilder;
import builder.LocacaoBuilder;
import models.Cliente;
import models.Imovel;
import models.Locacao;
import org.junit.jupiter.api.*;
import repositories.ClienteRepository;
import repositories.DAO;
import repositories.ImovelRepository;
import repositories.LocacaoRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class LocacaoRepositoryTest {
    private LocacaoRepository repository;
    private ClienteRepository clienteRepository;
    private ImovelRepository imovelRepository;
    private static EntityManagerFactory emf;
    private EntityManager manager;


    @BeforeAll
    public static void inicio(){
        emf = Persistence.createEntityManagerFactory("imobiliariaPU_test");
    }
    @BeforeEach
    public void antes(){
        manager = emf.createEntityManager();
        manager.getTransaction().begin();

        repository = new LocacaoRepository(manager);
        clienteRepository = new ClienteRepository(manager);
        imovelRepository = new ImovelRepository(manager);

    }
    @AfterEach
    public void depois(){
        manager.getTransaction().rollback();
    }
    @AfterAll
    public static  void fim(){
        emf.close();
    }

    @Test
    public void testaEncontrarLocacaoCadastrada(){
        imovelRepository.save(ImovelBuilder.umImovel().constroi());
        Imovel imovel = imovelRepository.findByEndereco(1L);
        clienteRepository.save(ClienteBuilder.umCliente().constroi());
        Cliente cliente = clienteRepository.findByCpf(ClienteBuilder.umCliente().constroi().getCpf()).get();
        repository.save(LocacaoBuilder.umaLocacao().comCliente(cliente).comImovel(imovel).constroi());
        repository.findLocacao(LocacaoBuilder.umaLocacao().comCliente(cliente).constroi().getCliente(), LocacaoBuilder.umaLocacao().comImovel(imovel).constroi().getImovel());
    }
}
