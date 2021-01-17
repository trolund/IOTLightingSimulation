package infrastructure.repositories;

import dto.AccountInformation;
import infrastructure.repositories.interfaces.IAccountRepository;

import javax.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class AccountRepository implements IAccountRepository {

    private final List<AccountInformation> accountInformations;

    public AccountRepository() {
        accountInformations = new ArrayList<>();
    }

    @Override
    public void add(AccountInformation accountInformation) {
        accountInformations.add(accountInformation);
    }

    @Override
    public AccountInformation getById(String id) {
        return accountInformations.stream()
                .filter(a -> a.getId().equals(id))
                .findAny()
                .orElse(null);
    }

    @Override
    public AccountInformation getByCpr(String cpr) {
        return accountInformations.stream()
                .filter(a -> a.getCpr().equals(cpr))
                .findAny()
                .orElse(null);
    }

    @Override
    public void remove(String id) {
        AccountInformation accountInformation = getById(id);
        if (accountInformation != null) {
            accountInformations.remove(accountInformation);
        }
    }

    @Override
    public List<AccountInformation> getAll() {
        return accountInformations;
    }

}