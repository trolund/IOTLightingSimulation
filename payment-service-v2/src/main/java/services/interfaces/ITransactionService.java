package services.interfaces;

import dto.Transaction;
import dto.TransactionDTO;
import exceptions.account.AccountNotFoundException;
import exceptions.transaction.TransactionNotFoundException;

import java.util.List;

public interface ITransactionService {
    List<TransactionDTO> getTransactions(String accountId) throws AccountNotFoundException;
    TransactionDTO getLatestTransaction(String accountId) throws AccountNotFoundException, TransactionNotFoundException;
}
