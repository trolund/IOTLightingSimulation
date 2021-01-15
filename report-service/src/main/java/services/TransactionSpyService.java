package services;

import dto.TransactionDTO;
import exceptions.TransactionException;
import infrastructure.repositories.interfaces.ITransactionRepository;
import messaging.Event;
import messaging.EventReceiver;
import messaging.EventSender;

import javax.inject.Inject;

public class TransactionSpyService implements EventReceiver {

    private final EventSender eventSender;
    private final ReportService rs;

    public TransactionSpyService(EventSender eventSender, ReportService reportService) {
        this.eventSender = eventSender;
        this.rs = reportService;
    }

    @Override
    public void receiveEvent(Event event) throws Exception {
        System.out.println("handling event: "+event);
        if(event.getEventType().equals("TransactionSuccessful")) {
            TransactionDTO transaction = (TransactionDTO) event.getArguments()[0];
            Event newEvent = new Event("TransactionRecordingSuccessful", new Object[] {transaction});
            try {
                rs.addToRepo(transaction);
            } catch (TransactionException e) {
                newEvent = new Event("TransactionRecordingFailed", new Object[] {transaction});
            }
            System.out.println("Transaction recording failed!");
            eventSender.sendEvent(newEvent);
        }
    }
}
