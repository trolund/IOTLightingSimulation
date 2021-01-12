package services.interfaces;

import dto.TransactionDTO;
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
    void createTransaction(String mid, String cid, int amount) throws TransactionException, CustomerException, MerchantException;
    List<TransactionDTO> getTransactions(String id) throws CustomerException;
    Transaction getLatestTransaction(String id) throws CustomerException;
    void refund(String mid, String cid, int amount) throws CustomerException, MerchantException, TransactionException;
}

