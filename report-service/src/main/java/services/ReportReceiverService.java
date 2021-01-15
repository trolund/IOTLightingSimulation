package services;

import dto.TransactionDTO;
import messaging.Event;
import messaging.EventReceiver;
import messaging.EventSender;
import services.interfaces.IReportService;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class ReportReceiverService implements EventReceiver {

    IReportService rs = new ReportService();

    EventSender eventSender;

    private CompletableFuture<List<TransactionDTO>> result;
    private CompletableFuture<Map<String, BigDecimal>> summary;

    public ReportReceiverService(EventSender eventSender) {
        this.eventSender = eventSender;
    }

    public List<TransactionDTO> requestAllTransactions() throws Exception {
        Event event = new Event("requestAllTransactionsFromPayment", new Object[] {});
        result = new CompletableFuture<>();
        eventSender.sendEvent(event);
        return result.join();
    }

    public Map<String, BigDecimal> requestSummary() throws Exception {
        List<TransactionDTO> transactions = requestAllTransactions();
        return rs.requestSummary(transactions);
    }

    @Override
    public void receiveEvent(Event event) throws Exception {
        System.out.println("handling event: "+event);
        if (event.getEventType().equals("requestAllTransactions")) {
            List<TransactionDTO> transactions = requestAllTransactions();
            eventSender.sendEvent(new Event("requestAllTransactionsSuccessful", new Object[] {transactions}));
        } else if (event.getEventType().equals("requestAllTransactionsReply")) {
            result.complete((List<TransactionDTO>) event.getArguments()[0]);
        } else if (event.getEventType().equals("requestSummary")) {
            Map<String, BigDecimal> summary = requestSummary();
            eventSender.sendEvent(new Event("requestSummarySuccessful", new Object[] {summary}));
        }
    }
}
