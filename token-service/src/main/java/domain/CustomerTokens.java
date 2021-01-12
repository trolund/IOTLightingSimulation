package domain;

import exceptions.TooManyTokensException;

import java.util.List;
import java.util.ArrayList;
import java.io.Serializable;

// Are we 100% sure that we need to implement serializable here?
public class CustomerTokens implements Serializable {

    private String customerId;
    private List<Token> tokens;

    public CustomerTokens(String customerId) {
        this.customerId = customerId;
        this.tokens = new ArrayList<>();
    }

    public String getId() {
        return customerId;
    }

    public void setId(String customerId) {
        this.customerId = customerId;
    }

    public List<Token> getTokens() {
        return tokens;
    }

    public void addTokens(Integer amount) throws TooManyTokensException {
        if (tokens.size() <= 1) {
            tokens.addAll(generateTokens(amount));
        } else {
            throw new TooManyTokensException(customerId, tokens.size());
        }
    }

    private List<Token> generateTokens(Integer amount){
        List<Token> newTokens = new ArrayList<>();
        for (int i = 0; i < amount; i++){
            newTokens.add(new Token(String.valueOf(Math.random() * 8100352)));
        }
        return newTokens;

    }

    public boolean findTokenInList(String tokenId) {
        Token result = this.getTokens().stream().filter(obj -> obj.getId().equals(tokenId)).findAny().orElse(null);
        if (result == null){
            return false;
        } else {
            tokens.remove(result);
            return true;
        }
    }

}