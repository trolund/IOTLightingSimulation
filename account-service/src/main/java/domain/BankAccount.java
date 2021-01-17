package domain;

import infrastructure.bank.*;

import java.io.Serializable;
import java.math.BigDecimal;

public class BankAccount implements Serializable {
    private String bankId;
    private BigDecimal balance;

    public BankAccount() { }

    public BankAccount(Account account) {
        this.bankId = account.getId();
        this.balance = account.getBalance();
    }

    public BankAccount(String bankId, BigDecimal initialBalance) {
        this.bankId = bankId;
        this.balance = initialBalance;
    }

   public String getBankId() {
        return this.bankId;
    }

    private void setBankId(String id) {
        this.bankId = id;
    }

    public BigDecimal getBalance() {
        return this.balance;
    }

    private void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
