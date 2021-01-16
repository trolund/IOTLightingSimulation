package com.MerchantApp;

import com.CustomerApp.CustomerApp;
import com.client.PaymentServiceClient;
import dto.Token;

public class MerchantApp {

    private final PaymentServiceClient paymentClient = new PaymentServiceClient();
    private final CustomerApp customerApp = new CustomerApp();

    public Token requestTokenFromCustomer(String customerId) {
        return customerApp.getToken(customerId);
    }

    public boolean processPayment(String customerId, String merchantId, Integer amount, String token) {
        return paymentClient.processPayment(customerId, merchantId, amount, token);
    }

}