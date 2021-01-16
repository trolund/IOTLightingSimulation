package services.interfaces;

import dto.PaymentRequest;
import exceptions.account.AccountNotFoundException;
import exceptions.token.InvalidTokenException;
import exceptions.transaction.TransactionException;

/**
 * @author Troels (s161791)
 * @co-author Daniel (s151641)
 */
public interface IPaymentService {
    void processPayment(PaymentRequest paymentRequest) throws AccountNotFoundException, TransactionException, InvalidTokenException;
    void refund(PaymentRequest paymentRequest) throws TransactionException, InvalidTokenException, AccountNotFoundException;
}