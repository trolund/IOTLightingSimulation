package cucumber.steps;

import dto.TransactionDTO;
import infrastructure.bank.Account;
import io.cucumber.java.After;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.quarkus.test.junit.QuarkusTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
public class PaymentSteps {

    private TestClient client = new TestClient();

    private String cAccountId, mAccountId;
    private String firstName, lastName;
    private String cCpr, mCpr;
    private Integer balance, amount;
    private boolean isSuccess;
    private TransactionDTO ct, mt;


    @Given("a new customer with cpr {string}, first name {string},")
    public void a_new_customer_with_cpr_first_name(String cpr, String firstName) {
        this.cCpr = cpr;
        this.firstName = firstName;
    }

    @Given("last name {string}, a balance of {int}")
    public void last_name_a_balance_of(String lastName, Integer balance) {
        this.lastName = lastName;
        this.balance = balance;
    }

    @Then("when the customer is created")
    public void when_the_customer_is_created(){
        this.cAccountId = client.createAccount(cCpr, firstName,lastName, balance);
    }

    @Then("when the merchant is created")
    public void when_the_merchant_is_created(){
        this.mAccountId = client.createAccount(mCpr, firstName,lastName, balance);
    }

    @Then("the new customer exists in the system")
    public void the_new_customer_exists_in_the_system() {
        // Write code here that turns the phrase above into concrete actions
        assertNotNull(cAccountId);
    }

    @Given("a new merchant with cpr {string}, first name {string},")
    public void a_new_merchant_with_cpr_first_name(String cpr, String firstName) {
        // Write code here that turns the phrase above into concrete actions
        this.mCpr = cpr;
        this.firstName = firstName;
    }

    @Then("the new merchant exists in the system")
    public void the_new_merchant_exists_in_the_system() {
        // Write code here that turns the phrase above into concrete actions
        assertNotNull(mAccountId);
    }

    @When("the merchant initiates a payment for {int} by the customer")
    public void the_merchant_initiates_a_payment_for_by_the_customer(Integer amount) {
        // Write code here that turns the phrase above into concrete actions
       boolean isCreated = client.createTransaction(this.cAccountId, this.mAccountId, amount);
       assertTrue(isCreated);
    }

    @Then("the customer should have a balance of {int} left")
    public void the_customer_should_have_a_balance_of_left(Integer balance) {
        // Write code here that turns the phrase above into concrete actions
        Account a = client.getAccount(this.cAccountId);
        assertEquals(BigDecimal.valueOf(balance), a.getBalance());
    }

    @Then("the merchant should have a balance of {int} left")
    public void the_merchant_should_have_a_balance_of_left(Integer balance) {
        // Write code here that turns the phrase above into concrete actions
        Account a = client.getAccount(this.mAccountId);
        assertEquals(a.getBalance(), BigDecimal.valueOf(balance));
    }

    @And("the latest transaction contain the amount {int} for both accounts")
    public void latest_transaction(int amount){
        TransactionDTO ca = client.getLatestTransaction(cAccountId);
        TransactionDTO ma = client.getLatestTransaction(mAccountId);

        this.ct = ca;
        this.mt = ma;

        assertEquals(BigDecimal.valueOf(amount), ca.getAmount());
        assertEquals(BigDecimal.valueOf(amount), ma.getAmount());
    }

    @And("the latest transaction related to the customer contain balance {int}")
    public void trasaction_balence(int balance){
        assertEquals(this.ct.getBalance(), BigDecimal.valueOf(balance));
    }

    @And("the latest transaction related to the merchant contain balance {int}")
    public void trasaction_balence_merchant(int balance){
        assertEquals(this.mt.getBalance(), BigDecimal.valueOf(balance));
    }



    @After
    public void cleanup(){
        client.retireAccount(this.cAccountId);
        client.retireAccount(this.mAccountId);
    }

    /*

    @Given("a customer with id {string}")
    public void a_customer_with_id(String string) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Given("cpr {string}, first name {string}, last name {string}, a balance of <c_start_bal>")
    public void cpr_first_name_last_name_a_balance_of_c_start_bal(String string, String string2, String string3) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Given("cpr {string}, first name {string}, last name {string}, a balance of <m_start_bal>")
    public void cpr_first_name_last_name_a_balance_of_m_start_bal(String string, String string2, String string3) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Given("cpr {string}, first name {string}, last name {string}, a balance of {int}")
    public void cpr_first_name_last_name_a_balance_of(String cpr, String firstName, String lastName, Integer balance) {
        this.cpr = cpr;
        this.firstName = firstName;
        this.lastName = lastName;
        this.balance = balance;
    }

    @Given("a merchant with id {string},")
    public void a_merchant_with_id(String mId) {
        this.mId = mId;
    }

    @When("the merchant initiates a payment for {int} by the customer")
    public void the_merchant_initiates_a_payment_for_by_the_customer(Integer balance) {
        isSuccess = client.createTransaction(cId, mId, balance);
    }

    @Then("the customer should have a balance of {int} left")
    public void the_customer_should_have_a_balance_of_left(Integer balanceLeft) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Then("the merchant should have a balance of {int} left")
    public void the_merchant_should_have_a_balance_of_left(Integer balanceLeft) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }


     */

}