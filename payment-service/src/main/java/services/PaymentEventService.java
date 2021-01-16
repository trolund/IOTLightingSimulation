package services;

import dto.TransactionDTO;
import exceptions.account.AccountNotFoundException;
import interfaces.rest.RootApplication;
import messaging.Event;
import messaging.EventReceiver;
import messaging.EventSender;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * @primary-author Daniel (s151641)
 * @co-author Troels (s161791)
 */
public class PaymentEventService implements EventReceiver {

    private final static Logger LOGGER = Logger.getLogger(RootApplication.class.getName());
    private final EventSender eventSender;
    TransactionService ts;
    private CompletableFuture<String> result;

    public PaymentEventService(EventSender eventSender, TransactionService ts) {
        this.eventSender = eventSender;
        this.ts = ts;
    }

    @Override
    public void receiveEvent(Event event) {
        switch (event.getEventType()) {
            case "getLatestTransaction":
                getLatestTransaction(event);
                break;
            case "getTransactions":
                getTransactions(event);
                break;
            default:
                LOGGER.log(Level.WARNING, "Ignored event with type: " + event.getEventType() + ". Event: " + event.toString());
                break;
        }
    }

    public void sendTransactionDone(TransactionDTO dto, boolean successful) throws Exception {
        Event event = null;
        if (successful) {
            event = new Event("TransactionSuccessful", new Object[]{dto});
        } else {
            event = new Event("TransactionFailed", new Object[]{dto});
        }
        eventSender.sendEvent(event);
    }

    private void getLatestTransaction(Event event) {
        Object[] arguments;
        Event eventToSend;

        String accountId = (String) event.getArguments()[0];

        try {
            arguments = new Object[]{ts.getLatestTransaction(accountId)};
            eventToSend = new Event("getLatestTransactionSuccessful", arguments);
        } catch (Exception e) {
            e.printStackTrace();
            eventToSend = new Event("getLatestTransactionFailed");
        }

        try {
            eventSender.sendEvent(eventToSend);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.log(Level.WARNING, "Event send failed: " + e.getMessage());
        }
    }

    private void getTransactions(Event event) {
        Object[] arguments;
        Event eventToSend;

        String accountId = (String) event.getArguments()[0];

        try {
            List<TransactionDTO> dtos = ts.getTransactions(accountId);

            List<TransactionDTO> dtosMapped = dtos.stream().peek(t -> {

                String[] tokenarray = t.getDescription().split(" ");
                String token = tokenarray[tokenarray.length - 1];

                t.setToken(token);

            }).collect(Collectors.toList());
            arguments = new Object[]{dtosMapped};
            eventToSend = new Event("getAllTransactionsSuccessful", arguments);
        } catch (AccountNotFoundException e) {
            e.printStackTrace();
            eventToSend = new Event("getAllTransactionsFailed");
        }

        try {
            eventSender.sendEvent(eventToSend);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.log(Level.WARNING, "Event send failed: " + e.getMessage());
        }
    }

}