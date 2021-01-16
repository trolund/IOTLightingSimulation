package dto;

import java.io.Serializable;

public class UserAccountDTO implements Serializable {

    private String firstName;
    private String lastName;
    private String cprNumber;
    private BankAccountDTO bankAccount;

    public UserAccountDTO() {

    }

    public UserAccountDTO(String firstName, String lastName,
                       String cprNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.cprNumber = cprNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCprNumber() {
        return cprNumber;
    }

    public void setCprNumber(String cprNumber) {
        this.cprNumber = cprNumber;
    }

    public BankAccountDTO getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(BankAccountDTO bankAccount) {
        this.bankAccount = bankAccount;
    }
}
