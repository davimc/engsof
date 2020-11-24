package service;

import builder.ClienteBuilder;
import models.Aluguel;
import models.Cliente;
import models.Locacao;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import services.AluguelService;
import services.ClienteService;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDate;

public class AluguelServiceTest {
    private AluguelService service;
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

        service = new AluguelService(manager);
    }
    @AfterEach
    public void depois(){
        manager.getTransaction().rollback();
    }
    @AfterAll
    public static  void fim(){
        emf.close();
    }


}