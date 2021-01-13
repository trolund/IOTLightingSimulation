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

    private final List<CustomerTokens> customerTokens;

    public CustomerTokensRepository() {
        customerTokens = new ArrayList<>();
    }

    @Override
    public void add(CustomerTokens customerTokens) {
        this.customerTokens.add(customerTokens);
    }

    @Override
    public CustomerTokens get(String customerId) throws CustomerNotFoundException {
        CustomerTokens customerTokens = this.customerTokens.stream()
                .filter(obj -> obj.getCustomerId().equals(customerId))
                .findAny()
                .orElse(null);

        if (customerTokens == null) {
            throw new CustomerNotFoundException(customerId);
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
    public void update(CustomerTokens customerTokens) throws CustomerNotFoundException {
        delete(customerTokens.getCustomerId());
        add(customerTokens);
    }

    @Override
    public void delete(String customerId) throws CustomerNotFoundException{
        customerTokens.remove(get(customerId));
    }

}