package domain;

import exceptions.TokenNotFoundException;
import exceptions.TooManyTokensException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CustomerToken implements Serializable {

    private final List<Token> tokens;
    private String customerId;

    public CustomerToken(String customerId) {
        this.customerId = customerId;
        this.tokens = new ArrayList<>();
    }

    public List<Token> addTokens(Integer amount) throws TooManyTokensException {
        // Only allowed to request 1-5 tokens if you have one or less tokens
        if (tokens.size() < 2 && amount < 6 && amount > 0) {
            tokens.addAll(generateTokens(amount));
        } else {
            throw new TooManyTokensException(customerId, amount);
        }
        return tokens;
    }

    public List<Token> getTokens() {
        return tokens;
    }

    public String getCustomerId() {
        return customerId;
    }

    public boolean findTokenInList(String tokenId) {
        Token result = this.getTokens().stream()
                .filter(obj -> obj.getId()
                        .equals(tokenId))
                .findAny()
                .orElse(null);
        return result != null;
    }

    private String generateId() {
        return String.valueOf(Math.random() * 8100352);
    }

    private List<Token> generateTokens(Integer amount) {
        List<Token> newTokens = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            newTokens.add(new Token(generateId()));
        }
        return newTokens;
    }

    public void invalidateToken(String tokenId) {
        tokens.removeIf(obj -> obj.getId().equals(tokenId));
    }
}