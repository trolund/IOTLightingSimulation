package infrastructure.repositories.interfaces;

import dto.UserAccount;

import java.util.List;

public interface IAccountRepository extends IRepository<UserAccount> {
    void add(UserAccount account);
    void add(String id, String firstName, String lastName, String cprNumber);
    UserAccount getById(String id);
    UserAccount getByCpr(String cpr);
    List<UserAccount> getAll();
}
