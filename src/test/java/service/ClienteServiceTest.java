package service;

import builder.ClienteBuilder;
import models.Cliente;
import models.Endereco;
import org.junit.jupiter.api.*;
import repositories.EnderecoRepository;
import services.ClienteService;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class ClienteServiceTest {
    private ClienteService service;
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

        service = new ClienteService(manager);
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
    public void testaCadastrarClientePelaPrimeiraVez(){
        Assertions.assertDoesNotThrow(()->{
            service.cadastraCliente(ClienteBuilder.umCliente().constroi());
        });
    }
    @Test
    public void testaCadastrarClienteMaisDeUmVezDeveRetornarOmMesmo(){
        Cliente cliente1,cliente2;
        cliente1 = service.cadastraCliente(ClienteBuilder.umCliente().constroi());
        cliente2 = service.cadastraCliente(ClienteBuilder.umCliente().constroi());;
        Assertions.assertEquals(cliente1,cliente2);
    }
}