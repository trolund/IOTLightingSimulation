package interfaces.rabbitmq;

import com.google.gson.Gson;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import dto.TransactionDTO;
import exceptions.AccountException;
import exceptions.TransactionException;
import exceptions.customer.CustomerException;
import exceptions.merchant.MerchantException;
import infrastructure.bank.Transaction;
import interfaces.rest.RootApplication;
import services.interfaces.IPaymentService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;

@ApplicationScoped
public class RabbitMQAdapter {

    private static final String EXCHANGE_NAME = "payment-service";
    private final static Logger LOGGER = Logger.getLogger(RootApplication.class.getName());

    @Inject
    IPaymentService service;

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
            TransactionDTO dto = new Gson().fromJson(message, TransactionDTO.class);

            String log = "RabbitMQ: Payments: Received '" +
                    delivery.getEnvelope().getRoutingKey() + "':'" + dto + "'";

            if (routingKey.contains("payment.payment")) {
                try {
                    service.createTransaction(dto.getCreditor(), dto.getDebtor(), dto.getAmount().intValue());
                } catch (TransactionException e) {
                    e.printStackTrace();
                } catch (CustomerException e) {
                    e.printStackTrace();
                } catch (MerchantException e) {
                    e.printStackTrace();
                }
            } else if (routingKey.contains("payment.refund")) {
                LOGGER.log(Level.INFO, "REFUND NOT IMPLEMENTED :)");
            }

            LOGGER.log(Level.INFO, log);
        };

        channel.basicConsume(queueNamePayments, true, paymentsCallback, consumerTag -> {
        });

        DeliverCallback transactionCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            String routingKey = delivery.getEnvelope().getRoutingKey();

            String log = "RabbitMQ: Transactions: Received '" +
                    delivery.getEnvelope().getRoutingKey() + "':'" + message + "'";

            if (routingKey.contains("transaction.latest")) {
                try {
                    // send this transaction to the caller!
                    Transaction t = service.getLatestTransaction(message);
                    channel.basicPublish(EXCHANGE_NAME, "transaction.reply", null, new Gson().toJson(t).getBytes(StandardCharsets.UTF_8));
                } catch (CustomerException e) {
                    e.printStackTrace();
                } catch (AccountException e) {
                    e.printStackTrace();
                }
            } else if (routingKey.contains("transaction.all")) {

            }


            LOGGER.log(Level.INFO, log);
        };

        channel.basicConsume(queueNameTransactions, true, transactionCallback, consumerTag -> {
        });
    }

}