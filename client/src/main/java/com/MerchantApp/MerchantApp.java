package com.MerchantApp;

import com.CustomerApp.CustomerApp;
import com.client.PaymentServiceClient;
import dto.Token;

public class MerchantApp {

    CustomerApp customerApp = new CustomerApp();
    private PaymentServiceClient paymentClient = new PaymentServiceClient();

    public Token requestTokenFromCustomer(String customerId) {
        return customerApp.getToken(customerId);
    }

    public boolean processPayment(String customerId, String merchantId, Integer amount, String token) {
       return paymentClient.processPayment(customerId, merchantId, amount, token);
    }

}