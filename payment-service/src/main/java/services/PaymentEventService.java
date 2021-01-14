package services;

import dto.TransactionDTO;
import exceptions.AccountException;
import exceptions.customer.CustomerException;
import interfaces.rest.RootApplication;
import messaging.Event;
import messaging.EventReceiver;
import messaging.EventSender;
import services.interfaces.IPaymentService;
import javax.inject.Inject;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class PaymentEventService implements EventReceiver {

    private final static Logger LOGGER = Logger.getLogger(RootApplication.class.getName());

    private final EventSender eventSender;

    @Inject
    IPaymentService ps;

    public PaymentEventService(EventSender eventSender) {
        this.eventSender = eventSender;
    }

    @Override
    public void receiveEvent(Event event) {
        Event eventToSend;
        Object[] arguments = null;

        String accountId;
        try {
            switch (event.getEventType()) {
                case "getLatestTransaction":
                     accountId = (String) event.getArguments()[0];
                     try {
                         arguments = new Object[]{ps.getLatestTransaction(accountId)};
                         eventToSend = new Event("getLatestTransactionSuccessful", arguments);
                     }catch (Exception e){
                         e.printStackTrace();
                         eventToSend = new Event("getLatestTransactionFailed");
                     }
                    eventSender.sendEvent(eventToSend);
                    break;
                case "getTransactions":
                     accountId = (String) event.getArguments()[0];

                    List<TransactionDTO> dtosMapped = null;
                    try {
                        dtosMapped = ps.getTransactions(accountId).stream().map(t -> {

                            String[] tokenarray = t.getDescription().split(" ");
                            String token = tokenarray[tokenarray.length - 1];

                            t.setToken(token);

                            return t;
                        }).collect(Collectors.toList());
                        arguments = new Object[]{dtosMapped};
                        eventToSend = new Event("getAllTransactionsSuccessful", arguments);
                    } catch (CustomerException | AccountException e) {
                        e.printStackTrace();
                        eventToSend = new Event("getAllTransactionsFailed");
                    }

                    eventSender.sendEvent(eventToSend);
                    break;
                default:
                    LOGGER.log(Level.WARNING, "Ignored event with type: " + event.getEventType() + ". Event: " + event.toString());
                    break;
            }
        }catch (Exception e){
            LOGGER.log(Level.WARNING, "Event send failed: " + e.getMessage());
        }
    }

}