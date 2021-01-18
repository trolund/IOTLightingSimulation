package com.CustomerApp;

import com.client.DTUPayClient;
import dto.Token;

public class CustomerApp {

    private final DTUPayClient dtuPay = new DTUPayClient();

    public Token getToken(String customerId) {
        return dtuPay.getToken(customerId);
    }

    public boolean requestTokens(String customerId, int amount) {
        return dtuPay.requestTokens(customerId, amount);
    }

}
