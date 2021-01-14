package dto;


import javax.xml.datatype.XMLGregorianCalendar;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author Troels (s161791)
 * TransactionDTO.
 */
// @XmlRootElement(name = "TransactionDTO")
public class TransactionDTO implements Serializable {

    private BigDecimal amount;
    private BigDecimal balance;
    private String creditor;
    private String debtor;
    private String description;
    private XMLGregorianCalendar time;
    private String token;

    public TransactionDTO() {

    }

    public TransactionDTO(BigDecimal amount, String creditor, String debtor) {
        this.amount = amount;
        this.creditor = creditor;
        this.debtor = debtor;
    }

    public TransactionDTO(BigDecimal amount, BigDecimal balance, String creditor, String debtor, String description, XMLGregorianCalendar time) {
        this.amount = amount;
        this.balance = balance;
        this.creditor = creditor;
        this.debtor = debtor;
        this.description = description;
        this.time = time;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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

    @Override
    public String toString() {
        return "TransactionDTO{" +
                "amount=" + amount +
                ", balance=" + balance +
                ", creditor='" + creditor + '\'' +
                ", debtor='" + debtor + '\'' +
                ", description='" + description + '\'' +
                ", time=" + time +
                '}';
    }

}