package infrastructure.repositories.interfaces;

import domain.ExampleObj;

// Specific to "Example" entity.
// These methods should be specific to the given entity. For example,
// getByCpr, whatever specific methods.
public interface IExampleRepository extends IRepository<ExampleObj> {
    ExampleObj get(Integer id);
    void delete(Integer id);
    ExampleObj readExample();
}