package infrastructure.repositories.interfaces;

import domain.AccountObj;

// Specific to "Account" entity.
// These methods should be specific to the given entity. For example,
// getByCpr, whatever specific methods.
public interface IAccountRepository extends IRepository<AccountObj> {
    AccountObj get(Integer id);
    void delete(Integer id);
    AccountObj readAccount();
}
