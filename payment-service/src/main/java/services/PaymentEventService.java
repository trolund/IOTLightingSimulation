package services;

import dto.TransactionDTO;
import exceptions.AccountException;
import exceptions.customer.CustomerException;
import interfaces.rest.RootApplication;
import messaging.Event;
import messaging.EventReceiver;
import messaging.EventSender;
import services.interfaces.IPaymentService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class PaymentEventService implements EventReceiver {

    /*
     * This DI is NOT working..
     */
    @Inject
    IPaymentService ps;

    private final static Logger LOGGER = Logger.getLogger(RootApplication.class.getName());

    private final EventSender eventSender;

    public PaymentEventService(EventSender eventSender) {
        this.eventSender = eventSender;
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

    private void getLatestTransaction(Event event) {
        Object[] arguments;
        Event eventToSend;

        String accountId = (String) event.getArguments()[0];

        try {
            arguments = new Object[]{ps.getLatestTransaction(accountId)};
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
            List<TransactionDTO> dtos = ps.getTransactions(accountId);

            List<TransactionDTO> dtosMapped = dtos.stream().peek(t -> {

                String[] tokenarray = t.getDescription().split(" ");
                String token = tokenarray[tokenarray.length - 1];

                t.setToken(token);

            }).collect(Collectors.toList());
            arguments = new Object[]{dtosMapped};
            eventToSend = new Event("getAllTransactionsSuccessful", arguments);
        } catch (AccountException | CustomerException e) {
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