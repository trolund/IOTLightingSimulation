package services;

import dto.BankAccount;
import dto.UserAccount;
import exceptions.account.AccountException;
import exceptions.account.RemoteAccountDoesNotExistException;
import exceptions.account.RemoteAccountExistsException;
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
    public void add(UserAccount ua) throws Exception {
        try {
            getAccountFromBank(ua);
        } catch (RemoteAccountDoesNotExistException e1) {
            try {
                createAccountAtBank(ua, ua.getBankAccount().getBalance());
            } catch (RemoteAccountExistsException e2) {
                throw e2;
            }
        }

        repo.add(ua);
    }

    @Override
    public UserAccount getById(String id) throws AccountException {
        return repo.getById(id);
    }

    @Override
    public UserAccount getByCpr(String cpr) throws AccountException {
        return repo.getByCpr(cpr);
    }

    @Override
    public List<UserAccount> getAll() {
        return repo.getAll();
    }

    public void retireAccount(UserAccount ua)
            throws RemoteAccountDoesNotExistException {

        try {
            bs.retireAccount(ua.getBankAccount().getBankId());
        } catch (Exception e) {
            throw new RemoteAccountDoesNotExistException();
        }
    }

    private void getAccountFromBank(UserAccount ua)
            throws RemoteAccountDoesNotExistException {

        User user = new User();
        user.setCprNumber(ua.getCprNumber());
        user.setFirstName(ua.getFirstName());
        user.setLastName(ua.getLastName());

        try {
            Account account = bs.getAccountByCprNumber(user.getCprNumber());
            ua.setBankAccount(new BankAccount(account));
        } catch (Exception e) {
            throw new RemoteAccountDoesNotExistException();
        }
    }

    private void createAccountAtBank(UserAccount ua, BigDecimal initialBalance)
            throws RemoteAccountExistsException {

        User user = new User();
        user.setCprNumber(ua.getCprNumber());
        user.setFirstName(ua.getFirstName());
        user.setLastName(ua.getLastName());

        // try to create a new account
        try {
            String bankId = bs.createAccountWithBalance(user, initialBalance);
            ua.setBankAccount(new BankAccount(bankId, initialBalance));
        } catch (Exception e) {
            throw new RemoteAccountExistsException();
        }
    }
}
