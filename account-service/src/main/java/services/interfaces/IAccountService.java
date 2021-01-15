package services.interfaces;

import domain.UserAccount;
import java.util.List;

import exceptions.*;

public interface IAccountService {
    void add(UserAccount account) throws Exception;
    UserAccount getById(String id);
    UserAccount getByCpr(String cpr);
    List<UserAccount> getAll();
    void retireAccount(UserAccount ua) throws RemoteAccountDoesNotExistException;
}
