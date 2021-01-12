package dto;

import java.io.Serializable;

public class PersonDTO implements Serializable {

    private String firstName;
    private String lastName;
    private String cpr;
    private String id;

    public PersonDTO() {
    }

    public PersonDTO(String firstName, String lastName, String cpr, String id) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.id = id;
        this.cpr = cpr;
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
}
