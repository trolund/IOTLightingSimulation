package services.interfaces;

import dto.MoneySummary;
import dto.TransactionDTO;
import exceptions.transaction.TransactionException;
import infrastructure.repositories.interfaces.ITransactionRepository;
import javax.xml.datatype.DatatypeConfigurationException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

public interface IReportService {
    List<TransactionDTO> customerReport(String customerId);

    List<TransactionDTO> merchantReport(String merchantId, String start, String end) throws DatatypeConfigurationException, ParseException;
    List<TransactionDTO> customerReport(String customerId, String start, String end) throws DatatypeConfigurationException, ParseException;
    MoneySummary getSummary();

    List<TransactionDTO> merchantReport(String merchantId);

    Map<String, BigDecimal> requestSummary(List<TransactionDTO> transactions) throws Exception;
    List<TransactionDTO> requestAllCustomerTransactions(List<TransactionDTO> transactions, String customerId);
    List<TransactionDTO> requestAllCustomerTransactionsBetween(List<TransactionDTO> transactions, String customerId, String beg, String end) throws DatatypeConfigurationException, ParseException;
    ITransactionRepository getRepo();
    List<TransactionDTO> requestAllMerchantTransactions(List<TransactionDTO> transactions, String merchantId);
    List<TransactionDTO> requestAllMerchantTransactionsBetween(List<TransactionDTO> transactions, String merchantId, String beg, String end) throws DatatypeConfigurationException, ParseException;

    void addToRepo(TransactionDTO transaction) throws TransactionException;
}
