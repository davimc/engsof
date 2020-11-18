package service;

import builder.ImovelBuilder;
import models.Imovel;
import org.junit.jupiter.api.*;
import services.ImovelService;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class ImovelServiceTest {

    private ImovelService service;
    private static EntityManagerFactory emf;
    private EntityManager manager;

    private Imovel imovel;
    @BeforeAll
    public static void inicio(){
        emf = Persistence.createEntityManagerFactory("imobiliariaPU_test");
    }
    @BeforeEach
    public void antes(){
        manager = emf.createEntityManager();
        manager.getTransaction().begin();

        service = new ImovelService(manager);
        imovel = service.criaImovel(ImovelBuilder.umImovel().constroi());
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
    public void testaCriarImovel(){

            Assertions.assertEquals(imovel.getEndereco().getCep(),ImovelBuilder.umImovel().constroi().getEndereco().getCep());
            Assertions.assertEquals(1,service.encontraImoveisDisponivelPorBairro("Centro").size());

    }
    @Test
    public void testaCriarDisImoveisOSegundoImovelNaoDeveSerDuplicado(){
        Assertions.assertDoesNotThrow(()->{
            Imovel imovel1=service.criaImovel(ImovelBuilder.umImovel().constroi());

            Assertions.assertEquals(1,service.encontraImoveisDisponivelPorBairro("Centro").size());
            Assertions.assertEquals(imovel,imovel1);
        });
    }
    @Test
    public void testaLocarUmImovel(){
        service.locaImovel(imovel);
        Assertions.assertTrue(imovel.isAtivo());
    }
    @Test
    public void testaLocarUmImovelAtivoDeveLancarExcecao(){
        service.locaImovel(imovel);
        Assertions.assertThrows(IllegalStateException.class,()->{
           service.locaImovel(imovel);
        });
    }
    //Questão um
    @Test
    public void testaEncontrarImoveisDisponiveisPorBairro(){
        service.criaImovel(ImovelBuilder.umImovel().comEndereco("Rua 2","3","Centro","65076210").constroi());
        service.criaImovel(ImovelBuilder.umImovel().comEndereco("Rua 3","5","São Francisco","65076210").constroi());

        Assertions.assertEquals(2, service.encontraImoveisDisponivelPorBairro("Centro").size());
    }
    @Test
    public void testaEncontrarImoveisDisponiveisPorPrecoMaximo(){
        service.criaImovel(ImovelBuilder.umImovel().comEndereco("Rua 3","5","São Francisco","65076210").comAluguelSugerido(1000).constroi());
        service.criaImovel(ImovelBuilder.umImovel().comEndereco("Rua 2","3","Centro","65076210").comAluguelSugerido(700).constroi());

        Assertions.assertEquals(2, service.encontraImoveisDisponiveisPorPreco(900).size());
    }
}
