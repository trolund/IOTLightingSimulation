package services.interfaces;

import dto.ExampleObjDTO;
import dto.TransactionDTO;

import javax.xml.datatype.DatatypeConfigurationException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface IReportService {
    String hello();
    Map<String, BigDecimal> requestSummary(List<TransactionDTO> transactions) throws Exception;
    List<TransactionDTO> requestAllCustomerTransactions(List<TransactionDTO> transactions, String customerId);
    List<TransactionDTO> requestAllCustomerTransactionsBetween(List<TransactionDTO> transactions, String customerId, String beg, String end) throws DatatypeConfigurationException;

    List<TransactionDTO> getRepo();
}
