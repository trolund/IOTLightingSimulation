package infrastructure.repositories.interfaces;

import java.util.List;

// Simple and basic repository that provides some CRUD functionality.
public interface IRepository<T> {
    void add(T obj);
    List<T> getAll();
    //void update(T obj);
}
