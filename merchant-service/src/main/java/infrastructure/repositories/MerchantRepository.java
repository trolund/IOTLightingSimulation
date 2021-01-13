package infrastructure.repositories;

import domain.Merchant;
import exceptions.MerchantAlreadyExistsException;
import exceptions.MerchantException;
import exceptions.MerchantNotFoundException;
import infrastructure.repositories.interfaces.IMerchantRepository;

import javax.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class MerchantRepository implements IMerchantRepository {

    private final List<Merchant> merchants;

    public MerchantRepository() {
        merchants = new ArrayList<>();
    }

    @Override
    public void add(Merchant merchant) throws MerchantException {
        Merchant obj = merchants.stream()
                .filter(m -> m.getId().equals(merchant.getId()))
                .findAny()
                .orElse(null);

        if (obj != null) {
            throw new MerchantAlreadyExistsException("Merchant (" + merchant.getId() + ") already exists!");
        }

        merchants.add(merchant);
    }

    @Override
    public Merchant get(String id) throws MerchantException {
        Merchant merchant = merchants.stream()
                .filter(obj -> obj.getId().equals(id))
                .findAny()
                .orElse(null);

        if (merchant == null) {
            throw new MerchantNotFoundException("Merchant (" + id + ") not found!");
        }

        return merchant;
    }

    @Override
    public List<Merchant> getAll() {
        return merchants;
    }

    @Override
    public void update(Merchant merchant) throws MerchantException {
        get(merchant.getId());
        delete(merchant.getId());
        add(merchant);
    }

    @Override
    public void delete(String id) throws MerchantException {
        merchants.remove(get(id));
    }

}