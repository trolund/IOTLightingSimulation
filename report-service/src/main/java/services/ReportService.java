package services;

import domain.ExampleObj;
import dto.ExampleObjDTO;
import dto.TransactionDTO;
import infrastructure.repositories.interfaces.IExampleRepository;
import messaging.Event;
import org.modelmapper.ModelMapper;
import services.interfaces.IReportService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

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
    public Event requestAllTransactions() throws Exception {
        return new Event("requestAllTransactions");
    }

    @Override
    public TransactionDTO[] displayAllTransactions(TransactionDTO[] transactions) {
        return transactions;
    }
}
