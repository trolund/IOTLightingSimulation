package services;

import exceptions.TransactionException;
import exceptions.customer.CustomerException;
import exceptions.customer.CustomerNotFoundException;
import exceptions.merchant.MerchantException;
import exceptions.merchant.MerchantNotFoundException;
import infrastructure.bank.Account;
import infrastructure.bank.IBankService;
import infrastructure.bank.BankService;
import infrastructure.bank.Transaction;
import org.modelmapper.ModelMapper;
import services.interfaces.IPaymentService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.List;

@ApplicationScoped
public class PaymentService implements IPaymentService {

    IBankService bs;

    ModelMapper mapper;

    @Inject public PaymentService(BankService bs, ModelMapper mapper) {
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

            Transaction t = new Transaction();
            t.setBalance(c.getBalance());
            t.setAmount(BigDecimal.valueOf(amount));
            t.setDebtor(c.getId());
            t.setCreditor(m.getId());
            t.setDescription("Transaction from " + c.getId() + " to " + m.getId());

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

    public List<Transaction> getTransactions(String id) throws CustomerException {
        try {
            return bs.getAccount(id).getTransactions();
        } catch (Exception e) {
            throw new CustomerException("Account not found");
        }
    }

}