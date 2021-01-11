package Services.PaymentServiceTest.dao;

import javax.ws.rs.core.GenericType;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Transaction extends GenericType<List<Transaction>> implements Serializable {

    public int id;
    public int amount;
    public int balance;
    public String creditor;
    public String debtor;
    public String description;
    public Date time;

    public Transaction() {

    }

    public Transaction(int amount, int balance, String creditor, String debtor, String description, Date time) {
        this.amount = amount;
        this.balance = balance;
        this.creditor = creditor;
        this.debtor = debtor;
        this.description = description;
        this.time = time;
    }

    public Transaction(int id, int amount, int balance, String creditor, String debtor, String description, Date time) {
        this.id = id;
        this.amount = amount;
        this.balance = balance;
        this.creditor = creditor;
        this.debtor = debtor;
        this.description = description;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
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
}
