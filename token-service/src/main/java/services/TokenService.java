package services;

import dto.CustomerTokens;
import dto.Token;
import exceptions.*;
import exceptions.token.InvalidTokenException;
import infrastructure.repositories.CustomerTokensRepository;
import interfaces.TokenReceiver;
import org.jboss.logmanager.Level;
import services.interfaces.ITokenService;

import javax.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@ApplicationScoped
public class TokenService implements ITokenService {

    CustomerTokensRepository repo = new CustomerTokensRepository();

    Logger logger = Logger.getLogger(TokenService.class.getName());

    @Override
    public void registerCustomer(String customerId) throws CustomerAlreadyRegisteredException {
        CustomerTokens customerToken = new CustomerTokens(customerId);
        repo.add(customerToken);
    }

    @Override
    public String requestTokens(String customerId, int amount) throws CustomerNotFoundException, TooManyTokensException, CustomerAlreadyRegisteredException {
        logger.log(Level.SEVERE, "REQUEST TOKENS: CustomerId: " + customerId + ", amount: " + amount);

        if (!customerExists(customerId)) {
            logger.log(Level.SEVERE, "CUSTOMER DID NOT EXIST! REGISTRERING NEW!");
            registerCustomer(customerId);
        }

        logger.log(Level.SEVERE, "ADDING TOKENS TO CUSTOMER!");
        addTokens(repo.get(customerId), amount);
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
        return validateTokenLogic(tokenId, repo.getCustomerWithTokenId(tokenId));
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

    private void addTokens(CustomerTokens customerTokens, Integer amount)
            throws TooManyTokensException {
        // Only allowed to request 1-5 tokens if you have one or less tokens
        if (customerTokens.getTokens().size() < 2 && amount < 6 && amount > 0) {
            customerTokens.getTokens().addAll(generateTokens(amount));
        } else {
            throw new TooManyTokensException(customerTokens.getCustomerId(), amount);
        }
    }

    private String generateTokenId() {
        return String.valueOf(Math.random() * 8100352);
    }

    private List<Token> generateTokens(Integer amount) {
        List<Token> newTokens = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            newTokens.add(new Token(generateTokenId()));
        }
        return newTokens;
    }

    private Token validateTokenLogic(String tokenId, CustomerTokens customerTokens) throws InvalidTokenException {
        Token token = customerTokens.getTokens().stream()
                .filter(obj -> obj.getId().equals(tokenId))
                .findAny()
                .orElse(null);

        if (token == null) {
            throw new InvalidTokenException(tokenId);
        }

        customerTokens.getTokens().removeIf(obj -> obj.getId().equals(tokenId));

        return token;
    }

}