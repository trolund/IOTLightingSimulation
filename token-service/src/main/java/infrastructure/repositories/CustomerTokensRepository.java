package infrastructure.repositories;

import dto.CustomerTokens;
import dto.Token;
import exceptions.*;
import exceptions.token.InvalidTokenException;
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
    public void add(CustomerTokens customerToken) throws CustomerAlreadyRegisteredException {
        if (customerTokens.stream().noneMatch(obj -> obj.getCustomerId().equals(customerToken.getCustomerId()))) {
            this.customerTokens.add(customerToken);
        } else {
            throw new CustomerAlreadyRegisteredException(customerToken.getCustomerId());
        }
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

    @Override
    public CustomerTokens getCustomerWithTokenId(String tokenId) throws TokenNotFoundException {
        CustomerTokens customerToken = this.customerTokens.stream()
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
    public Token validateTokenFromCustomer(String tokenId) throws TokenNotFoundException, InvalidTokenException {
        return getCustomerWithTokenId(tokenId).validateToken(tokenId);
    }

    @Override
    public Token getTokenFromCustomer(String customerId) throws CustomerNotFoundException, CustomerHasNoTokensException {
        if (!get(customerId).getTokens().isEmpty()) {
            return get(customerId).getTokens().get(0);
        } else {
            throw new CustomerHasNoTokensException(customerId);
        }
    }

}