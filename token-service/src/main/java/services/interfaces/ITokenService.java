package services.interfaces;

import domain.CustomerToken;
import domain.Token;
import exceptions.*;

public interface ITokenService {
    void registerCustomer(String customerId) throws CustomerAlreadyRegisteredException;
    String requestTokens(String customerId, int amount) throws CustomerNotFoundException, TooManyTokensException, CustomerAlreadyRegisteredException;
    Token getToken(String customerId) throws CustomerNotFoundException, CustomerHasNoTokensException;
    CustomerToken getCustomerFromToken(String tokenId) throws TokenNotFoundException, CustomerNotFoundException;
    Token validateToken(String tokenId) throws CustomerNotFoundException, TokenNotFoundException, InvalidTokenException;
    String deleteCustomer(String customerId) throws CustomerNotFoundException;
    CustomerToken getCustomer(String customerId) throws CustomerNotFoundException;
    boolean customerExists(String customerId);
}
