package domain;

import exceptions.TooManyTokensException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CustomerTokens implements Serializable {

    private final List<Token> tokens;
    private String customerId;

    public CustomerTokens(String customerId) {
        this.customerId = customerId;
        this.tokens = new ArrayList<>();
    }

    public List<Token> addTokens(Integer amount) throws TooManyTokensException {
        if (tokens.size() <= 1) {
            tokens.addAll(generateTokens(amount));
        } else {
            throw new TooManyTokensException(customerId, tokens.size());
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
        if (result == null) {
            return false;
        } else {
            tokens.remove(result);
            return true;
        }
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

    public void deleteToken(String tokenId) {
        tokens.removeIf(obj -> obj.getId().equals(tokenId));
    }
}