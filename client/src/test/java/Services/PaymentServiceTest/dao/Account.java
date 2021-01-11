package Services.PaymentServiceTest.dao;

import java.util.ArrayList;

public class Account {

    private int id;
    private int balance;
    private ArrayList<Transaction> transactions;
    private String userId;


    public Account(int id, int balance, String userId) {
        this.id = id;
        this.balance = balance;
        this.transactions = new ArrayList<>();
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(ArrayList<Transaction> transactions) {
        this.transactions = transactions;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }


}
