package interfaces.rabbitmq.payment;

import messaging.EventSender;
import services.*;

/**
 * @primary-author Daniel (s151641)
 * @co-author Troels (s161791)
 * <p>
 * Thanks to Hubert Baumeister (huba@dtu.dk) for initial
 * rabbitMQ implementation template.
 */
public class PaymentEventServiceFactory {

    static PaymentService paymentService = null;

    public PaymentService getService() {
        // The singleton pattern.
        // Ensure that there is at most
        // one instance of a PaymentService
        if (paymentService != null) {
            return paymentService;
        }

        // Hookup the classes to send and receive
        // messages via RabbitMq, i.e. RabbitMqSender and
        // RabbitMqListener.
        // This should be done in the factory to avoid
        // the PaymentService knowing about them. This
        // is called dependency injection.
        // At the end, we can use the PaymentService in tests
        // without sending actual messages to RabbitMq.
        EventSender b = new PaymentSender();
        paymentService = new PaymentService(b);
        PaymentListener r = new PaymentListener(paymentService);
        try {
            r.listen();
        } catch (Exception e) {
            throw new Error(e);
        }
        return paymentService;
    }
}
