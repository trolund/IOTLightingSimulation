package interfaces.rabbitmq.token;

import messaging.EventSender;
import services.TokenEventService;

/**
 * @primary-author Troels (s161791)
 * @co-author Daniel (s151641)
 * <p>
 * Thanks to Hubert Baumeister (huba@dtu.dk) for initial
 * rabbitMQ implementation template.
 */
public class RabbitMQTokenAdapterFactory {

    static TokenEventService tokenEventService = null;

    public TokenEventService getService() {
        // The singleton pattern.
        // Ensure that there is at most
        // one instance of a PaymentService
        if (tokenEventService != null) {
            return tokenEventService;
        }

        // Hookup the classes to send and receive
        // messages via RabbitMq, i.e. RabbitMqSender and
        // RabbitMqListener.
        // This should be done in the factory to avoid
        // the PaymentService knowing about them. This
        // is called dependency injection.
        // At the end, we can use the PaymentService in tests
        // without sending actual messages to RabbitMq.
        EventSender b = new RabbitMQTokenSender();
        tokenEventService = new TokenEventService(b);
        RabbitMQTokenListener r = new RabbitMQTokenListener(tokenEventService);

        try {
            r.listen();
        } catch (Exception e) {
            throw new Error(e);
        }

        return tokenEventService;
    }
}
