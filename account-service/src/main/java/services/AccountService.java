package services;

import dto.BankAccount;
import dto.UserAccount;
import exceptions.account.AccountException;
import exceptions.account.RemoteAccountDoesNotExistException;
import exceptions.account.RemoteAccountExistsException;
import infrastructure.bank.BankService;
import infrastructure.bank.BankServiceService;
import infrastructure.bank.User;
import infrastructure.repositories.interfaces.IAccountRepository;
import services.interfaces.IAccountService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class AccountService implements IAccountService {

    BankService bs = new BankServiceService().getBankServicePort();

    @Inject
    IAccountRepository repo;

    @Override
    public String add(UserAccount ua) throws Exception {
        try {
            return checkIfAccountExists(ua);
        } catch (RemoteAccountDoesNotExistException e1) {
            return createAccount(ua);
        }
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

    @Override
    public void retireAccount(UserAccount ua)
            throws RemoteAccountDoesNotExistException {
        try {
            bs.retireAccount(ua.getBankAccount().getBankId());
            repo.remove(ua.getId());
        } catch (Exception e) {
            throw new RemoteAccountDoesNotExistException();
        }
    }

    private String checkIfAccountExists(UserAccount ua)
            throws RemoteAccountDoesNotExistException {
        try {
            bs.getAccountByCprNumber(ua.getCprNumber());
            return repo.getByCpr(ua.getCprNumber()).getId();
        } catch (Exception e) {
            throw new RemoteAccountDoesNotExistException();
        }
    }

    private String createAccount(UserAccount ua)
            throws RemoteAccountExistsException {
        BigDecimal initialBalance = ua.getBankAccount().getBalance();

        User user = new User();
        user.setCprNumber(ua.getCprNumber());
        user.setFirstName(ua.getFirstName());
        user.setLastName(ua.getLastName());

        // try to create a new account
        try {
            String bankId = bs.createAccountWithBalance(user, initialBalance);
            ua.setBankAccount(new BankAccount(bankId, initialBalance));
            String internalId = String.valueOf(UUID.randomUUID());
            ua.setId(internalId);
            repo.add(ua);
            return internalId;
        } catch (Exception e) {
            throw new RemoteAccountExistsException();
        }
    }

}