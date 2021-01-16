package dto;

import infrastructure.bank.*;

import java.io.Serializable;
import java.math.BigDecimal;

public class BankAccountDTO implements Serializable {
    private BigDecimal balance;
    private String bankId;

    public BankAccountDTO() {
    }

    public BankAccountDTO(BigDecimal balance, String bankId) {
        this.balance = balance;
        this.bankId = bankId;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getBankId() {
        return bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId;
    }
}
