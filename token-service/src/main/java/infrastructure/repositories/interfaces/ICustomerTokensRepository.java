package infrastructure.repositories.interfaces;

import domain.CustomerToken;
import domain.Token;
import exceptions.CustomerAlreadyRegisteredException;
import exceptions.CustomerHasNoTokensException;
import exceptions.CustomerNotFoundException;
import exceptions.TokenNotFoundException;
import exceptions.token.InvalidTokenException;

public interface ICustomerTokensRepository extends IRepository<CustomerToken> {
    void add(CustomerToken obj) throws CustomerAlreadyRegisteredException;
    CustomerToken get(String id) throws CustomerNotFoundException;
    CustomerToken getCustomerWithTokenId(String tokenId) throws TokenNotFoundException, CustomerNotFoundException;
    void deleteCustomer(String id) throws CustomerNotFoundException;
    Token validateTokenFromCustomer(String tokenId) throws TokenNotFoundException, CustomerNotFoundException, InvalidTokenException;
    Token getTokenFromCustomer(String customerId) throws CustomerNotFoundException, CustomerHasNoTokensException;
}