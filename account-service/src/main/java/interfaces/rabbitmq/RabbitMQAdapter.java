package interfaces.rabbitmq;


import com.google.gson.Gson;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import domain.UserAccount;
import domain.BankAccount;
import interfaces.rest.RootApplication;
import interfaces.rest.AccountResource;
import services.interfaces.IAccountService;
import services.AccountService;

import javax.enterprise.context.ApplicationScoped;
import java.nio.charset.StandardCharsets;
import javax.inject.Inject;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@ApplicationScoped
public class RabbitMQAdapter {

    private static final String EXCHANGE_NAME = "message-hub";
    private final static Logger LOGGER = Logger.getLogger(RootApplication.class.getName());

    @Inject
    IAccountService service;

    private final static String routingKeyRead = "account.*";
    private final static String routingKeyWrite = "account.service";

    public void initConnection() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setPort(5672);
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, "topic");
        String accountQueue = channel.queueDeclare().getQueue();
        channel.queueBind(accountQueue, EXCHANGE_NAME, routingKeyRead);

        Gson gson = new Gson();

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            String[] splitMessage = message.split(" ");
            String messageType = splitMessage[0];

            LOGGER.log(Level.INFO, "RABBITMQ: Message type: " + messageType);
            LOGGER.log(Level.INFO, "RABBITMQ: Raw message: " + message);

            if (messageType.equals("getUser")) {
                String userId = splitMessage[1];
                try {
                    UserAccount userAccount = service.getById(userId);
                    String json = gson.toJson(userAccount);
                    channel.basicPublish(EXCHANGE_NAME, routingKeyWrite, null, json.getBytes(StandardCharsets.UTF_8));
                } catch (Exception e) {
                    channel.basicPublish(EXCHANGE_NAME, routingKeyWrite, null, e.getMessage().getBytes(StandardCharsets.UTF_8));
                }
            } else if (messageType.equals("getAllUsers")) {
                try {
                    List<UserAccount> users = service.getAll();
                    String json = gson.toJson(users);
                    channel.basicPublish(EXCHANGE_NAME, routingKeyWrite, null, json.getBytes(StandardCharsets.UTF_8));
                } catch (Exception e) {
                    channel.basicPublish(EXCHANGE_NAME, routingKeyWrite, null, e.getMessage().getBytes(StandardCharsets.UTF_8));
                }
            } else if (messageType.equals("getUserByCpr")) {
                String userCprNumber = splitMessage[1];
                try {
                    UserAccount userAccount = service.getByCpr(userCprNumber);
                    String json = gson.toJson(userAccount);
                    channel.basicPublish(EXCHANGE_NAME, routingKeyWrite, null, json.getBytes(StandardCharsets.UTF_8));
                } catch (Exception e) {
                    channel.basicPublish(EXCHANGE_NAME, routingKeyWrite, null, e.getMessage().getBytes(StandardCharsets.UTF_8));
                }
            } else if (messageType.equals("registerUser")) {
                String payload = splitMessage[1];
                UserAccount userAccount = gson.fromJson(payload, UserAccount.class);
                try {
                    service.add(userAccount);
                    channel.basicPublish(EXCHANGE_NAME, routingKeyWrite, null, "RegisterUserSuccessful".getBytes(StandardCharsets.UTF_8));
                } catch (Exception e) {
                    channel.basicPublish(EXCHANGE_NAME, routingKeyWrite, null, e.getMessage().getBytes(StandardCharsets.UTF_8));
                }
            } else {
                LOGGER.log(Level.INFO, "RABBITMQ: Message was ignored.");
            }
        };

        channel.basicConsume(accountQueue, true, deliverCallback, consumerTag -> {
        });
    }
}
