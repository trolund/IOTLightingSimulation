package infrastructure.repositories;

import dto.TransactionDTO;
import exceptions.TransactionException;
import infrastructure.repositories.interfaces.ITransactionRepository;

import javax.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class TransactionRepository implements ITransactionRepository {

    // This class should probably be a singleton class (?)
    // or maybe this does not matter because dependency injection?

    private final List<TransactionDTO> transactions;

    public TransactionRepository() {
        transactions = new ArrayList<>();
    }

    @Override
    public void add(TransactionDTO obj) {
        transactions.add(obj);
    }

    @Override
    public TransactionDTO get(String tokenId) throws TransactionException {
        TransactionDTO transaction = transactions.stream()
                .filter(obj -> obj.getToken().equals(tokenId))
                .findAny()
                .orElse(null);

        if (transaction == null) {
            throw new TransactionException("Transactions with tokenid " + tokenId + " was not found");
        }

        return transaction;
    }

    @Override
    public List<TransactionDTO> getAll() {
        return transactions;
    }

    @Override
    public void update(TransactionDTO transaction) throws TransactionException {
        delete(transaction.getToken());
        add(transaction);
    }

    @Override
    public void delete(String tokenId) throws TransactionException {
        transactions.remove(get(tokenId));
    }

}