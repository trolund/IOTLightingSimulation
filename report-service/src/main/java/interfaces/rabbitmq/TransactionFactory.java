package interfaces.rabbitmq;

import messaging.EventSender;
import services.ReportService;
import services.TransactionSpyService;

public class TransactionFactory {
    static TransactionSpyService transactionSpyService = null;

    public TransactionSpyService getService(ReportService reportService) {
        // The singleton pattern.
        // Ensure that there is at most
        // one instance of a PaymentService
        if (transactionSpyService != null) {
            return transactionSpyService;
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
        transactionSpyService = new TransactionSpyService(b, reportService);
        RabbitMQReceiver r = new RabbitMQReceiver(transactionSpyService);

        try {
            r.initConnection("payments.*");
        } catch (Exception e) {
            throw new Error(e);
        }

        return transactionSpyService;
    }
}
