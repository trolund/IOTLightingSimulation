package services;

import domain.CustomerTokens;
import dto.ExampleObjDTO;
import exceptions.CustomerNotFoundException;
import exceptions.TooManyTokensException;
import infrastructure.repositories.CustomerTokensRepository;
import infrastructure.repositories.interfaces.IExampleRepository;
import org.modelmapper.ModelMapper;
import services.interfaces.IExampleService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

// All of the business logic concerning the
// Example domain should be written here.
@ApplicationScoped
public class ExampleService implements IExampleService {

    CustomerTokensRepository repo = new CustomerTokensRepository();

    public String hello() {
        return "I am healthy and ready to work!";
    }

    public ExampleObjDTO readExample() {
        ModelMapper mapper = new ModelMapper();
        CustomerTokens exampleDto = repo.readExample();
        return mapper.map(exampleDto, ExampleObjDTO.class);
    }

    public void addCustomer(String customerId) {
        CustomerTokens ct = new CustomerTokens(customerId);
        repo.add(ct);
    }

    public CustomerTokens getCustomer(String customerId) throws CustomerNotFoundException {
        return repo.get(customerId);
    }

    public void addTokens(String customerId, int amount) throws CustomerNotFoundException, TooManyTokensException {
        repo.get(customerId).addTokens(amount);
    }

}
