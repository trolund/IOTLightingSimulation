package interfaces.rabbitmq;

import messaging.EventSender;

public class RabbitMQAdapterFactory {

    static RabbitMQAdapter rabbitMQAdapter = null;

    public RabbitMQAdapter getAdapter() {
        // The singleton pattern.
        // Ensure that there is at most
        // one instance of a PaymentService
        if (rabbitMQAdapter != null) {
            return rabbitMQAdapter;
        }

        // Hookup the classes to send and receive
        // messages via RabbitMq, i.e. RabbitMqSender and
        // RabbitMqListener.
        // This should be done in the factory to avoid
        // the PaymentService knowing about them. This
        // is called dependency injection.
        // At the end, we can use the PaymentService in tests
        // without sending actual messages to RabbitMq.
        EventSender b = new RabbitMQSender();
        rabbitMQAdapter = new RabbitMQAdapter(b);
        RabbitMQListener r = new RabbitMQListener(rabbitMQAdapter);
        try {
            r.listen();
        } catch (Exception e) {
            throw new Error(e);
        }
        return rabbitMQAdapter;
    }
}
