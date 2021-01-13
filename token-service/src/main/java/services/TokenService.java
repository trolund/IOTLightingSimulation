package services;

import domain.CustomerToken;
import domain.Token;
import exceptions.*;
import infrastructure.repositories.CustomerTokensRepository;
import services.interfaces.ITokenService;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TokenService implements ITokenService {

    CustomerTokensRepository repo = new CustomerTokensRepository();

    @Override
    public void registerCustomer(String customerId) throws CustomerAlreadyRegisteredException {
        CustomerToken customerToken = new CustomerToken(customerId);
        repo.add(customerToken);
    }

    @Override
    public void requestTokens(String customerId, int amount) throws CustomerNotFoundException, TooManyTokensException, CustomerAlreadyRegisteredException {
        if (!customerExists(customerId)) {
            registerCustomer(customerId);
        }
        repo.get(customerId).addTokens(amount);
    }

    @Override
    public Token getToken(String customerId) throws CustomerNotFoundException, CustomerHasNoTokensException {
        return repo.getTokenFromCustomer(customerId);
    }

    @Override
    public CustomerToken getCustomerFromToken(String tokenId) throws TokenNotFoundException {
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
    public CustomerToken getCustomer(String customerId) throws CustomerNotFoundException {
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