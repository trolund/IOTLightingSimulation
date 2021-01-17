package infrastructure.repositories.interfaces;

import dto.TransactionDTO;

import java.util.List;

// Simple and basic repository that provides some CRUD functionality.
public interface IRepository<T> {
    List<TransactionDTO> getAll();
}
