package repository;

import builder.AluguelBuilder;
import builder.LocacaoBuilder;
import models.Aluguel;
import models.Cliente;
import models.Endereco;
import org.junit.jupiter.api.*;
import repositories.AluguelRepository;
import repositories.ClienteRepository;
import repositories.EnderecoRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import java.time.LocalDate;

public class AluguelRepositoryTest {
    private AluguelRepository repository;
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

        repository = new AluguelRepository(manager);

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
    public void testaAluguel(){
        Aluguel aluguel = AluguelBuilder.umAluguel().constroi();
        repository.save(aluguel);
    }

}
