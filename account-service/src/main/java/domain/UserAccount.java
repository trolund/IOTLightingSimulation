package domain;

import java.io.Serializable;
import exceptions.EmptyNameException;

public class UserAccount implements Serializable {

    private String firstName;
    private String lastName;
    private String cpr;
    private String id;
    private BankAccount bankAccount;
    private boolean disabled;

    public UserAccount() {
    }

    public UserAccount(String firstName, String lastName, String cpr) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.id = "";
        this.cpr = cpr;
        this.bankAccount = bankAccount;
        this.disabled = false;
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

    public String getCpr() {
        return cpr;
    }

    public void setCpr(String cpr) {
        this.cpr = cpr;
    }

    public boolean getDisabled() {
        return disabled;
    }

    public void setDisabled(boolean bool) {
        this.disabled = bool;
    }

    public BankAccount getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }

}
