package services;

import dto.TransactionDTO;
import infrastructure.repositories.TransactionRepository;
import infrastructure.repositories.interfaces.ITransactionRepository;
import services.interfaces.IReportService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.math.RoundingMode.HALF_UP;

// All of the business logic concerning the
// Example domain should be written here.
@ApplicationScoped
public class ReportService implements IReportService {

    ITransactionRepository repo = new TransactionRepository();

    public String hello() {
        return "I am healthy and ready to work!";
    }

    @Override
    public Map<String, BigDecimal> requestSummary(List<TransactionDTO> transactions) throws Exception {
        Map<String, BigDecimal> summary = new HashMap<>();
        BigDecimal min = null, max = null, sum = null;
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
        BigDecimal mean = sum.divide(new BigDecimal(transactions.size()), HALF_UP);
        summary.put("min", min);
        summary.put("max", max);
        summary.put("mean", mean);
        summary.put("sum", sum);
        return summary;
    }

    @Override
    public List<TransactionDTO> requestAllCustomerTransactions(List<TransactionDTO> transactions, String customerId) {
        transactions.removeIf(obj -> !obj.getCreditor().equals(customerId));
        return transactions;
    }

    @Override
    public List<TransactionDTO> requestAllCustomerTransactionsBetween(List<TransactionDTO> transactions, String customerId, String beg, String end) throws DatatypeConfigurationException {
        XMLGregorianCalendar startDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(beg);
        XMLGregorianCalendar endDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(end);
        transactions.removeIf(obj -> !obj.getCreditor().equals(customerId) || obj.getTime().compare(startDate) == DatatypeConstants.LESSER || obj.getTime().compare(endDate) == DatatypeConstants.GREATER);
        return transactions;
    }

    @Override
    public List<TransactionDTO> getRepo() {
        return repo.getAll();
    }
}
