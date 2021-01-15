package services.interfaces;

import dto.TransactionDTO;
import exceptions.AccountException;
import exceptions.TokenNotValidException;
import exceptions.TransactionException;
import exceptions.customer.CustomerException;
import exceptions.merchant.MerchantException;
import infrastructure.bank.Transaction;

import java.util.List;

/**
 * @author Troels (s161791)
 * @co-author Daniel (s151641)
 */
public interface IPaymentService {
    void processPayment(String customerId, String merchantId, int amount, String token) throws TransactionException, CustomerException, MerchantException, TokenNotValidException;
    List<TransactionDTO> getTransactions(String accountId) throws CustomerException, AccountException;
    Transaction getLatestTransaction(String accountId) throws CustomerException, AccountException;
    void refund(String customerId, String merchantId, int amount) throws CustomerException, MerchantException, TransactionException;
}

