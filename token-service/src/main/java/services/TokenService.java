package services;

import domain.CustomerTokens;
import domain.Token;
import exceptions.CustomerNotFoundException;
import exceptions.TokenNotFoundException;
import exceptions.TooManyTokensException;
import infrastructure.repositories.CustomerTokensRepository;
import services.interfaces.ITokenService;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TokenService implements ITokenService {

    CustomerTokensRepository repo = new CustomerTokensRepository();

    @Override
    public void registerCustomer(String customerId) {
        CustomerTokens customerTokens = new CustomerTokens(customerId);
        repo.add(customerTokens);
    }

    @Override
    public void requestTokens(String customerId, int amount) throws CustomerNotFoundException, TooManyTokensException {
        repo.get(customerId).addTokens(amount);
    }

    @Override
    public Token getToken(String customerId) throws CustomerNotFoundException {
        return repo.getTokenFromCustomer(customerId);
    }

    @Override
    public CustomerTokens getCustomerFromToken(String tokenId) throws TokenNotFoundException {
        return repo.getCustomerWithTokenId(tokenId);
    }

    @Override
    public void invalidateToken(String tokenId) throws TokenNotFoundException {
        repo.invalidateTokenFromCustomer(tokenId);
    }

    @Override
    public void deleteCustomer(String customerId) throws CustomerNotFoundException {
        repo.deleteCustomer(customerId);
    }

    @Override
    public CustomerTokens getCustomer(String customerId) throws CustomerNotFoundException {
        return repo.get(customerId);
    }
}