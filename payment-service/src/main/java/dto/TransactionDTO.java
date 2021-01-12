package dto;

import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.datatype.XMLGregorianCalendar;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author Troels (s161791)
 * TransactionDTO.
 */
public class TransactionDTO implements Serializable {

    public BigDecimal amount;
    public BigDecimal balance;
    public String creditor;
    public String debtor;
    public String description;
    @XmlSchemaType(name = "dateTime")
    public XMLGregorianCalendar time;

    public TransactionDTO() {

    }

    public TransactionDTO(BigDecimal amount, BigDecimal balance, String creditor, String debtor, String description, XMLGregorianCalendar time) {
        this.amount = amount;
        this.balance = balance;
        this.creditor = creditor;
        this.debtor = debtor;
        this.description = description;
        this.time = time;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getCreditor() {
        return creditor;
    }

    public void setCreditor(String creditor) {
        this.creditor = creditor;
    }

    public String getDebtor() {
        return debtor;
    }

    public void setDebtor(String debtor) {
        this.debtor = debtor;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public XMLGregorianCalendar getTime() {
        return time;
    }

    public void setTime(XMLGregorianCalendar time) {
        this.time = time;
    }

}