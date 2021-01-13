package com.MerchantApp;

import com.CustomerApp.CustomerApp;
import com.client.PaymentServiceClient;
import com.dto.Token;

public class MerchantApp {

    CustomerApp customerApp = new CustomerApp();
    private PaymentServiceClient paymentClient = new PaymentServiceClient();

    public Token merchantRequestCustomerToken(String customerId) {
        return customerApp.getToken(customerId);
    }

    public boolean processPayment(String customerId, String merchantId, Integer amount) {
       return paymentClient.processPayment(customerId, merchantId, amount);
    }

}