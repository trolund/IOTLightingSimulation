package com;

import com.CustomerApp.CustomerApp;
import com.MerchantApp.MerchantApp;
import com.client.AccountServiceClient;
import com.client.PaymentServiceClient;
import com.dto.BankAccount;
import com.dto.User;

import java.math.BigDecimal;

public class Main {

    private final static CustomerApp customerApp = new CustomerApp();
    private final MerchantApp merchantApp = new MerchantApp();

    private final static AccountServiceClient accountService = new AccountServiceClient();
    private final static PaymentServiceClient paymentService = new PaymentServiceClient();


    public static void main(String[] args) {

        accountService.retireUser("");
    }

}