package cucumber.steps;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.quarkus.test.junit.QuarkusTest;

import javax.ws.rs.client.Client;

@QuarkusTest
public class PaymentSteps {

    private TestClient client = new TestClient();

    private String cAccountId, mAccountId;
    private String cId, mId, cpr, firstName, lastName;
    private Integer balance, amount;
    private boolean isSuccess;


    @Given("a new customer with cpr {string}, first name {string},")
    public void a_new_customer_with_cpr_first_name(String cpr, String firstName) {
        this.cpr = cpr;
        this.firstName = firstName;
    }

    @Given("last name {string}, a balance of {int}")
    public void last_name_a_balance_of(String lastName, Integer balance) {
        this.lastName = lastName;
        this.balance = balance;
    }

    @Then("the new customer exists in the system")
    public void the_new_customer_exists_in_the_system() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Given("a new merchant with cpr {string}, first name {string},")
    public void a_new_merchant_with_cpr_first_name(String cpr, String firstName) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Then("the new merchant exists in the system")
    public void the_new_merchant_exists_in_the_system() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @When("the merchant initiates a payment for {int} by the customer")
    public void the_merchant_initiates_a_payment_for_by_the_customer(Integer balance) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Then("the customer should have a balance of {int} left")
    public void the_customer_should_have_a_balance_of_left(Integer balance) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Then("the merchant should have a balance of {int} left")
    public void the_merchant_should_have_a_balance_of_left(Integer balance) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
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