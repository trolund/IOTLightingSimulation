package interfaces.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import dto.TransactionDTO;
import interfaces.rest.RootApplication;
import io.cucumber.messages.internal.com.google.gson.Gson;
import services.interfaces.IMerchantService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

@ApplicationScoped
public class RabbitMQAdapter {

    private static final String EXCHANGE_NAME = "payment-service";
    private final static Logger LOGGER = Logger.getLogger(RootApplication.class.getName());

    @Inject
    IMerchantService service;

    public void initConnection() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, "topic");
        String queueNamePayments = channel.queueDeclare().getQueue();
        channel.queueBind(queueNamePayments, EXCHANGE_NAME, "payment.*");

        channel.exchangeDeclare(EXCHANGE_NAME, "topic");
        String queueNameTransactions = channel.queueDeclare().getQueue();
        channel.queueBind(queueNameTransactions, EXCHANGE_NAME, "transaction.*");

        DeliverCallback paymentsCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            String routingKey = delivery.getEnvelope().getRoutingKey();

            String log = "RabbitMQ: Payments: Received '" +
                    delivery.getEnvelope().getRoutingKey() + "':'" + message + "'";


            LOGGER.log(Level.INFO, log);
        };

        channel.basicConsume(queueNamePayments, true, paymentsCallback, consumerTag -> {
        });

        DeliverCallback transactionCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            String routingKey = delivery.getEnvelope().getRoutingKey();

            String log = "RabbitMQ: Transactions: Received '" +
                    delivery.getEnvelope().getRoutingKey() + "':'" + message + "'";

            LOGGER.log(Level.INFO, log);
        };

        channel.basicConsume(queueNameTransactions, true, transactionCallback, consumerTag -> {
        });
    }

    public void createTransactionToQueue(TransactionDTO transactionDTO) {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {

            System.out.println("PORT: " + channel.getConnection().getPort());
            LOGGER.log(Level.INFO, "PORT: " + channel.getConnection().getPort());

            channel.exchangeDeclare(EXCHANGE_NAME, "topic");

            String routingKey = "payment.payment";

            String message = new Gson().toJson(transactionDTO);

            channel.basicPublish(EXCHANGE_NAME, routingKey, null, message.getBytes(StandardCharsets.UTF_8));
            System.out.println("RabbitMQ: Sent '" + routingKey + "':'" + message + "'");

        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
    }

}