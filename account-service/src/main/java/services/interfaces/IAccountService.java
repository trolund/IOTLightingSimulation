package services.interfaces;

import domain.UserAccount;
import java.util.List;

public interface IAccountService {
    void add(UserAccount account);
    UserAccount getById(String id);
    UserAccount getByCpr(String cpr);
    List<UserAccount> getAll();
}
