package cucumber;

import com.CustomerApp.CustomerApp;
import com.MerchantApp.MerchantApp;
import com.client.AccountServiceClient;
import com.client.PaymentServiceClient;
import com.dto.Token;
import com.dto.Transaction;
import io.cucumber.java.After;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class PaymentSteps {

    private final CustomerApp customerApp = new CustomerApp();
    private final MerchantApp merchantApp = new MerchantApp();

    private final AccountServiceClient accountService = new AccountServiceClient();
    private final PaymentServiceClient paymentService = new PaymentServiceClient();

    private UserAccount customerUser, merchantUser;
    private UserAccount customerFromSystem, merchantFromSystem;
    private String currentCustomerId, currentMerchantId;

    boolean isUserRegistered;
    boolean successPayment;

    private Token customerToken;
    private Integer paymentAmount;

    private Transaction cusLatestTran, mercLatestTran;

    @After
    public void cleanup(){
        //accountService.retireUser(currentCustomerId);
        //accountService.retireUser(currentMerchantId);
    }

    @Given("a new customer with cpr {string}, first name {string}, last name {string} and a balance of {int}")
    public void a_new_customer_with_cpr_first_name(String cpr, String firstName, String lastName, Integer balance) {
        customerUser = new UserAccount();
        customerUser.setBankAccount(new BankAccount());

        customerUser.setCprNumber(cpr);
        customerUser.setFirstName(firstName);
        customerUser.setLastName(lastName);
        customerUser.getBankAccount().setBalance(BigDecimal.valueOf(balance));
    }

    @When("the customer is registered")
    public void when_the_customer_is_registered() {
        isUserRegistered = accountService.registerUser(customerUser);
    }

    @Then("the registration should be successful")
    public void then_the_registration_should_be_successful() {
        assertTrue(isUserRegistered);
    }

    @And("the new customer should exist in the system")
    public void the_new_customer_exists_in_the_system() {
        customerFromSystem = accountService.getUserByCpr(customerUser.getCprNumber());
        currentCustomerId = customerFromSystem.getId();
        Assert.assertNotNull(customerFromSystem);
    }

    @Then("the customer requests {int} tokens")
    public void the_customer_requests_tokens(Integer tokenAmount) {
        boolean isSuccess = customerApp.requestTokens(customerFromSystem.getId(), tokenAmount);
        assertTrue(isSuccess);
    }

    @Given("a new merchant with cpr {string}, first name {string}, last name {string} and a balance of {int}")
    public void a_new_merchant_with_cpr_first_name(String cpr, String firstName, String lastName, Integer balance) {
        merchantUser = new UserAccount();
        merchantUser.setBankAccount(new BankAccount());

        merchantUser.setCprNumber(cpr);
        merchantUser.setFirstName(firstName);
        merchantUser.setLastName(lastName);
        merchantUser.getBankAccount().setBalance(BigDecimal.valueOf(balance));
    }

    @When("the merchant is registered")
    public void when_the_merchant_is_registered() {
       isUserRegistered = accountService.registerUser(merchantUser);
    }

    @Then("the new merchant exists in the system")
    public void the_new_merchant_exists_in_the_system() {
        merchantFromSystem = accountService.getUserByCpr(merchantUser.getCprNumber());
        currentMerchantId = merchantFromSystem.getId();
        Assert.assertNotNull(customerFromSystem);
    }

    @When("the merchant initiates a payment for {int} by the customer")
    public void the_merchant_initiates_a_payment_for_by_the_customer(Integer paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    @And("the merchant asks for a token from the customer")
    public void the_merchant_asks_for_a_token_from_the_customer() {
        customerToken = merchantApp.requestTokenFromCustomer(customerFromSystem.getId());
    }

    @Then("the merchant should receive a token")
    public void the_merchant_should_receive_a_token() {
        Assert.assertNotNull(customerToken);
    }

    @And("the payment is successful")
    public void the_payment_is_successful() {
        successPayment = merchantApp.processPayment(currentCustomerId, currentMerchantId, paymentAmount);
        assertTrue(successPayment);
    }

    @Then("the customer should have a balance of {int} left")
    public void the_customer_should_have_a_balance_of_left(Integer balanceLeft) {
        customerFromSystem = accountService.getUserByCpr(customerUser.getCprNumber());
        Assert.assertNotNull(customerFromSystem);
        Assert.assertEquals(customerFromSystem.getBankAccount().getBalance(), BigDecimal.valueOf(balanceLeft));
    }

    @Then("the merchant should have a balance of {int} left")
    public void the_merchant_should_have_a_balance_of_left(Integer balanceLeft) {
        merchantFromSystem = accountService.getUserByCpr(merchantUser.getCprNumber());
        Assert.assertNotNull(customerFromSystem);
        Assert.assertEquals(merchantFromSystem.getBankAccount().getBalance(), BigDecimal.valueOf(balanceLeft));
    }

    @Then("the latest transaction contain the amount {int} for both accounts")
    public void the_latest_transaction_contain_the_amount_for_both_accounts(Integer transactionAmount) {
        cusLatestTran = paymentService.getLatestTransaction(customerFromSystem.getBankAccount().getBankId());
        mercLatestTran = paymentService.getLatestTransaction(merchantFromSystem.getBankAccount().getBankId());
        Assert.assertNotNull(cusLatestTran);
        Assert.assertNotNull(mercLatestTran);
        Assert.assertEquals(BigDecimal.valueOf(transactionAmount), cusLatestTran.getAmount());
        Assert.assertEquals(BigDecimal.valueOf(transactionAmount), mercLatestTran.getAmount());
    }

    @And("the latest transaction related to the customer contain balance {int}")
    public void the_latest_transaction_related_to_the_customer_contain_balance(Integer balance) {
        Assert.assertEquals(BigDecimal.valueOf(balance), cusLatestTran.getBalance());
    }

    @And("the latest transaction related to the merchant contain balance {int}")
    public void the_latest_transaction_related_to_the_merchant_contain_balance(Integer balance) {
        Assert.assertEquals(BigDecimal.valueOf(balance), mercLatestTran.getBalance());
    }

    @Given("a customer with id {string} that does not exist in the system")
    public void a_customer_with_id_that_does_not_exist_in_the_system(String id) {
        this.currentCustomerId = id;
    }

    @And("a merchant that exists in the system")
    public void a_merchant_that_exists_in_the_system() {
        currentMerchantId = merchantFromSystem.getId();
    }

    @Then("the payment is unsuccessful")
    public void the_payment_is_unsuccessful() {
        successPayment = merchantApp.processPayment(currentCustomerId, currentMerchantId, paymentAmount);
        assertFalse(successPayment);
    }

    @Given("a customer that exists in the system")
    public void a_customer_that_exist_in_the_system(String id) {
        this.currentCustomerId = id;
    }

    @Given("a merchant with id {string} that does not exist in the system")
    public void a_merchant_with_id_that_does_not_exist_in_the_system(String id) {
        this.currentMerchantId = id;
    }

}