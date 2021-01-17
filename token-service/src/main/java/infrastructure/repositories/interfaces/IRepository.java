package infrastructure.repositories.interfaces;

import exceptions.CustomerAlreadyRegisteredException;

public interface IRepository<T> {
    void add(T obj) throws CustomerAlreadyRegisteredException;
}