package infrastructure.repositories;

import domain.CustomerToken;
import domain.Token;
import exceptions.*;
import exceptions.token.InvalidTokenException;
import infrastructure.repositories.interfaces.ICustomerTokensRepository;

import javax.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class CustomerTokensRepository implements ICustomerTokensRepository {

    private final List<CustomerToken> customerTokens;

    public CustomerTokensRepository() {
        customerTokens = new ArrayList<>();
        testToken();
    }

    private void testToken() {
        // for testing
        CustomerToken testct = new CustomerToken("0c4143e2-aed2-4cb8-bcb8-058ddd0a0929");
        testct.getTokens().add(new Token("42"));
        testct.getTokens().add(new Token("42"));
        testct.getTokens().add(new Token("42"));
        testct.getTokens().add(new Token("42"));
        testct.getTokens().add(new Token("42"));
        customerTokens.add(testct);
    }

    @Override
    public void add(CustomerToken customerToken) throws CustomerAlreadyRegisteredException {
        if (customerTokens.stream().noneMatch(obj -> obj.getCustomerId().equals(customerToken.getCustomerId()))) {
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