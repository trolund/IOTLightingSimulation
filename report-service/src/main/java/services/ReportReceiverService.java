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

    IReportService rs;

    EventSender eventSender;

    public ReportReceiverService(EventSender eventSender, ReportService reportService) {
        this.eventSender = eventSender;
        this.rs = reportService;
    }

    public List<TransactionDTO> requestAllTransactions() {
        return rs.getRepo().getAll();
    }

    public Map<String, BigDecimal> requestSummary() throws Exception {
        List<TransactionDTO> transactions = requestAllTransactions();
        return rs.requestSummary(transactions);
    }

    public List<TransactionDTO> requestAllCustomerTransactions(String customerId) {
        List<TransactionDTO> transactions = requestAllTransactions();
        return rs.requestAllCustomerTransactions(transactions, customerId);
    }

    public List<TransactionDTO> requestAllCustomerTransactionsBetween(String customerId, String beg, String end) throws Exception {
        List<TransactionDTO> transactions = requestAllTransactions();
        return rs.requestAllCustomerTransactionsBetween(transactions, customerId, beg, end);
    }

    public List<TransactionDTO> requestAllMerchantTransactions(String merchantId) {
        List<TransactionDTO> transactions = requestAllTransactions();
        return rs.requestAllMerchantTransactions(transactions, merchantId);
    }

    public List<TransactionDTO> requestAllMerchantTransactionsBetween(String merchantId, String beg, String end) throws Exception {
        List<TransactionDTO> transactions = requestAllTransactions();
        return rs.requestAllMerchantTransactionsBetween(transactions, merchantId, beg, end);
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
