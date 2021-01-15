package services;

import dto.TransactionDTO;
import infrastructure.repositories.interfaces.ITransactionRepository;
import messaging.Event;
import messaging.EventReceiver;

import javax.inject.Inject;

public class TransactionSpyService implements EventReceiver {

    @Inject
    ITransactionRepository repo;

    @Override
    public void receiveEvent(Event event) throws Exception {
        System.out.println("handling event: "+event);
        if(event.getEventType().equals("TransactionSuccessful")) {
            TransactionDTO transaction = (TransactionDTO) event.getArguments()[0];
            repo.add(transaction);
        }
    }
}
