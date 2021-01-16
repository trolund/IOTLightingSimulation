package cucumber;

import com.CustomerApp.CustomerApp;
import com.MerchantApp.MerchantApp;
import com.client.AccountServiceClient;
import com.client.PaymentServiceClient;
import dto.*;
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
    boolean successPayment;

    private UserRegistrationDTO cusRegDTO, mercRegDTO;

    private UserAccountDTO customerDTO, merchantDTO;

    private String currentCustomerId, currentMerchantId;
    private Token customerToken;
    private Integer paymentAmount;

    private Transaction cusLatestTran, mercLatestTran;

    @After
    public void cleanup() {
        accountService.retireUser(currentCustomerId);
        accountService.retireUser(currentMerchantId);
    }

    @Given("a new customer with cpr {string}, first name {string}, last name {string} and a balance of {int}")
    public void a_new_customer_with_cpr_first_name(String cpr, String firstName, String lastName, Integer balance) {
        cusRegDTO = new UserRegistrationDTO();
        cusRegDTO.setBankAccount(new BankRegistrationDTO());

        cusRegDTO.setCprNumber(cpr);
        cusRegDTO.setFirstName(firstName);
        cusRegDTO.setLastName(lastName);
        cusRegDTO.getBankAccount().setBalance(BigDecimal.valueOf(balance));
    }

    @When("the customer is registered")
    public void when_the_customer_is_registered() {
        currentCustomerId = accountService.registerUser(cusRegDTO);
        System.out.println("CusID " + currentCustomerId);
    }

    @Then("the customer registration should be successful")
    public void then_the_customer_registration_should_be_successful() {
        assertNotNull(currentCustomerId);
    }

    @And("the new customer should exist in the system")
    public void the_new_customer_exists_in_the_system() {
        customerDTO = accountService.getUserById(currentCustomerId);
        Assert.assertNotNull(customerDTO);
    }

    @Then("the customer requests {int} tokens")
    public void the_customer_requests_tokens(Integer tokenAmount) {
        boolean isSuccess = customerApp.requestTokens(currentCustomerId, tokenAmount);
        assertTrue(isSuccess);
    }

    @Given("a new merchant with cpr {string}, first name {string}, last name {string} and a balance of {int}")
    public void a_new_merchant_with_cpr_first_name(String cpr, String firstName, String lastName, Integer balance) {
        mercRegDTO = new UserRegistrationDTO();
        mercRegDTO.setBankAccount(new BankRegistrationDTO());

        mercRegDTO.setCprNumber(cpr);
        mercRegDTO.setFirstName(firstName);
        mercRegDTO.setLastName(lastName);
        mercRegDTO.getBankAccount().setBalance(BigDecimal.valueOf(balance));
    }

    @When("the merchant is registered")
    public void when_the_merchant_is_registered() {
        currentMerchantId = accountService.registerUser(mercRegDTO);
        System.out.println("merID " + currentMerchantId);
    }

    @Then("the merchant registration should be successful")
    public void then_the_merchant_registration_should_be_successful() {
        assertNotNull(currentMerchantId);
    }

    @Then("the new merchant exists in the system")
    public void the_new_merchant_exists_in_the_system() {
        merchantDTO = accountService.getUserById(currentMerchantId);
        Assert.assertNotNull(merchantDTO);
    }

    @When("the merchant initiates a payment for {int} by the customer")
    public void the_merchant_initiates_a_payment_for_by_the_customer(Integer paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    @And("the merchant asks for a token from the customer")
    public void the_merchant_asks_for_a_token_from_the_customer() {
        customerToken = merchantApp.requestTokenFromCustomer(currentCustomerId);
    }

    @Then("the merchant should receive a token")
    public void the_merchant_should_receive_a_token() {
        Assert.assertNotNull(customerToken);
    }

    @And("the payment is successful")
    public void the_payment_is_successful() {
        successPayment = merchantApp.processPayment(currentCustomerId, currentMerchantId, paymentAmount, customerToken.getId());
        assertTrue(successPayment);
    }

    @Then("the customer should have a balance of {int} left")
    public void the_customer_should_have_a_balance_of_left(Integer balanceLeft) {
        customerDTO = accountService.getUserById(currentCustomerId);
        Assert.assertNotNull(customerDTO);
        Assert.assertEquals(customerDTO.getBankAccount().getBalance(), BigDecimal.valueOf(balanceLeft));
    }

    @Then("the merchant should have a balance of {int} left")
    public void the_merchant_should_have_a_balance_of_left(Integer balanceLeft) {
        merchantDTO = accountService.getUserById(currentMerchantId);
        Assert.assertNotNull(customerDTO);
        Assert.assertEquals(merchantDTO.getBankAccount().getBalance(), BigDecimal.valueOf(balanceLeft));
    }

    @Then("the latest transaction contain the amount {int} for both accounts")
    public void the_latest_transaction_contain_the_amount_for_both_accounts(Integer transactionAmount) {
        cusLatestTran = paymentService.getLatestTransaction(customerDTO.getBankAccount().getBankId());
        mercLatestTran = paymentService.getLatestTransaction(merchantDTO.getBankAccount().getBankId());
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
        currentMerchantId = merchantDTO.getId();
    }

    @Then("the payment is unsuccessful")
    public void the_payment_is_unsuccessful() {
        successPayment = merchantApp.processPayment(currentCustomerId, currentMerchantId, paymentAmount, customerToken.getId());
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