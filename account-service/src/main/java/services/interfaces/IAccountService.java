package services.interfaces;

import domain.UserAccount;
import exceptions.DuplicateException;
import exceptions.EmptyCprException;
import exceptions.EmptyNameException;
import exceptions.MissingIdException;

import java.util.List;

public interface IAccountService {
    void add(UserAccount account);
    UserAccount getById(String id);
    UserAccount getByCpr(String cpr);
    List<UserAccount> getAll();
}
