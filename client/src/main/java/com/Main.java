package com;

import com.CustomerApp.CustomerApp;
import com.MerchantApp.MerchantApp;
import com.client.AccountServiceClient;
import com.client.PaymentServiceClient;
import dto.BankAccountDTO;
import dto.UserAccountDTO;
import model.UserAccount;


import java.math.BigDecimal;

public class Main {

    private final static CustomerApp customerApp = new CustomerApp();
    private final MerchantApp merchantApp = new MerchantApp();

    private final static AccountServiceClient accountService = new AccountServiceClient();
    private final static PaymentServiceClient paymentService = new PaymentServiceClient();

    public static void main(String[] args) {
        UserAccountDTO customerUserAccount = new UserAccountDTO();
        customerUserAccount.setBankAccount(new BankAccountDTO());

        customerUserAccount.setCprNumber("000000-9876");
        customerUserAccount.setFirstName("Michael");
        customerUserAccount.setLastName("Hardy");
        customerUserAccount.getBankAccount().setBalance(BigDecimal.valueOf(20));

        System.out.println(accountService.registerUser(customerUserAccount));
    }

}