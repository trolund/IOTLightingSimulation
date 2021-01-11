package services;

import domain.ExampleObj;
import dto.ExampleObjDTO;
import infrastructure.repositories.interfaces.IExampleRepository;
import org.modelmapper.ModelMapper;
import services.interfaces.IExampleService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

// All of the business logic concerning the
// Example domain should be written here.
@ApplicationScoped
public class ExampleService implements IExampleService {

    @Inject
    IExampleRepository repo;

    @Inject
    ModelMapper mapper;

    public String hello() {
        return "I am healthy and ready to work!";
    }

    public ExampleObjDTO readExample() {
        ExampleObj exampleDto = repo.readExample();
        return mapper.map(exampleDto, ExampleObjDTO.class);
    }

}
