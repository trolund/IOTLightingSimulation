package services.interfaces;

import dto.UserAccount;
import exceptions.account.AccountException;
import exceptions.account.RemoteAccountDoesNotExistException;

import java.util.List;

public interface IAccountService {
    String add(UserAccount account) throws Exception;
    UserAccount getById(String id) throws AccountException;
    UserAccount getByCpr(String cpr) throws AccountException;
    List<UserAccount> getAll();
    void retireAccount(UserAccount ua) throws RemoteAccountDoesNotExistException;
}