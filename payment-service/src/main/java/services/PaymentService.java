package services;

import dto.TransactionDTO;
import exceptions.AccountException;
import exceptions.TokenNotValidException;
import exceptions.TransactionException;
import exceptions.customer.CustomerException;
import exceptions.customer.CustomerNotFoundException;
import exceptions.merchant.MerchantException;
import exceptions.merchant.MerchantNotFoundException;
import infrastructure.bank.Account;
import infrastructure.bank.BankService;
import infrastructure.bank.Transaction;
import interfaces.rabbitmq.token.RabbitMQTokenAdapterFactory;
import services.interfaces.IPaymentService;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

/**
 * @primary-author Troels (s161791)
 * @co-author Daniel (s151641)
 *
 * Payment microservice REST resource.
 */
@ApplicationScoped
public class PaymentService implements IPaymentService {

    BankService bs;
    MapperService mapper;

    @Inject
    public PaymentService(Bank bank, MapperService mapper) {
        this.bs = bank.getBankServicePort();
        this.mapper = mapper;
    }

    public void createTransaction(String mId, String cId, int amount, String token) throws CustomerException, MerchantException, TransactionException {
        Account merchant = null;
        Account customer = null;

        try {

            // Checks if a token is valid
            TokenEventService service = new RabbitMQTokenAdapterFactory().getService();
            if (!service.validateToken(token)){
                throw new TokenNotValidException("The token: " + token + " is not valid" );
            }

            // TODO get with account service
            merchant = bs.getAccount(mId);
            customer = bs.getAccount(cId);

            bs.transferMoneyFromTo(
                    merchant.getId(),
                    customer.getId(),
                    BigDecimal.valueOf(amount),
                    "Transaction between Customer (" + cId + ") and Merchant (" + mId + ") for amount " + amount + ", with token: " + token);

        } catch (Exception e) {
            if (merchant == null) {
                throw new MerchantNotFoundException("Merchant (" + mId + ") is not found!");
            }
            if (customer == null) {
                throw new CustomerNotFoundException("Customer (" + cId + ") is not found!");
            }
            throw new TransactionException(e.getMessage());
        }
    }

    public void refund(String mid, String cid, int amount) throws CustomerException, MerchantException, TransactionException {
        createTransaction(cid, mid, amount, "");
    }

    public List<TransactionDTO> getTransactions(String accountId) throws AccountException {
        try {
            return mapper.mapList(bs.getAccount(accountId).getTransactions(), TransactionDTO.class);
        } catch (Exception e) {
            throw new AccountException("User (" + accountId + ") is not found!");
        }
    }

    public Transaction getLatestTransaction(String id) throws AccountException {
        try {
            Comparator<Transaction> comparator = (p1, p2) -> p1.getTime().compare(p2.getTime());
            return bs.getAccount(id).getTransactions().stream().max(comparator).get();
        } catch (Exception e) {
            throw new AccountException("User (" + id + ") is not found!");
        }
    }

}