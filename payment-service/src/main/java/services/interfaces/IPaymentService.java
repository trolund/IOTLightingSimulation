package services.interfaces;

import dto.TransactionDTO;
import exceptions.AccountException;
import exceptions.TransactionException;
import exceptions.customer.CustomerException;
import exceptions.merchant.MerchantException;
import infrastructure.bank.Transaction;

import java.util.List;

/**
 * @author Troels (s161791)
 * UserNotFoundException to use when a user cannot be found.
 */

public interface IPaymentService {
    void createTransaction(String mid, String cid, int amount, String token) throws TransactionException, CustomerException, MerchantException;
    List<TransactionDTO> getTransactions(String accountId) throws CustomerException, AccountException;
    Transaction getLatestTransaction(String accountId) throws CustomerException, AccountException;
    void refund(String mid, String cid, int amount) throws CustomerException, MerchantException, TransactionException;
}

