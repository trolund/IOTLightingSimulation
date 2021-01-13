package junit;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.TokenService;
import services.interfaces.ITokenService;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TokenTests {

    private ITokenService service;

    @BeforeEach
    public void setUp() {
        service = new TokenService();
        System.out.println("Setting up...");
    }

    @AfterEach
    public void tearDown() {
        // Put teardown code here if needed.
        System.out.println("Tearing down...");
    }

    private static final String EXCHANGE_NAME = "topic_logs";

    //@Test
    public void testRabbitMQ() {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {

            channel.exchangeDeclare(EXCHANGE_NAME, "topic");

            String routingKey = "t1.t2";
            String message = "do fucking payment motherfucker!";

            channel.basicPublish(EXCHANGE_NAME, routingKey, null, message.getBytes("UTF-8"));
            System.out.println(" [x] Sent '" + routingKey + "':'" + message + "'");
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
            Assertions.fail();
        }
    }

}
