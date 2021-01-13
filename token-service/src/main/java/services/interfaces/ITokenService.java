package services.interfaces;

import domain.CustomerTokens;
import domain.Token;
import exceptions.CustomerAlreadyRegisteredException;
import exceptions.CustomerNotFoundException;
import exceptions.TokenNotFoundException;
import exceptions.TooManyTokensException;

import java.util.List;

public interface ITokenService {
    void registerCustomer(String customerId) throws CustomerAlreadyRegisteredException;
    List<Token> requestTokens(String customerId, int amount) throws CustomerNotFoundException, TooManyTokensException;
    CustomerTokens getTokens(String customerId) throws CustomerNotFoundException;
    CustomerTokens getCustomerFromToken(String tokenId) throws TokenNotFoundException;
    void invalidateToken(String customerId, String tokenId) throws CustomerNotFoundException, TokenNotFoundException;
}
