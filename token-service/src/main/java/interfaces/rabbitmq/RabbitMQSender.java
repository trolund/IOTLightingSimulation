package interfaces.rabbitmq;

import com.google.gson.Gson;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import messaging.Event;
import messaging.EventReceiver;
import messaging.EventSender;

public class RabbitMQSender implements EventSender {

    private static final String EXCHANGE_NAME = "message-hub";
    private static final String QUEUE_TYPE = "topic";
    private static final String TOPIC = "report.service";

    private EventReceiver service;
    private Channel channel;

    public void initConnection() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("rabbitMq");
        Connection connection = factory.newConnection();
        channel = connection.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, QUEUE_TYPE);
    }

    @Override
    public void sendEvent(Event event) throws Exception {
        String message = new Gson().toJson(event);
        System.out.println("[x] sending "+message);
        channel.basicPublish(EXCHANGE_NAME, TOPIC, null, message.getBytes("UTF-8"));
    }
}
