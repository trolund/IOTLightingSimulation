package services.interfaces;

import domain.UserAccount;
import exceptions.DuplicateException;
import exceptions.EmptyCprException;
import exceptions.EmptyNameException;
import exceptions.MissingIdException;

import java.util.List;
import java.math.BigDecimal;

public interface IAccountService {
    void add(UserAccount account, BigDecimal balance);
    UserAccount getById(String id);
    UserAccount getByCpr(String cpr);
    List<UserAccount> getAll();
}
