package infrastructure.repositories;

import dto.TransactionDTO;
import exceptions.transaction.TransactionException;
import infrastructure.repositories.interfaces.ITransactionRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Singleton;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Singleton
public class TransactionRepository implements ITransactionRepository {

    private final List<TransactionDTO> transactions;

    public TransactionRepository() {
        transactions = new ArrayList<>();

        // test data
        TransactionDTO dto = new TransactionDTO(BigDecimal.valueOf(100), BigDecimal.valueOf(1000), "1234", "2345", "test", new Date(), true);
        add(dto);
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