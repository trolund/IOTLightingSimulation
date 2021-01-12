package services;

import domain.Merchant;
import dto.TransactionDTO;
import exceptions.MerchantException;
import infrastructure.repositories.interfaces.IMerchantRepository;
import interfaces.rabbitmq.RabbitMQAdapter;
import services.interfaces.IMerchantService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.math.BigDecimal;

@ApplicationScoped
public class MerchantService implements IMerchantService {

    @Inject
    IMerchantRepository repo;

    @Inject
    RabbitMQAdapter rabbitMQAdapter;

    @Override
    public void register(Merchant merchant) throws MerchantException {
        try {
            repo.add(merchant);
        } catch (Exception e) {
            throw new MerchantException(e.getMessage());
        }
    }

    @Override
    public Merchant get(String id) throws MerchantException {
        return repo.get(id);
    }

    @Override
    public void update(Merchant merchant) throws MerchantException {
        try {
            repo.update(merchant);
        } catch (Exception e) {
            throw new MerchantException(e.getMessage());
        }
    }

    @Override
    public void retire(String id) throws MerchantException {
        repo.delete(id);
    }

    @Override
    public void createTransaction(String cId, String mId, Integer amount) {
        TransactionDTO transactionDTO = new TransactionDTO(BigDecimal.valueOf(amount), mId, cId);
        rabbitMQAdapter.createTransactionToQueue(transactionDTO);
    }

}