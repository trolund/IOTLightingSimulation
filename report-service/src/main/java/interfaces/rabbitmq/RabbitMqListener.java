package interfaces.rabbitmq;

import com.google.gson.Gson;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import interfaces.ReportReceiver;
import messaging.Event;
import messaging.EventReceiver;
import messaging.EventSender;

public class RabbitMqListener {

	private static final String EXCHANGE_NAME = "message-hub";
	private static final String QUEUE_TYPE = "topic";
	private static final String TOPIC = "report.*";

	EventSender service;
	private static Channel channel;

	public RabbitMqListener(ReportReceiver service) {
		this.service = service;
	}

	public void initConnection() throws Exception {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("rabbitMq");
		Connection connection = factory.newConnection();
		channel = connection.createChannel();
		channel.exchangeDeclare(EXCHANGE_NAME, QUEUE_TYPE);
		String queueName = channel.queueDeclare().getQueue();
		channel.queueBind(queueName, EXCHANGE_NAME, TOPIC);

		DeliverCallback deliverCallback = (consumerTag, delivery) -> {
			String message = new String(delivery.getBody(), "UTF-8");
			System.out.println("[x] receiving "+message);

			Event event = new Gson().fromJson(message, Event.class);
			try {
				service.receiveEvent(event);
			} catch (Exception e) {
				throw new Error(e);
			}
		};
		channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {
		});
	}
}
