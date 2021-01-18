package infrastructure.repositories.interfaces;

import dto.TransactionDTO;
import exceptions.transaction.TransactionException;

public interface ITransactionRepository extends IRepository<TransactionDTO> {
    void add(TransactionDTO obj) throws TransactionException;

    TransactionDTO get(String tokenId) throws TransactionException;

    void update(TransactionDTO transaction) throws TransactionException;

    void delete(String tokenId) throws TransactionException;

    void dropEverything();
}