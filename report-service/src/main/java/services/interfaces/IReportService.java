package services.interfaces;

import dto.TransactionDTO;
import exceptions.transaction.TransactionException;
import infrastructure.repositories.interfaces.ITransactionRepository;

import javax.xml.datatype.DatatypeConfigurationException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

public interface IReportService {
    Map<String, BigDecimal> requestSummary(List<TransactionDTO> transactions) throws Exception;
    List<TransactionDTO> requestAllCustomerTransactions(List<TransactionDTO> transactions, String customerId);
    List<TransactionDTO> requestAllCustomerTransactionsBetween(List<TransactionDTO> transactions, String customerId, String beg, String end) throws DatatypeConfigurationException, ParseException;
    ITransactionRepository getRepo();
    List<TransactionDTO> requestAllMerchantTransactions(List<TransactionDTO> transactions, String merchantId);
    List<TransactionDTO> requestAllMerchantTransactionsBetween(List<TransactionDTO> transactions, String merchantId, String beg, String end) throws DatatypeConfigurationException, ParseException;

    void addToRepo(TransactionDTO transaction) throws TransactionException;
}
