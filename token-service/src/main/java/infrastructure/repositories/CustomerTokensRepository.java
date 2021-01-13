package infrastructure.repositories;

import domain.CustomerToken;
import domain.Token;
import exceptions.CustomerAlreadyRegisteredException;
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

    private final List<CustomerToken> customerTokens;

    public CustomerTokensRepository() {
        customerTokens = new ArrayList<>();
    }

    @Override
    public void add(CustomerToken customerToken) throws CustomerAlreadyRegisteredException {
        if (!customerTokens.stream().anyMatch(obj -> obj.getCustomerId().equals(customerToken.getCustomerId()))) {
            this.customerTokens.add(customerToken);
        } else {
            throw new CustomerAlreadyRegisteredException(customerToken.getCustomerId());
        }
    }

    @Override
    public CustomerToken get(String customerId) throws CustomerNotFoundException {
        CustomerToken customerToken = this.customerTokens.stream()
                .filter(obj -> obj.getCustomerId().equals(customerId))
                .findAny()
                .orElse(null);

        if (customerToken == null) {
            throw new CustomerNotFoundException(customerId);
        }

        return customerToken;
    }

    @Override
    public CustomerToken getCustomerWithTokenId(String tokenId) throws TokenNotFoundException {
        CustomerToken customerToken = this.customerTokens.stream()
                .filter(obj -> obj.findTokenInList(tokenId))
                .findAny()
                .orElse(null);

        if (customerToken == null) {
            throw new TokenNotFoundException(tokenId);
        }

        return customerToken;
    }

    @Override
    public void deleteCustomer(String id) throws CustomerNotFoundException{
        customerTokens.remove(get(id));
    }

    @Override
    public void invalidateTokenFromCustomer(String tokenId) throws TokenNotFoundException {
        getCustomerWithTokenId(tokenId).invalidateToken(tokenId);
    }

    @Override
    public Token getTokenFromCustomer(String customerId) throws CustomerNotFoundException {
        return get(customerId).getTokens().get(0);
    }
}