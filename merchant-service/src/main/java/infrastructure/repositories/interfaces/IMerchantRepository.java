package infrastructure.repositories.interfaces;

import domain.Merchant;
import exceptions.MerchantException;

public interface IMerchantRepository extends IRepository<Merchant> {
    Merchant get(String id) throws MerchantException;
    void delete(String id) throws MerchantException;
}