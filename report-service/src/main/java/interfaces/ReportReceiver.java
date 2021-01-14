package interfaces;

import dto.TransactionDTO;
import messaging.Event;
import messaging.EventReceiver;
import messaging.EventSender;
import services.interfaces.IReportService;

import javax.inject.Inject;
import java.util.List;

public class ReportReceiver implements EventReceiver {
    @Inject
    IReportService rs;

    private Event outputEvent;
    EventSender eventSender;

    public ReportReceiver(EventSender eventSender) {
        this.eventSender = eventSender;
    }

    @Override
    public void receiveEvent(Event event) throws Exception {
        System.out.println("handling event: "+event);
        if (event.getEventType().equals("requestAllTransactions")) {
            rs.requestAllTransactions(eventSender);
        } else if (event.getEventType().equals("RequestAllTransactionsReply")) {
            TransactionDTO[] transactions = rs.displayAllTransactions((TransactionDTO[]) event.getArguments()[0]);
            Object[] output = new Object[1];
            output[0] = transactions;
            outputEvent = new Event("requestAllTransactionsReply", output);
            eventSender.sendEvent(outputEvent);
        }
    }
}
