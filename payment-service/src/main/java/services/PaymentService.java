package services;

import exceptions.TransactionException;
import exceptions.UserNotFoundException;
import infrastructure.bank.Account;
import infrastructure.bank.BankService;
import infrastructure.bank.BankServiceService;
import infrastructure.bank.Transaction;
import org.modelmapper.ModelMapper;

import javax.inject.Inject;
import javax.ws.rs.NotFoundException;
import java.math.BigDecimal;
import java.util.List;

public class PaymentService {

    BankService bs = new BankServiceService().getBankServicePort();

    @Inject
    ModelMapper mapper;

    public void createTransaction(String mid, String cid, int amount) throws UserNotFoundException, TransactionException {

        Account m = null;
        Account c = null;

        try{
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

        }catch (Exception e) {
            if(m == null) {
                throw new UserNotFoundException("merchant with id " + mid + " is unknown");
            }
            if(c == null){
                throw new UserNotFoundException("customer with id " + cid + " is unknown");
            }
            throw new TransactionException(e.getMessage());
        }

    }

    public List<Transaction> getTransactions(String id) throws UserNotFoundException {
        try {
            return bs.getAccount(id).getTransactions();
        }catch (Exception e){
            throw new UserNotFoundException("Account not found");
        }
    }

}
