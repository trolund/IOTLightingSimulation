package services;

import domain.ExampleObj;
import dto.ExampleObjDTO;
import dto.TransactionDTO;
import infrastructure.repositories.interfaces.IExampleRepository;
import messaging.Event;
import messaging.EventSender;
import org.modelmapper.ModelMapper;
import services.interfaces.IReportService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.concurrent.CompletableFuture;

// All of the business logic concerning the
// Example domain should be written here.
@ApplicationScoped
public class ReportService implements IReportService {

    @Inject
    IExampleRepository repo;
    private CompletableFuture<List<TransactionDTO>> result;

    public String hello() {
        return "I am healthy and ready to work!";
    }

    public ExampleObjDTO readExample() {
        ModelMapper mapper = new ModelMapper();
        ExampleObj exampleDto = repo.readExample();
        return mapper.map(exampleDto, ExampleObjDTO.class);
    }

    @Override
    public void requestAllTransactions(EventSender eventSender) throws Exception {
        Event event = new Event("requestAllTransactions");
        result = new CompletableFuture<>();
        eventSender.sendEvent(event);
    }

    @Override
    public TransactionDTO[] displayAllTransactions(TransactionDTO[] transactions) {
        return transactions;
    }
}
