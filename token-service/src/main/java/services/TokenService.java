package services;

import domain.CustomerTokens;
import domain.Token;
import exceptions.CustomerNotFoundException;
import exceptions.TokenNotFoundException;
import exceptions.TooManyTokensException;
import infrastructure.repositories.CustomerTokensRepository;
import services.interfaces.ITokenService;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class TokenService implements ITokenService {

    CustomerTokensRepository repo = new CustomerTokensRepository();

    @Override
    public void registerCustomer(String customerId) {
        CustomerTokens customerTokens = new CustomerTokens(customerId);
        repo.add(customerTokens);
    }

    @Override
    public List<Token> requestTokens(String customerId, int amount) throws CustomerNotFoundException, TooManyTokensException {
        return repo.get(customerId).addTokens(amount);
    }

    @Override
    public CustomerTokens getTokens(String customerId) throws CustomerNotFoundException {
        return repo.get(customerId);
    }

    @Override
    public CustomerTokens getCustomerFromToken(String tokenId) throws TokenNotFoundException {
        return repo.getCustomerWithTokenId(tokenId);
    }

    @Override
    public void invalidateToken(String customerId, String tokenId) throws CustomerNotFoundException, TokenNotFoundException {
        repo.deleteToken(customerId, tokenId);
    }

    @Override
    public void deleteCustomer(String customerId) throws CustomerNotFoundException {
        repo.delete(customerId);
    }

    @Override
    public CustomerTokens getCustomer(String customerId) throws CustomerNotFoundException {
        return repo.get(customerId);
    }
}