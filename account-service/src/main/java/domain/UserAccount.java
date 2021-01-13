package domain;

import java.io.Serializable;

public class UserAccount implements Serializable {

    private String firstName;
    private String lastName;
    private String cpr;
    private String id;
    private boolean disabled;

    public UserAccount() {
    }

    public UserAccount(String firstName, String lastName, String cpr) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.id = "";
        this.cpr = cpr;
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

    public void setDisabled(boolean bool) {
        this.disabled = bool;
    }

    public boolean getDisabled() {
        return disabled;
    }

}
