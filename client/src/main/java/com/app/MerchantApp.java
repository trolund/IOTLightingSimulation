package com.app;

import com.client.DTUPayClient;
import dto.PaymentRequest;
import dto.Token;

public class MerchantApp {

    private final DTUPayClient dtuPay = new DTUPayClient();
    private final CustomerApp customerApp = new CustomerApp();

    public Token requestTokenFromCustomer(String customerId) {
        return customerApp.getToken(customerId);
    }

    public boolean processPayment(PaymentRequest paymentRequest) {
        return dtuPay.processPayment(paymentRequest);
    }

}