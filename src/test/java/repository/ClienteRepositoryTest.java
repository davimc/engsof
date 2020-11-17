package repository;

import builder.ClienteBuilder;
import org.junit.jupiter.api.*;
import repositories.ClienteRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class ClienteRepositoryTest {
    private ClienteRepository repository;
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

        repository = new ClienteRepository(manager);

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
    public void testaEncontrarUmCliente(){
        repository.save(ClienteBuilder.umCliente().constroi());
        Assertions.assertEquals(ClienteBuilder.umCliente().constroi(),repository.findByCpf(ClienteBuilder.umCliente().constroi().getCpf()).get());
    }
}
