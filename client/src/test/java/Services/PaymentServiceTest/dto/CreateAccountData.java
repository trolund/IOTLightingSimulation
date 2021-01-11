package Services.PaymentServiceTest.dto;

import Services.PaymentServiceTest.dao.User;

import java.io.Serializable;

public class CreateAccountData implements Serializable {
    public int balance;
    public User user;
}
