package services;

import dto.TransactionDTO;
import exceptions.TransactionException;
import exceptions.customer.CustomerException;
import exceptions.customer.CustomerNotFoundException;
import exceptions.merchant.MerchantException;
import exceptions.merchant.MerchantNotFoundException;
import infrastructure.bank.Account;
import infrastructure.bank.BankService;
import infrastructure.bank.IBankService;
import infrastructure.bank.Transaction;
import services.interfaces.IPaymentService;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

/**
 * @author Troels (s161791)
 * UserNotFoundException to use when a user cannot be found.
 */

@ApplicationScoped
public class PaymentService implements IPaymentService {

    IBankService bs;
    MapperService mapper;

    @Inject
    public PaymentService(BankService bs, MapperService mapper) {
        this.bs = bs.getBankServicePort();
        this.mapper = mapper;
    }

    public void createTransaction(String mid, String cid, int amount) throws CustomerException, MerchantException, TransactionException {
        Account m = null;
        Account c = null;

        try {
            m = bs.getAccount(mid);
            c = bs.getAccount(cid);

            bs.transferMoneyFromTo(
                    m.getId(),
                    c.getId(),
                    BigDecimal.valueOf(amount),
                    "Transaction");

        } catch (Exception e) {
            if (m == null) {
                throw new MerchantNotFoundException("merchant with id " + mid + " is not found!");
            }
            if (c == null) {
                throw new CustomerNotFoundException("customer with id " + cid + " is not found!");
            }
            throw new TransactionException(e.getMessage());
        }
    }

    public void refund(String mid, String cid, int amount) throws CustomerException, MerchantException, TransactionException {
        createTransaction(cid, mid, amount);
    }

    public List<TransactionDTO> getTransactions(String id) throws CustomerException {
        try {
            return mapper.mapList(bs.getAccount(id).getTransactions(), TransactionDTO.class);
        } catch (Exception e) {
            throw new CustomerException("Account not found");
        }
    }

    public Transaction getLatestTransaction(String id) throws CustomerException {
        try {
            Comparator<Transaction> comparator = (p1, p2) -> p1.getTime().compare(p2.getTime());
            return bs.getAccount(id).getTransactions().stream().max(comparator).get();
        } catch (Exception e) {
            throw new CustomerException("Account not found");
        }
    }

}