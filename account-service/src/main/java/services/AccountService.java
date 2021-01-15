package services;

import domain.UserAccount;
import domain.BankAccount;
import infrastructure.repositories.interfaces.IAccountRepository;
import infrastructure.repositories.AccountRepository;
import infrastructure.bank.*;
import services.interfaces.IAccountService;

import org.modelmapper.ModelMapper;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import java.util.List;
import java.math.BigDecimal;

@ApplicationScoped
public class AccountService implements IAccountService {


    BankService bs = new BankServiceService().getBankServicePort();
    AccountRepository repo = new AccountRepository();
    
    @Override
    public void add(UserAccount userAccount, BigDecimal balance) {
        createAtBank(userAccount, balance);
        repo.add(userAccount);
    }

    @Override
    public UserAccount getById(String id) {
        return repo.getById(id);
    }

    @Override
    public UserAccount getByCpr(String cpr) {
        return repo.getByCpr(cpr);
    }

    @Override
    public List<UserAccount> getAll() {
        return repo.getAll();
    }

    public void createAtBank(UserAccount userAccount, BigDecimal balance) {
        // create a Bank User object from the userAccount
        User user = new User();
        user.setCprNumber(userAccount.getCprNumber());
        user.setFirstName(userAccount.getFirstName());
        user.setLastName(userAccount.getLastName());
        
        // try to create a new account
        try {
            bs.createAccountWithBalance(user, balance);
            Account account = bs.getAccountByCprNumber(user.getCprNumber());
            BankAccount bankAccount = new BankAccount(account);
            userAccount.setBankAccount(bankAccount);
        } catch (Exception e) {
            e.getMessage();
        }
    }
}
