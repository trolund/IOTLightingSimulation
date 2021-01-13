package interfaces.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import domain.Token;
import exceptions.CustomerAlreadyRegisteredException;
import exceptions.CustomerNotFoundException;
import exceptions.TooManyTokensException;
import interfaces.rest.RootApplication;
import interfaces.rest.TokenResource;
import services.TokenService;
import services.interfaces.ITokenService;

import javax.inject.Inject;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RabbitMQAdapter {

    static ITokenService service = new TokenService();

    private static final String EXCHANGE_NAME = "message-hub";

    private final static Logger LOGGER = Logger.getLogger(RootApplication.class.getName());

    private final static String routingKeyRead = "token.*";
    private final static String routingKeyWrite = "token.service";

    public static void initConnection() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("172.26.0.2");
        factory.setPort(5672);
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, "topic");
        String queueName = channel.queueDeclare().getQueue();

        channel.queueBind(queueName, EXCHANGE_NAME, routingKeyRead);

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            String[] splitMessage = message.split(" ");
            System.out.println("Raw message: " + message);
            if (splitMessage[0].equals("requestTokens")) {
                try {
                    service.requestTokens(splitMessage[1], Integer.parseInt(splitMessage[2]));
                    channel.basicPublish(EXCHANGE_NAME, routingKeyWrite, null, "TokenRequestSuccessful".getBytes("UTF-8"));
                } catch (Exception e) {
                    channel.basicPublish(EXCHANGE_NAME, routingKeyWrite, null, e.getMessage().getBytes("UTF-8"));
                }
            }
            if (splitMessage[0].equals("getToken")) {
                try {
                    Token token = service.getToken(splitMessage[1]);
                    channel.basicPublish(EXCHANGE_NAME, routingKeyWrite, null, token.getId().getBytes("UTF-8"));
                } catch (Exception e) {
                    channel.basicPublish(EXCHANGE_NAME, routingKeyWrite, null, e.getMessage().getBytes("UTF-8"));
                }
            }
            if (splitMessage[0].equals("validateToken")) {
                try {
                    service.invalidateToken(splitMessage[1]);
                    channel.basicPublish(EXCHANGE_NAME, routingKeyWrite, null, "TokenValidationSuccessful".getBytes("UTF-8"));
                } catch (Exception e) {
                    channel.basicPublish(EXCHANGE_NAME, routingKeyWrite, null, e.getMessage().getBytes("UTF-8"));
                }
            }
            if (splitMessage[0].equals("deleteCustomer")) {
                try {
                    service.deleteCustomer(splitMessage[1]);
                    channel.basicPublish(EXCHANGE_NAME, routingKeyWrite, null, "CustomerDeletionSuccessful".getBytes("UTF-8"));
                } catch (Exception e) {
                    channel.basicPublish(EXCHANGE_NAME, routingKeyWrite, null, e.getMessage().getBytes("UTF-8"));
                }
            }
        };

        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {
        });
    }

}