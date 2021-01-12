package junit;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import services.interfaces.IMerchantService;

import javax.inject.Inject;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

@QuarkusTest
public class MerchantTests {

    private static final String EXCHANGE_NAME = "payment-service";

    @Inject
    IMerchantService service;

    @Test
    public void testRabbitMQ() {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {

            channel.exchangeDeclare(EXCHANGE_NAME, "topic");

            String routingKey = "payment.payment";

            //String message = new Gson().toJson(dto);

           // channel.basicPublish(EXCHANGE_NAME, routingKey, null, message.getBytes(StandardCharsets.UTF_8));
           // System.out.println("RabbitMQ: Sent '" + routingKey + "':'" + message + "'");

        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testCreateTransactionQueue() {
        service.createTransaction("customerId", "merchantId", 234234);
    }


}
