package services;

import domain.UserAccount;
import infrastructure.repositories.interfaces.IAccountRepository;
import infrastructure.repositories.AccountRepository;
import services.interfaces.IAccountService;

import org.modelmapper.ModelMapper;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import java.util.List;

@ApplicationScoped
public class AccountService implements IAccountService {

    AccountRepository repo = new AccountRepository();
    
    @Override
    public void add(UserAccount account) {
        repo.add(account);
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
}
