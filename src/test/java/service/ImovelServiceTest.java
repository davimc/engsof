package service;

import builder.ImovelBuilder;
import models.Imovel;
import org.junit.jupiter.api.*;
import services.ImovelService;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class ImovelServiceTest {

    private services.ImovelService service;
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

        service = new ImovelService(manager);
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

            Imovel imovel = service.criaImovel(ImovelBuilder.umImovel().constroi());

            Assertions.assertEquals(imovel.getEndereco().getCep(),ImovelBuilder.umImovel().constroi().getEndereco().getCep());
            Assertions.assertEquals(1,service.encontraImovelPorBairro("Centro").size());

    }
    @Test
    public void testaCriarDisImoveis(){
        Assertions.assertDoesNotThrow(()->{
            service.criaImovel(ImovelBuilder.umImovel().constroi());
            service.criaImovel(ImovelBuilder.umImovel().constroi());

            Assertions.assertEquals(1,service.encontraImovelPorBairro("Centro").size());
        });
    }
}
