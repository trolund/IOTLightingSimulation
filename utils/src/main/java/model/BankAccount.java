package model;

import infrastructure.bank.Account;

import java.io.Serializable;
import java.math.BigDecimal;

public class BankAccount implements Serializable {
    private String bankId;
    private BigDecimal balance;

    public BankAccount() {
    }

    public BankAccount(Account account) {
        this.bankId = account.getId();
        this.balance = account.getBalance();
    }

    public BankAccount(String bankId, BigDecimal balance) {
        this.bankId = bankId;
        this.balance = balance;
    }

    public String getBankId() {
        return this.bankId;
    }

    public void setBankId(String id) {
        this.bankId = id;
    }

    public BigDecimal getBalance() {
        return this.balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
