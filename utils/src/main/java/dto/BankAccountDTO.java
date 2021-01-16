package dto;

import infrastructure.bank.*;

import java.io.Serializable;
import java.math.BigDecimal;

public class BankAccountDTO implements Serializable {
    private BigDecimal balance;

    public BankAccountDTO() {
    }

    public BankAccountDTO(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getBalance() {
        return this.balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
