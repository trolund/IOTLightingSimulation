package interfaces.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import interfaces.rest.RootApplication;

import java.util.logging.Level;
import java.util.logging.Logger;

public class RabbitMQAdapter {

    private static final String EXCHANGE_NAME = "topic_logs";

    private final static Logger LOGGER = Logger.getLogger(RootApplication.class.getName());


    public static void initConnection() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, "topic");
        String queueName = channel.queueDeclare().getQueue();

        channel.queueBind(queueName, EXCHANGE_NAME, "#");

        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
        LOGGER.log(Level.INFO, " [*] Waiting for messages. To exit press CTRL+C");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            String log = " [x] Received '" +
                    delivery.getEnvelope().getRoutingKey() + "':'" + message + "'";
            System.out.println(log);
            LOGGER.log(Level.INFO, log);
        };

        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {
        });
    }

}