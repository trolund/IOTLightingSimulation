package services.interfaces;

import dto.TransactionDTO;
import exceptions.account.AccountException;
import exceptions.customer.CustomerException;
import exceptions.merchant.MerchantException;
import exceptions.token.InvalidTokenException;
import exceptions.transaction.TransactionException;
import infrastructure.bank.Transaction;

import java.util.List;

/**
 * @author Troels (s161791)
 * @co-author Daniel (s151641)
 */
public interface IPaymentService {
    void processPayment(String customerId, String merchantId, int amount, String token) throws TransactionException, CustomerException, MerchantException, InvalidTokenException;
    List<TransactionDTO> getTransactions(String accountId) throws CustomerException, AccountException;
    Transaction getLatestTransaction(String accountId) throws CustomerException, AccountException;
    void refund(String customerId, String merchantId, int amount, String token) throws CustomerException, MerchantException, TransactionException, InvalidTokenException;
}

