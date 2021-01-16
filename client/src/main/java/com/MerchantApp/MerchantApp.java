package com.MerchantApp;

import com.CustomerApp.CustomerApp;
import com.client.PaymentServiceClient;
import dto.PaymentRequest;
import dto.Token;

public class MerchantApp {

    private final PaymentServiceClient paymentClient = new PaymentServiceClient();
    private final CustomerApp customerApp = new CustomerApp();

    public Token requestTokenFromCustomer(String customerId) {
        return customerApp.getToken(customerId);
    }

    public boolean processPayment(PaymentRequest paymentRequest) {
        return paymentClient.processPayment(paymentRequest);
    }

}