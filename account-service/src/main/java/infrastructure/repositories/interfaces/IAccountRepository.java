package infrastructure.repositories.interfaces;

import dto.UserAccount;
import exceptions.account.AccountException;

public interface IAccountRepository extends IRepository<UserAccount> {
    UserAccount getById(String id) throws AccountException;
    UserAccount getByCpr(String cpr) throws AccountException;
    void remove(String id) throws AccountException;
}