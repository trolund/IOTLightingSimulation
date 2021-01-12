package services.interfaces;

import domain.Merchant;
import dto.TransactionDTO;
import exceptions.MerchantException;

public interface IMerchantService {
    void register(Merchant merchant) throws MerchantException;
    Merchant get(String id) throws MerchantException;
    void update(Merchant merchant) throws MerchantException;
    void retire(String id) throws MerchantException;
    void createTransaction(String cId, String mId, Integer amount);
}
