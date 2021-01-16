package services;

import dto.CustomerTokens;
import dto.Token;
import exceptions.*;
import exceptions.token.InvalidTokenException;
import infrastructure.repositories.CustomerTokensRepository;
import services.interfaces.ITokenService;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TokenService implements ITokenService {

    CustomerTokensRepository repo = new CustomerTokensRepository();

    @Override
    public void registerCustomer(String customerId) throws CustomerAlreadyRegisteredException {
        CustomerTokens customerToken = new CustomerTokens(customerId);
        repo.add(customerToken);
    }

    @Override
    public String requestTokens(String customerId, int amount) throws CustomerNotFoundException, TooManyTokensException, CustomerAlreadyRegisteredException {
        if (!customerExists(customerId)) {
            registerCustomer(customerId);
        }
        repo.get(customerId).addTokens(amount);
        return customerId;
    }

    @Override
    public Token getToken(String customerId) throws CustomerNotFoundException, CustomerHasNoTokensException {
        return repo.getTokenFromCustomer(customerId);
    }

    @Override
    public CustomerTokens getCustomerFromToken(String tokenId) throws TokenNotFoundException {
        return repo.getCustomerWithTokenId(tokenId);
    }

    @Override
    public Token validateToken(String tokenId) throws TokenNotFoundException, InvalidTokenException {
        return repo.validateTokenFromCustomer(tokenId);
    }

    @Override
    public String deleteCustomer(String customerId) throws CustomerNotFoundException {
        repo.deleteCustomer(customerId);
        return customerId;
    }

    @Override
    public CustomerTokens getCustomer(String customerId) throws CustomerNotFoundException {
        return repo.get(customerId);
    }

    @Override
    public boolean customerExists(String customerId) {
        try {
            repo.get(customerId);
        } catch (CustomerNotFoundException e) {
            return false;
        }
        return true;
    }

}