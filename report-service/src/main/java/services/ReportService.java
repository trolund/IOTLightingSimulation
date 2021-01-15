package services;

import domain.ExampleObj;
import dto.ExampleObjDTO;
import dto.TransactionDTO;
import infrastructure.repositories.interfaces.IExampleRepository;
import org.modelmapper.ModelMapper;
import services.interfaces.IReportService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.math.RoundingMode.HALF_UP;

// All of the business logic concerning the
// Example domain should be written here.
@ApplicationScoped
public class ReportService implements IReportService {

    @Inject
    IExampleRepository repo;

    public String hello() {
        return "I am healthy and ready to work!";
    }

    public ExampleObjDTO readExample() {
        ModelMapper mapper = new ModelMapper();
        ExampleObj exampleDto = repo.readExample();
        return mapper.map(exampleDto, ExampleObjDTO.class);
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
}
