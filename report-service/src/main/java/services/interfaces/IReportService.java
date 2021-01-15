package services.interfaces;

import dto.ExampleObjDTO;
import dto.TransactionDTO;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface IReportService {
    String hello();
    ExampleObjDTO readExample();
    Map<String, BigDecimal> requestSummary(List<TransactionDTO> transactions) throws Exception;
}
