package services.interfaces;

import dto.UserAccount;
import exceptions.*;

import java.util.List;

public interface IAccountService {
    void add(UserAccount account) throws Exception;
    UserAccount getById(String id);
    UserAccount getByCpr(String cpr);
    List<UserAccount> getAll();
    void retireAccount(UserAccount ua) throws RemoteAccountDoesNotExistException;
}
