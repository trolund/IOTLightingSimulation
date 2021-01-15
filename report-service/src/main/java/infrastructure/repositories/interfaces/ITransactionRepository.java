package infrastructure.repositories.interfaces;

import domain.ExampleObj;
import dto.TransactionDTO;
import exceptions.TransactionException;

// Specific to "Example" entity.
// These methods should be specific to the given entity. For example,
// getByCpr, whatever specific methods.
public interface ITransactionRepository extends IRepository<ExampleObj> {
    void add(TransactionDTO obj);

    TransactionDTO get(String tokenId) throws TransactionException;

    void update(TransactionDTO transaction) throws TransactionException;

    void delete(String tokenId) throws TransactionException;

}