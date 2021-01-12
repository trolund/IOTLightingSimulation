package infrastructure.repositories;

import domain.AccountObj;
import infrastructure.repositories.interfaces.IAccountRepository;

import javax.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class AccountRepository implements IAccountRepository {

    // This class should probably be a singleton class (?)
    // or maybe this does not matter because dependency injection?

    private final List<AccountObj> exampleObjs;

    public AccountRepository() {
        exampleObjs = new ArrayList<>();
    }

    @Override
    public void add(AccountObj obj) {
        exampleObjs.add(obj);
    }

    @Override
    public AccountObj get(Integer id) {
        AccountObj exampleObj = exampleObjs.stream()
                .filter(obj -> obj.getId().equals(id))
                .findAny()
                .orElse(null);

        if (exampleObj == null) {
            // throw exception if null
        }

        return exampleObj;
    }

    @Override
    public List<AccountObj> getAll() {
        return exampleObjs;
    }

    @Override
    public void update(AccountObj obj) {
        delete(obj.getId());
        add(obj);
    }

    @Override
    public void delete(Integer id) {
        exampleObjs.remove(get(id));
    }

    @Override
    public AccountObj readAccount() {
        return new AccountObj(101, "Account obj");
    }

}
