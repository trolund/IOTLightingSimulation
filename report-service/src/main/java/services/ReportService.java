package services;

import dto.MoneySummary;
import dto.TransactionDTO;
import exceptions.transaction.TransactionException;
import infrastructure.repositories.TransactionRepository;
import infrastructure.repositories.interfaces.ITransactionRepository;
import services.interfaces.IReportService;

import javax.xml.datatype.DatatypeConfigurationException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.math.RoundingMode.HALF_UP;

public class ReportService implements IReportService {

    private static final ITransactionRepository repo = TransactionRepository.getInstance();

    @Override
    public MoneySummary getSummary(){
        List<TransactionDTO> list = repo.getAll();
        System.out.println(list);
        return new MoneySummary(requestSummary(list), list);
    }

    @Override
    public List<TransactionDTO> customerReport(String customerId, String start, String end) throws DatatypeConfigurationException, ParseException {
        List<TransactionDTO> list = repo.getAll();
        return requestAllCustomerTransactionsBetween(list, customerId, start, end);
    }

    @Override
    public List<TransactionDTO> customerReport(String customerId) {
        List<TransactionDTO> list = repo.getAll();
        return requestAllCustomerTransactions(list, customerId);
    }

    @Override
    public List<TransactionDTO> merchantReport(String merchantId, String start, String end) throws DatatypeConfigurationException, ParseException {
        List<TransactionDTO> list = repo.getAll();
        return requestAllMerchantTransactionsBetween(list, merchantId, start, end);
    }

    @Override
    public List<TransactionDTO> merchantReport(String merchantId) {
        List<TransactionDTO> list = repo.getAll();
        return requestAllMerchantTransactions(list, merchantId);
    }

    @Override
    public Map<String, BigDecimal> requestSummary(List<TransactionDTO> transactions) {
        Map<String, BigDecimal> summary = new HashMap<>();
        BigDecimal min = null, max = null, sum = null, mean = null;
        for(TransactionDTO t : transactions) {
            if (min == null) {
                min = t.getAmount();
                max = t.getAmount();
                sum = t.getAmount();
            } else {
                min = min.min(t.getAmount());
                max = max.max(t.getAmount());
                sum = sum.add(t.getAmount());
            }
        }
        if (sum != null) {
            mean = sum.divide(new BigDecimal(transactions.size()), HALF_UP);
        }
        summary.put("min", min);
        summary.put("max", max);
        summary.put("mean", mean);
        summary.put("sum", sum);
        return summary;
    }

    @Override
    public List<TransactionDTO> requestAllCustomerTransactions(List<TransactionDTO> transactions, String customerId) {
        transactions.removeIf(obj -> !obj.getDebtor().equals(customerId));
        return transactions;
    }

    @Override
    public List<TransactionDTO> requestAllCustomerTransactionsBetween(List<TransactionDTO> transactions, String customerId, String beg, String end) throws ParseException {
        SimpleDateFormat formatter =new SimpleDateFormat("E, MMM dd yyyy HH:mm:ss");

        Date startDate = formatter.parse(beg);
        Date endDate = formatter.parse(end);
        transactions.removeIf(obj -> obj.getTime() == null);
        transactions.removeIf(obj -> !obj.getDebtor().equals(customerId) || obj.getTime().compareTo(startDate) < 0 || obj.getTime().compareTo(endDate) > 0);
        return transactions;
    }

    @Override
    public ITransactionRepository getRepo() {
        return repo;
    }

    @Override
    public List<TransactionDTO> requestAllMerchantTransactions(List<TransactionDTO> transactions, String merchantId) {
        transactions.removeIf(obj -> !obj.getCreditor().equals(merchantId));
        for(TransactionDTO transaction : transactions) {
            transaction.setDebtor(null);
            transaction.setBalance(null);
        }
        return transactions;
    }

    @Override
    public List<TransactionDTO> requestAllMerchantTransactionsBetween(List<TransactionDTO> transactions, String merchantId, String beg, String end) throws ParseException {
        SimpleDateFormat formatter =new SimpleDateFormat("E, MMM dd yyyy HH:mm:ss");

        Date startDate = formatter.parse(beg);
        Date endDate = formatter.parse(end);
        transactions.removeIf(obj -> obj.getTime() == null);
        transactions.removeIf(obj -> !obj.getCreditor().equals(merchantId) || obj.getTime().compareTo(startDate) < 0 || obj.getTime().compareTo(endDate) > 0);
        for(TransactionDTO transaction : transactions) {
            transaction.setDebtor(null);
            transaction.setBalance(null);
        }
        return transactions;
    }

    @Override
    public void addToRepo(TransactionDTO transaction) throws TransactionException {
        if (transaction.getAmount() != null && transaction.getCreditor() != null && transaction.getDebtor() != null) {
            repo.add(transaction);
        } else {
            throw new TransactionException("Transaction formed incorrectly");
        }
    }
}
