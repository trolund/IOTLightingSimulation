package infrastructure.repositories.interfaces;

import dto.CustomerTokens;
import dto.Token;
import exceptions.CustomerAlreadyRegisteredException;
import exceptions.CustomerHasNoTokensException;
import exceptions.CustomerNotFoundException;
import exceptions.TokenNotFoundException;
import exceptions.token.InvalidTokenException;

public interface ICustomerTokensRepository extends IRepository<CustomerTokens> {
    void add(CustomerTokens obj) throws CustomerAlreadyRegisteredException;
    CustomerTokens get(String id) throws CustomerNotFoundException;
    CustomerTokens getCustomerWithTokenId(String tokenId) throws TokenNotFoundException, CustomerNotFoundException;
    void deleteCustomer(String id) throws CustomerNotFoundException;
    Token validateTokenFromCustomer(String tokenId) throws TokenNotFoundException, CustomerNotFoundException, InvalidTokenException;
    Token getTokenFromCustomer(String customerId) throws CustomerNotFoundException, CustomerHasNoTokensException;
}