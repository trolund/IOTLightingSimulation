package infrastructure.repositories.interfaces;

import domain.CustomerTokens;
import domain.Token;
import exceptions.CustomerNotFoundException;
import exceptions.TokenNotFoundException;

public interface ICustomerTokensRepository extends IRepository<CustomerTokens> {
    void add(CustomerTokens obj);
    CustomerTokens get(String id) throws CustomerNotFoundException;
    CustomerTokens getCustomerWithTokenId(String tokenId) throws TokenNotFoundException, CustomerNotFoundException;
    void deleteCustomer(String id) throws CustomerNotFoundException;

    void invalidateTokenFromCustomer(String tokenId) throws TokenNotFoundException, CustomerNotFoundException;

    Token getTokenFromCustomer(String customerId) throws CustomerNotFoundException;
}