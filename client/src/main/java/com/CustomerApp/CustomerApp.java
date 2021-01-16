package com.CustomerApp;

import com.client.TokenServiceClient;
import dto.Token;

public class CustomerApp {

    private final TokenServiceClient tokenService = new TokenServiceClient();

    public Token getToken(String customerId) {
        return tokenService.getToken(customerId);
    }

    public boolean requestTokens(String customerId, int amount) {
        return tokenService.requestTokens(customerId, amount);
    }

}
