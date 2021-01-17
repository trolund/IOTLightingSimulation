package services;

import dto.PaymentRequest;
import dto.TransactionDTO;
import exceptions.EventFailedException;
import exceptions.SendEventFailedException;
import interfaces.rest.RootApplication;
import messaging.Event;
import messaging.EventReceiver;
import messaging.EventSender;

import javax.enterprise.context.ApplicationScoped;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @primary-author Daniel (s151641)
 * @co-author Troels (s161791)
 */
@ApplicationScoped
public class PaymentEventService implements EventReceiver {

    private final static Logger LOGGER = Logger.getLogger(RootApplication.class.getName());
    private final EventSender eventSender;

    private CompletableFuture<TransactionDTO> processPaymentResult;
    private CompletableFuture<TransactionDTO> processRefundResult;

    public PaymentEventService(EventSender eventSender) {
        this.eventSender = eventSender;
    }

    @Override
    public void receiveEvent(Event event) {
        switch (event.getEventType()) {
            case "PaymentSuccessful":
            case "PaymentFailed":
                TransactionDTO transactionDTO = (TransactionDTO) event.getArguments()[0];
                processPaymentResult.complete(transactionDTO);
                break;
            case "RefundSuccessful":
            case "RefundFailed":
                transactionDTO = (TransactionDTO) event.getArguments()[0];
                processRefundResult.complete(transactionDTO);
                break;
            default:
                LOGGER.log(Level.WARNING, "Ignored event with type: " + event.getEventType() + ". Event: " + event.toString());
                break;
        }
    }

    public TransactionDTO sendProcessPaymentEvent(PaymentRequest paymentRequest)
            throws SendEventFailedException, EventFailedException {
        String eventType = "ProcessPayment";
        Object[] arguments = new Object[]{paymentRequest};
        Event event = new Event(eventType, arguments);
        processPaymentResult = new CompletableFuture<>();

        try {
            eventSender.sendEvent(event);
            TransactionDTO transactionDTO = processPaymentResult.join();

            if (transactionDTO == null) {
                throw new EventFailedException(eventType + " event has failed for paymentRequest: " + paymentRequest);
            }

            return transactionDTO;
        } catch (EventFailedException e) {
            throw new EventFailedException(e.getMessage());
        } catch (Exception e) {
            throw new SendEventFailedException("ProcessPayment event failed to send!");
        }
    }

    public TransactionDTO sendProcessRefundEvent(PaymentRequest paymentRequest)
            throws SendEventFailedException, EventFailedException {
        String eventType = "ProcessRefund";
        Object[] arguments = new Object[]{paymentRequest};
        Event event = new Event(eventType, arguments);
        processRefundResult = new CompletableFuture<>();

        try {
            eventSender.sendEvent(event);
            TransactionDTO transactionDTO = processRefundResult.join();

            if (transactionDTO == null) {
                throw new EventFailedException(eventType + " event has failed for paymentRequest: " + paymentRequest);
            }

            return transactionDTO;
        } catch (EventFailedException e) {
            throw new EventFailedException(e.getMessage());
        } catch (Exception e) {
            throw new SendEventFailedException(eventType + " event failed to send!");
        }
    }

}