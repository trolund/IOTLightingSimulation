package domain;

import java.io.Serializable;

public class BankAccount implements Serializable {
    private String bankId;
    private String firstName;
    private String lastName;
    private String cprNumber;

    public BankAccount() {
    }

    public void createAtBank(String firstName, String lastName,
                             String cprNumber, int initialBalance) {
    }

    public void getFromBankByCpr(String cprNumber) {
    }

    public String getBankId() {
        return this.bankId;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public String getCprNumber() {
        return this.cprNumber;
    }
}
