package junit;

import com.google.gson.Gson;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import dto.TransactionDTO;
import infrastructure.bank.Transaction;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.MapperService;

import javax.inject.Inject;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
public class PaymentTests {

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

    private static final String EXCHANGE_NAME = "payment-service";

    @Test
    public void testRabbitMQ() {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {

            channel.exchangeDeclare(EXCHANGE_NAME, "topic");

            String routingKey = "payment.payment";

            TransactionDTO dto = new TransactionDTO(BigDecimal.TEN, "creditorXXX", "debtorXXX");
            String message = new Gson().toJson(dto);

            channel.basicPublish(EXCHANGE_NAME, routingKey, null, message.getBytes(StandardCharsets.UTF_8));
            System.out.println("RabbitMQ: Sent '" + routingKey + "':'" + message + "'");

        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
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
