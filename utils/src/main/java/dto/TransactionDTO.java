package dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

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
    private Date time;
    private String token;
    private boolean isSuccessful;

    public TransactionDTO() {

    }

    public TransactionDTO(BigDecimal amount, String creditor, String debtor) {
        this.amount = amount;
        this.creditor = creditor;
        this.debtor = debtor;
    }

    public TransactionDTO(BigDecimal amount, BigDecimal balance, String
            creditor, String debtor, String description, Date time, boolean isSuccessful) {
        this.amount = amount;
        this.balance = balance;
        this.creditor = creditor;
        this.debtor = debtor;
        this.description = description;
        this.time = time;
        this.isSuccessful = isSuccessful;
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

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public boolean isSuccessful() {
        return isSuccessful;
    }

    public void setSuccessful(boolean successful) {
        isSuccessful = successful;
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
                ", token='" + token + '\'' +
                ", isSuccessful=" + isSuccessful +
                '}';
    }

}