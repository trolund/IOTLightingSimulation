package services;

import domain.BankAccount;
import domain.UserAccount;
import infrastructure.bank.Account;
import infrastructure.bank.BankService;
import infrastructure.bank.BankServiceService;
import infrastructure.bank.User;
import infrastructure.repositories.AccountRepository;
import services.interfaces.IAccountService;

import javax.enterprise.context.ApplicationScoped;
import java.math.BigDecimal;
import java.util.List;

@ApplicationScoped
public class AccountService implements IAccountService {

    BankService bs = new BankServiceService().getBankServicePort();
    AccountRepository repo = new AccountRepository();

    @Override
    public void add(UserAccount userAccount) {
        createAtBank(userAccount);
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

    public void createAtBank(UserAccount userAccount) {
        // create a Bank User object from the userAccount
        User user = new User();
        user.setCprNumber(userAccount.getCprNumber());
        user.setFirstName(userAccount.getFirstName());
        user.setLastName(userAccount.getLastName());

        // try to create a new account
        try {
            bs.createAccountWithBalance(user, userAccount.getBankAccount().getBalance());
            Account account = bs.getAccountByCprNumber(user.getCprNumber());
            BankAccount bankAccount = new BankAccount(account);
            userAccount.setBankAccount(bankAccount);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
