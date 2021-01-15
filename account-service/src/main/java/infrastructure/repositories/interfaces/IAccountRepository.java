package infrastructure.repositories.interfaces;

import dto.UserAccount;
import exceptions.account.AccountException;

import java.util.List;

public interface IAccountRepository extends IRepository<UserAccount> {
    void add(UserAccount account);
    void add(String id, String firstName, String lastName, String cprNumber);
    UserAccount getById(String id) throws AccountException;
    UserAccount getByCpr(String cpr) throws AccountException;
    List<UserAccount> getAll();
}