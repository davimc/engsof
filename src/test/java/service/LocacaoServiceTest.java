package service;

import builder.ClienteBuilder;
import builder.ImovelBuilder;
import builder.LocacaoBuilder;
import models.Aluguel;
import models.Imovel;
import models.Locacao;
import org.junit.jupiter.api.*;
import services.ImovelService;
import services.LocacaoService;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDate;

public class LocacaoServiceTest {
    private LocacaoService service;
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

        service = new LocacaoService(manager);
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
    public void testaCriarLocacao(){
        Locacao locacao = service.alocaImovel(LocacaoBuilder.umaLocacao().constroi());

        Assertions.assertEquals(1L, locacao.getId());
    }
    @Test
    public void testaCadastrarAluguel(){
        Locacao locacao = service.alocaImovel(LocacaoBuilder.umaLocacao().constroi());
        Aluguel aluguel = service.novoAluguel(locacao, LocalDate.now(),"");
        Assertions.assertEquals(1L,aluguel.getId());
    }

    //Questões 3 e 4
    @Test
    public void testaPagarAluguelEmDia(){
        Locacao locacao = service.alocaImovel(LocacaoBuilder.umaLocacao().constroi());
        Aluguel aluguel = service.novoAluguel(locacao, LocalDate.now(), "");

        Assertions.assertDoesNotThrow(()->{
            service.pagaAluguel(locacao,aluguel.getDataVencimento(),LocalDate.now(),800);
        });
    }
    @Test
    public void testaPagarAluguelEmDiaComValorMenor(){
        Locacao locacao = service.alocaImovel(LocacaoBuilder.umaLocacao().constroi());
        Aluguel aluguel = service.novoAluguel(locacao, LocalDate.now(),"");

        Assertions.assertThrows(IllegalStateException.class,()->{
            service.pagaAluguel(locacao,aluguel.getDataVencimento(),LocalDate.now(),799);
        });
    }
    @Test
    public void testaPagarAluguelEmAtraso(){
        Locacao locacao = service.alocaImovel(LocacaoBuilder.umaLocacao().constroi());
        Aluguel aluguel = service.novoAluguel(locacao, LocalDate.now().minusMonths(1),"");

        Assertions.assertDoesNotThrow(()->{
            service.pagaAluguel(locacao,aluguel.getDataVencimento(),LocalDate.now(),service.calculaAluguel(locacao,aluguel.getDataVencimento(),LocalDate.now()));

        });
    }
    @Test
    public void testaListaDeAlugueisPagosEmAtraso(){
        Locacao locacao = service.alocaImovel(LocacaoBuilder.umaLocacao().constroi());
        Aluguel aluguelAtrasado1 = service.novoAluguel(locacao, LocalDate.now().minusMonths(1),"");
        Aluguel aluguelAtrasado2 = service.novoAluguel(locacao, LocalDate.now().minusMonths(2),"");
        Aluguel aluguel = service.novoAluguel(locacao, LocalDate.now(),"");

        service.pagaAluguel(locacao,aluguelAtrasado1.getDataVencimento(),LocalDate.now(),service.calculaAluguel(locacao,aluguelAtrasado1.getDataVencimento(),LocalDate.now()));
        service.pagaAluguel(locacao,aluguelAtrasado2.getDataVencimento(),LocalDate.now(),service.calculaAluguel(locacao,aluguelAtrasado2.getDataVencimento(),LocalDate.now()));
        service.pagaAluguel(locacao,aluguel.getDataVencimento(),LocalDate.now(),service.calculaAluguel(locacao,aluguel.getDataVencimento(),LocalDate.now()));

        Assertions.assertEquals(2,service.listaAlugueisPagosEmAtraso(locacao).size());
    }
}
