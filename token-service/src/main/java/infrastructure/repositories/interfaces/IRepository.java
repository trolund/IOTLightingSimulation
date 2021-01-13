package infrastructure.repositories.interfaces;

import exceptions.CustomerNotFoundException;

public interface IRepository<T> {
    void add(T obj);
    void update(T obj) throws CustomerNotFoundException;
}