package com.MerchantApp;

import com.CustomerApp.CustomerApp;
import com.dto.Token;

public class MerchantApp {

    CustomerApp customerApp = new CustomerApp();

    public Token merchantRequestCustomerToken(String customerId) {
        return customerApp.getToken(customerId);
    }

}