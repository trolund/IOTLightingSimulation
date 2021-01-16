package infrastructure.repositories.interfaces;

import exceptions.account.AccountException;
import model.UserAccount;

public interface IAccountRepository extends IRepository<UserAccount> {
    UserAccount getById(String id) throws AccountException;
    UserAccount getByCpr(String cpr) throws AccountException;
    void remove(String id) throws AccountException;
}