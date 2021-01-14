package infrastructure.repositories;

import domain.UserAccount;
import infrastructure.repositories.interfaces.IAccountRepository;

import javax.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
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
    public void add(String firstName, String lastName, String cprNumber) {
        accounts.add(new UserAccount(firstName, lastName, cprNumber));
    }

    @Override
    public UserAccount getById(String id) {
        UserAccount account = accounts.stream()
                                      .filter(a -> a.getId().equals(id))
                                      .findAny()
                                      .orElse(null);

        if (account == null) {
            // throw exception if null
        }

        return account;
    }

    @Override
    public UserAccount getByCpr(String cpr) {
        UserAccount account = accounts.stream()
                                      .filter(a -> a.getCprNumber().equals(cpr))
                                      .findAny()
                                      .orElse(null);

        if (account == null) {
            // throw exception if null
        }

        return account;
    }

    @Override
    public List<UserAccount> getAll() {
        return accounts;
    }
}
