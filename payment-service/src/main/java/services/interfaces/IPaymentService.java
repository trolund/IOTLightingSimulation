package services.interfaces;

import exceptions.TransactionException;
import exceptions.customer.CustomerException;
import exceptions.merchant.MerchantException;
import infrastructure.bank.Transaction;

import java.util.List;

public interface IPaymentService {
    void createTransaction(String mid, String cid, int amount) throws TransactionException, CustomerException, MerchantException;
    List<Transaction> getTransactions(String id) throws CustomerException;
}