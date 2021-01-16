package infrastructure.repositories;

import dto.CustomerTokens;
import dto.Token;
import exceptions.CustomerHasNoTokensException;
import exceptions.CustomerNotFoundException;
import exceptions.TokenNotFoundException;
import infrastructure.repositories.interfaces.ICustomerTokensRepository;
import services.TokenService;

import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Singleton
public class CustomerTokensRepository implements ICustomerTokensRepository {

    private final List<CustomerTokens> customerTokens;

    Logger logger = Logger.getLogger(TokenService.class.getName());

    public CustomerTokensRepository() {
        customerTokens = new ArrayList<>();
    }

    @Override
    public void add(CustomerTokens customerToken) {
        this.customerTokens.add(customerToken);
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
                .filter(obj -> findTokenInList(tokenId, obj))
                .findAny()
                .orElse(null);

        if (customerToken == null) {
            throw new TokenNotFoundException(tokenId);
        }

        return customerToken;
    }

    @Override
    public void deleteCustomer(String id) throws CustomerNotFoundException {
        customerTokens.remove(get(id));
    }

    @Override
    public Token getTokenFromCustomer(String customerId) throws CustomerNotFoundException, CustomerHasNoTokensException {
        if (!get(customerId).getTokens().isEmpty()) {
            logger.severe("get token cus" + get(customerId));
            System.out.println("get token cus" + get(customerId));
            Token t = get(customerId).getTokens().get(0);
            System.out.println("token " + t);
            logger.severe("token " + t);
            return t;
        } else {
            throw new CustomerHasNoTokensException(customerId);
        }
    }

    private boolean findTokenInList(String tokenId, CustomerTokens customerTokens) {
        Token result = customerTokens.getTokens().stream()
                .filter(obj -> obj.getId()
                        .equals(tokenId))
                .findAny()
                .orElse(null);
        return result != null;
    }

}