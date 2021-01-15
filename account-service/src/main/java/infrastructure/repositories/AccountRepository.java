package infrastructure.repositories;

import dto.UserAccount;
import exceptions.account.AccountException;
import infrastructure.repositories.interfaces.IAccountRepository;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class AccountRepository implements IAccountRepository {

    private final List<UserAccount> accounts;

    public AccountRepository() {
        accounts = new ArrayList<>();
    }

    @Override
    public void add(UserAccount account) {
        accounts.add(account);
    }

    @Override
    public void add(String id, String firstName, String lastName,
            String cprNumber) {
        accounts.add(new UserAccount(id, firstName, lastName, cprNumber));
    }

    @Override
    public UserAccount getById(String id) throws AccountException {
        UserAccount account = accounts.stream()
                                      .filter(a -> a.getId().equals(id))
                                      .findAny()
                                      .orElse(null);

        if (account == null) {
            throw new AccountException("Account with id: " + id + " not found");
        }

        return account;
    }

    @Override
    public UserAccount getByCpr(String cpr) throws AccountException {
        UserAccount account = accounts.stream()
                                      .filter(a -> a.getCprNumber().equals(cpr))
                                      .findAny()
                                      .orElse(null);

        if (account == null) {
            throw new AccountException("Account with cpr: " + cpr + " not found");
        }

        return account;
    }

    @Override
    public List<UserAccount> getAll() {
        return accounts;
    }
}
