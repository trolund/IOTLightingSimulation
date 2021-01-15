package infrastructure.repositories.interfaces;

import java.util.List;

/**
 * @primary-author Troels (s161791)
 * @co-author Daniel (s151641)
 */

// Simple and basic repository that provides some CRUD functionality.
public interface IRepository<T> {
    void add(T obj);
    List<T> getAll();
    //void update(T obj);
}
