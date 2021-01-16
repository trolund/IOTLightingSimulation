package infrastructure.repositories.interfaces;

import dto.AccountInformation;

public interface IAccountRepository extends IRepository<AccountInformation> {
    AccountInformation getById(String id);
    AccountInformation getByCpr(String cpr);
    void remove(String id);
}