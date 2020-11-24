package service;

import builder.AluguelBuilder;
import builder.ClienteBuilder;
import builder.ImovelBuilder;
import builder.LocacaoBuilder;
import models.Aluguel;
import models.Cliente;
import models.Imovel;
import models.Locacao;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import repositories.EmailService;
import repositories.LocacaoRepository;
import services.LocacaoService;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class EmailServiceTest {
    private static EntityManagerFactory emf;
    private EntityManager manager;

    private LocacaoService service;
    private LocacaoRepository repository;
    private EmailService emailService;

   /* @BeforeAll
    public static void inicio(){
        emf = Persistence.createEntityManagerFactory("imobiliariaPU_test");
    }
    @BeforeEach
    public void antes(){

        manager = emf.createEntityManager();
        manager.getTransaction().begin();

        service = new LocacaoService(manager);
        emailService = Mockito.mock(EmailService.class);


    }
    @AfterEach
    public void depois(){
        manager.getTransaction().rollback();
    }
    @AfterAll
    public static  void fim(){
        emf.close();
    }
*/
    @Test
    public void testaEnviarEmailParaAtrasados(){
        repository = Mockito.mock(LocacaoRepository.class);
        service = new LocacaoService(Persistence.createEntityManagerFactory("imobiliariaPU_test").createEntityManager());
        Cliente cliente1 = ClienteBuilder.umCliente().comCpf("cpf1").constroi();
        Cliente cliente2 = ClienteBuilder.umCliente().comCpf("cpf2").constroi();
        Cliente cliente3 = ClienteBuilder.umCliente().comCpf("cpf3").constroi();

        Imovel imovel1 = ImovelBuilder.umImovel().comEndereco("Rua 1","2","Centro","65076-210").constroi();
        Imovel imovel2 = ImovelBuilder.umImovel().comEndereco("Rua 2","2","Centro","65076-210").constroi();
        Imovel imovel3 = ImovelBuilder.umImovel().comEndereco("Rua 3","2","Centro","65076-210").constroi();


        Locacao locacao1 = LocacaoBuilder.umaLocacao().comCliente(cliente1).comImovel(imovel1).constroi(),
        locacao2 = LocacaoBuilder.umaLocacao().comCliente(cliente2).comImovel(imovel2).constroi(),
        locacao3 = LocacaoBuilder.umaLocacao().comCliente(cliente3).comImovel(imovel3).constroi();

       locacao1.adicionaAluguel(new AluguelBuilder().constroi());
       //locacao2.adicionaAluguel(new AluguelBuilder().comUmMesAtraso().comLocacao(locacao2).constroi());
       //locacao3.adicionaAluguel(new AluguelBuilder().comUmMesAtraso().comLocacao(locacao3).constroi());

        //Mockito.when(repository.findAll()).thenReturn(Arrays.asList(locacao1,locacao2,locacao3));
        //Mockito.verify(emailService).notificaAtraso(ClienteBuilder.umCliente().comCpf("cpf1").constroi());
    }



}
