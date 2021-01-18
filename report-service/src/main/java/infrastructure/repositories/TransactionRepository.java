package infrastructure.repositories;

import dto.AccountInformation;
import dto.Transaction;
import dto.TransactionDTO;
import exceptions.transaction.TransactionException;
import infrastructure.repositories.interfaces.ITransactionRepository;

import java.util.ArrayList;
import java.util.List;

public class TransactionRepository implements ITransactionRepository {

    private List<TransactionDTO> transactions;

    private static TransactionRepository instance = null;

    private TransactionRepository() {
        transactions = new ArrayList<>();
    }

    public static TransactionRepository getInstance() {
        if (instance == null)
            instance = new TransactionRepository();
        return instance;
    }

    @Override
    public void add(TransactionDTO obj) throws TransactionException {
        if (obj.getCreditor() != null || obj.getDebtor() != null || obj.getAmount() != null) {
            transactions.add(obj);
        } else {
            throw new TransactionException("Minimum transaction information not found");
        }
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

    @Override
    public void dropEverything() {
        transactions = new ArrayList<>();
    }



}