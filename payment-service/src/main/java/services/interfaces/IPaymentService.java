package services.interfaces;

import exceptions.TransactionException;
import exceptions.UserNotFoundException;
import infrastructure.bank.Transaction;

import java.util.List;

public interface IPaymentService {
    void createTransaction(String mid, String cid, int amount) throws UserNotFoundException, TransactionException;
    List<Transaction> getTransactions(String id) throws UserNotFoundException;
}
