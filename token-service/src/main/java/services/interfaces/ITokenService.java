package services.interfaces;

import domain.CustomerToken;
import domain.Token;
import exceptions.*;

public interface ITokenService {
    void registerCustomer(String customerId) throws CustomerAlreadyRegisteredException;
    void requestTokens(String customerId, int amount) throws CustomerNotFoundException, TooManyTokensException, CustomerAlreadyRegisteredException;
    Token getToken(String customerId) throws CustomerNotFoundException, CustomerHasNoTokensException;
    CustomerToken getCustomerFromToken(String tokenId) throws TokenNotFoundException, CustomerNotFoundException;
    void invalidateToken(String tokenId) throws CustomerNotFoundException, TokenNotFoundException;
    void deleteCustomer(String customerId) throws CustomerNotFoundException;
    CustomerToken getCustomer(String customerId) throws CustomerNotFoundException;

    boolean customerExists(String customerId);
}
