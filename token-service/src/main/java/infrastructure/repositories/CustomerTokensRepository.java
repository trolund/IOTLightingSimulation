package infrastructure.repositories;

import domain.CustomerTokens;
import exceptions.CustomerNotFoundException;
import exceptions.TokenNotFoundException;
import infrastructure.repositories.interfaces.ICustomerTokensRepository;

import javax.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class CustomerTokensRepository implements ICustomerTokensRepository {

    // This class should probably be a singleton class (?)
    // or maybe this does not matter because dependency injection?

    private final List<CustomerTokens> customerTokens;

    public CustomerTokensRepository() {
        customerTokens = new ArrayList<>();
    }

    @Override
    public void add(CustomerTokens obj) {
        customerTokens.add(obj);
    }

    @Override
    public CustomerTokens get(String id) throws CustomerNotFoundException {
        CustomerTokens customerTokens = this.customerTokens.stream()
                .filter(obj -> obj.getId().equals(id))
                .findAny()
                .orElse(null);

        if (customerTokens == null) {
            throw new CustomerNotFoundException(id);
        }

        return customerTokens;
    }

    public CustomerTokens getCustomerWithTokenId(String tokenId) throws TokenNotFoundException {
        CustomerTokens customerTokens = this.customerTokens.stream()
                .filter(obj -> obj.findTokenInList(tokenId))
                .findAny()
                .orElse(null);

        if (customerTokens == null) {
            throw new TokenNotFoundException(tokenId);
        }

        return customerTokens;
    }

    @Override
    public List<CustomerTokens> getAll() {
        return customerTokens;
    }

    @Override
    public void update(CustomerTokens obj) throws CustomerNotFoundException {
        delete(obj.getId());
        add(obj);
    }

    @Override
    public void delete(String id) throws CustomerNotFoundException{
        customerTokens.remove(get(id));
    }

    @Override
    public CustomerTokens readExample() {
        return new CustomerTokens("101");
    }

}