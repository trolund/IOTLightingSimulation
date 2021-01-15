/*package dto;

import infrastructure.bank.*;

import java.io.Serializable;
import java.math.BigDecimal;

public class BankAccount implements Serializable {
    private String bankId;
    private BigDecimal balance;

//    private static BankService bs;

    public BankAccount() {
//        this.bs = new BankServiceService().getBankServicePort();
    }

    public BankAccount(Account account) {
        this.bankId = account.getId();
        this.balance = account.getBalance();
    }

    public BankAccount(String bankId, BigDecimal balance) {
        this.bankId = bankId;
        this.balance = balance;
    }

 /*   public void createAtBank(String firstName, String lastName,
                             String cprNumber, int initialBalance) {
        // create a user object
        User user = new User();
        user.setCprNumber(cprNumber);
        user.setFirstName(firstName);
        user.setLastName(lastName);
       
        // try to create a new account
        try {
            bs.createAccountWithBalance(user, new BigDecimal(initialBalance));
        } catch (BankServiceException_Exception e) {
            // account already exists, fetch it instead
            getFromBankByCpr(cprNumber);
        }
    }

    public boolean getFromBankByCpr(String cprNumber) {
        try {
            Account a = bs.getAccountByCprNumber(cprNumber);
            setBankId(a.getId());
            setFirstName(a.getUser().getFirstName());
            setLastName(a.getUser().getLastName());
            setCprNumber(a.getUser().getCprNumber());

            return true;
        } catch (BankServiceException_Exception e) {
            // TODO handle exception
        }

        return false;
    }
*/
    public String getBankId() {
        return this.bankId;
    }

    private void setBankId(String id) {
        this.bankId = id;
    }

    public BigDecimal getBalance() {
        return this.balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
/*
    public String getFirstName() {
        return this.firstName;
    }

    private void setFirstName(String name) {
        this.firstName = name;
    }

    public String getLastName() {
        return this.lastName;
    }

    private void setLastName(String name) {
        this.lastName = name;
    }

    public String getCprNumber() {
        return this.cprNumber;
    }

    private void setCprNumber(String cprNumber) {
        this.cprNumber = cprNumber;
    }
    */
}
*/