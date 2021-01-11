package Services.PaymentServiceTest;

import Services.PaymentServiceTest.dao.Account;
import Services.PaymentServiceTest.dao.Transaction;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Troels (s161791)
 * Step definition for Payment service test.
 */

public class PaymentSteps {
    String cid, mid;
    int amount;
    PaymentServiceClient dtuPay = new PaymentServiceClient();
    boolean successful;
    List<Transaction> transactions;
    String errorMsg;
    Account account;
    String curCpr;

    @Given("a customer with id {string}")
    public void aCustomerWithId(String cid) {
        this.cid = cid;
    }
    @Given("a merchant with id {string}")
    public void aMerchantWithId(String mid) {
        this.mid = mid;
    }
    @When("the merchant initiates a payment for {int} kr by the customer")
    public void theMerchantInitiatesAPaymentForKrByTheCustomer(int amount) {
        String msg = dtuPay.pay(amount, cid, mid);
        if(msg == null){
            successful = true;
        }else {
            successful = false;
            errorMsg = msg;
        }
    }

    @Then("the payment is successful")
    public void thePaymentIsSuccessful() {
        assertTrue(successful);
    }

    @Given("a successful payment of {int} kr from customer {string} to merchant {string}")
    public void givenTrans(int amount, String cid, String mid) {
        this.cid = cid;
        this.mid = mid;
        this.amount = amount;
    }

    @When("the manager asks for a list of transactions")
    public void asksForTransactionList() {
        this.transactions = dtuPay.getTransactionList(this.mid);
    }

    @Then("the list contains a transaction where customer {string} paid {int} kr to merchant {string}")
    public void assertTransactions(String cid, int amount, String mid) {
        assertTrue(!this.transactions.stream().filter(x -> x.amount == amount).findAny().isEmpty());
    }

    @Then("the payment is not successful")
    public void faildPayment (){
        assertFalse(successful);
    }

    @And("an error message is returned saying {string}")
    public void checkErrorMsg(String msg) {
        assertEquals(msg, errorMsg);
    }

    @Given("the customer {string} {string} with CPR {string} has a bank account")
    public void givenAccount(String firstname, String lastname, String cpr){
        Account a = dtuPay.getAccount(cpr);
        curCpr = cpr;
        account = a;
        assertNotNull(a);
        /*assertEquals(firstname, a);
        assertEquals(lastname, a.user.lastName);*/
    }

    @And("the balance of that account is {int}")
    public void balanceIs(int balance){

    }
}