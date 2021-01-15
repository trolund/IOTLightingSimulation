package services;

import dto.TransactionDTO;
import messaging.Event;
import messaging.EventReceiver;
import messaging.EventSender;
import services.interfaces.IReportService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class ReportReceiverService implements EventReceiver {

    IReportService rs = new ReportService();

    EventSender eventSender;

    public ReportReceiverService(EventSender eventSender) {
        this.eventSender = eventSender;
    }

    public List<TransactionDTO> requestAllTransactions() throws Exception {
        return rs.getRepo();
    }

    public Map<String, BigDecimal> requestSummary() throws Exception {
        List<TransactionDTO> transactions = requestAllTransactions();
        return rs.requestSummary(transactions);
    }

    public List<TransactionDTO> requestAllCustomerTransactions(String customerId) throws Exception {
        List<TransactionDTO> transactions = requestAllTransactions();
        return rs.requestAllCustomerTransactions(transactions, customerId);
    }

    public List<TransactionDTO> requestAllCustomerTransactionsBetween(String customerId, String beg, String end) throws Exception {
        List<TransactionDTO> transactions = requestAllTransactions();
        return rs.requestAllCustomerTransactionsBetween(transactions, customerId, beg, end);
    }

    @Override
    public void receiveEvent(Event event) throws Exception {
        List<TransactionDTO> transactions;
        System.out.println("handling event: "+event);
        switch (event.getEventType()) {
            case "requestSummary":
                Map<String, BigDecimal> summary = requestSummary();
                eventSender.sendEvent(new Event("requestSummarySuccessful", new Object[]{summary}));
                break;
            case "requestAllCustomerTransactions":
                transactions = requestAllCustomerTransactions((String) event.getArguments()[0]);
                eventSender.sendEvent(new Event("requestAllCustomerTransactionsSuccessful", new Object[]{transactions}));
                break;
        }
    }
}
