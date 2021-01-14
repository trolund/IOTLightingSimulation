package services;

import dto.TransactionDTO;
import interfaces.rest.RootApplication;
import messaging.Event;
import messaging.EventReceiver;
import messaging.EventSender;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TokenEventService implements EventReceiver {

    private final static Logger LOGGER = Logger.getLogger(RootApplication.class.getName());

    private final EventSender eventSender;

    private CompletableFuture<Boolean> result;

    public TokenEventService(EventSender eventSender) {
        this.eventSender = eventSender;
    }

    public boolean validateToken(String token) throws Exception {
        Event event = new Event("validateToken", new Object[] {token});
        result = new CompletableFuture<>();

        eventSender.sendEvent(event);

        return result.join();
    }

    @Override
    public void receiveEvent(Event event) throws Exception {
        Event eventToSend;
        Object[] arguments;

        switch (event.getEventType()) {
            case "TokenRequestSuccessful":
                    // String id = (String) event.getArguments()[0];
                    result.complete(true);
                break;
            case "TokenRequestFaild":
                    // String id = (String) event.getArguments()[0];
                    result.complete(false);
                break;
            default:
                LOGGER.log(Level.WARNING, "Ignored event with type: " + event.getEventType() + ". Event: " + event.toString());
                break;
        }
    }

}