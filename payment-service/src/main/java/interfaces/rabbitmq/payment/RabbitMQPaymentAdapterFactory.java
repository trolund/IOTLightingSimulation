package interfaces.rabbitmq.payment;

import messaging.EventSender;
import services.Bank;
import services.MapperService;
import services.PaymentEventService;
import services.PaymentService;

import javax.inject.Inject;
/**
 * @primary-author Daniel (s151641)
 * @co-author Troels (s161791)
 */
public class RabbitMQPaymentAdapterFactory {

    static PaymentEventService paymentEventService = null;

    public PaymentEventService getService() {
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
        paymentEventService = new PaymentEventService(b, new PaymentService(new Bank(), new MapperService()));
        RabbitMQPaymentListener r = new RabbitMQPaymentListener(paymentEventService);
        try {
            r.listen();
        } catch (Exception e) {
            throw new Error(e);
        }
        return paymentEventService;
    }
}
