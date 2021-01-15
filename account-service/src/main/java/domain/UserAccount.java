package domain;

import java.io.Serializable;
import exceptions.EmptyNameException;

public class UserAccount implements Serializable {

    private String firstName;
    private String lastName;
    private String cprNumber;
    private String id;
    private BankAccount bankAccount;

    public UserAccount(String id, String firstName, String lastName,
            String cprNumber) {
        this.id = id;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCprNumber() {
        return cprNumber;
    }

    public void setCprNumber(String cprNumber) {
        this.cprNumber = cprNumber;
    }

    public BankAccount getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }

}
