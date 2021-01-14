package interfaces.rabbitmq.payment;

import messaging.EventSender;
import services.PaymentEventService;

public class RabbitMQPaymentAdapterFactory {

    static PaymentEventService paymentEventService = null;

    public PaymentEventService getAdapter() {
        // The singleton pattern.
        // Ensure that there is at most
        // one instance of a PaymentService
        if (paymentEventService != null) {
            return paymentEventService;
        }

        // Hookup the classes to send and receive
        // messages via RabbitMq, i.e. RabbitMqSender and
        // RabbitMqListener.
        // This should be done in the factory to avoid
        // the PaymentService knowing about them. This
        // is called dependency injection.
        // At the end, we can use the PaymentService in tests
        // without sending actual messages to RabbitMq.
        EventSender b = new RabbitMQPaymentSender();
        paymentEventService = new PaymentEventService(b);
        RabbitMQPaymentListener r = new RabbitMQPaymentListener(paymentEventService);
        try {
            r.listen();
        } catch (Exception e) {
            throw new Error(e);
        }
        return paymentEventService;
    }
}
