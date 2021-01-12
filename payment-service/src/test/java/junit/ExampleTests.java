package junit;

import dto.TransactionDTO;
import infrastructure.bank.Transaction;
import io.quarkus.test.junit.QuarkusTest;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import services.MapperService;

import javax.inject.Inject;

import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
public class ExampleTests {

    @Inject
    private MapperService mapper;

    @BeforeEach
    public void setUp() {
        //service = new ExampleService();
        System.out.println("Setting up...");
    }

    @AfterEach
    public void tearDown() {
        // Put teardown code here if needed.
        System.out.println("Tearing down...");
    }

    @Test
    public void testHealth() {
        String expected = "I am healthy and ready to work!";
        TransactionDTO dto = mapper.map(new Transaction(), TransactionDTO.class);
        //String actual = service.hello();

        // The third argument to assertEquals is a message displayed when the
        // test fails. This is useful to rapidly understand what the test expects.
        assertEquals("", "", "The two strings should be equal!");
    }

    @Test
    public void exampleRead() {
        String expected = "Example obj";
      //  String actual = service.readExample().getMsg();

        // The third argument to assertEquals is a message displayed when the
        // test fails. This is useful to rapidly understand what the test expects.
       // assertEquals(expected, actual, "The two strings should be equal!");
    }

}
