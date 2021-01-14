package interfaces.rabbitmq;

import dto.TransactionDTO;
import interfaces.rest.RootApplication;
import messaging.Event;
import messaging.EventReceiver;
import messaging.EventSender;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RabbitMQAdapter implements EventReceiver {

    private final static Logger LOGGER = Logger.getLogger(RootApplication.class.getName());

    private final EventSender eventSender;

    public RabbitMQAdapter(EventSender eventSender) {
        this.eventSender = eventSender;
    }

    @Override
    public void receiveEvent(Event event) throws Exception {
        Event eventToSend;
        Object[] arguments;

        switch (event.getEventType()) {
            case "getLatestTransaction":
                TransactionDTO dto = (TransactionDTO) event.getArguments()[0];
                arguments = new Object[]{dto};
                eventToSend = new Event("getLatestTransaction", arguments);
                eventSender.sendEvent(eventToSend);
                break;
            case "getTransactions":
                List<TransactionDTO> dtos = (List<TransactionDTO>) event.getArguments()[0];
                arguments = new Object[]{dtos};
                eventToSend = new Event("getAllTransactions", arguments);
                eventSender.sendEvent(eventToSend);
                break;
            default:
                LOGGER.log(Level.WARNING, "Ignored event with type: " + event.getEventType() + ". Event: " + event.toString());
                break;
        }
    }

}