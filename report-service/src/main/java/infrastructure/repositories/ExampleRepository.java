package infrastructure.repositories;

import domain.ExampleObj;
import infrastructure.repositories.interfaces.IExampleRepository;

import javax.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class ExampleRepository implements IExampleRepository {

    // This class should probably be a singleton class (?)
    // or maybe this does not matter because dependency injection?

    private final List<ExampleObj> exampleObjs;

    public ExampleRepository() {
        exampleObjs = new ArrayList<>();
    }

    @Override
    public void add(ExampleObj obj) {
        exampleObjs.add(obj);
    }

    @Override
    public ExampleObj get(Integer id) {
        ExampleObj exampleObj = exampleObjs.stream()
                .filter(obj -> obj.getId().equals(id))
                .findAny()
                .orElse(null);

        if (exampleObj == null) {
            // throw exception if null
        }

        return exampleObj;
    }

    @Override
    public List<ExampleObj> getAll() {
        return exampleObjs;
    }

    @Override
    public void update(ExampleObj obj) {
        delete(obj.getId());
        add(obj);
    }

    @Override
    public void delete(Integer id) {
        exampleObjs.remove(get(id));
    }

    @Override
    public ExampleObj readExample() {
        return new ExampleObj(101, "Example obj");
    }

}