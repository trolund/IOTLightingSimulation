package infrastructure.repositories.interfaces;

import domain.CustomerToken;
import domain.Token;
import exceptions.CustomerAlreadyRegisteredException;
import exceptions.CustomerNotFoundException;
import exceptions.TokenNotFoundException;

public interface ICustomerTokensRepository extends IRepository<CustomerToken> {
    void add(CustomerToken obj) throws CustomerAlreadyRegisteredException;
    CustomerToken get(String id) throws CustomerNotFoundException;
    CustomerToken getCustomerWithTokenId(String tokenId) throws TokenNotFoundException, CustomerNotFoundException;
    void deleteCustomer(String id) throws CustomerNotFoundException;

    void invalidateTokenFromCustomer(String tokenId) throws TokenNotFoundException, CustomerNotFoundException;

    Token getTokenFromCustomer(String customerId) throws CustomerNotFoundException;
}