package cucumber.merchant;

import com.CustomerApp.CustomerApp;
import com.MerchantApp.MerchantApp;
import com.client.AccountServiceClient;
import com.client.TokenServiceClient;
import com.dto.BankAccount;
import com.dto.Token;
import com.dto.User;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

import java.math.BigDecimal;

public class PaymentSteps {

    private CustomerApp customerApp = new CustomerApp();
    private MerchantApp merchantApp = new MerchantApp();

    private AccountServiceClient accountService = new AccountServiceClient();
    private TokenServiceClient tokenService = new TokenServiceClient();

    private User customerUser, merchantUser;
    private BankAccount customerBank, merchantBank;

    private User customerFromSystem, merchantFromSystem;

    private Token customerToken;
    private Integer paymentAmount;

    @Given("a new customer with cpr {string}, first name {string}, last name {string} and a balance of {int}")
    public void a_new_customer_with_cpr_first_name(String cpr, String firstName, String lastName, Integer balance) {
        customerUser = new User();
        customerBank = new BankAccount();
        customerUser.setBankAccount(customerBank);

        customerUser.setCprNumber(cpr);
        customerUser.setFirstName(firstName);
        customerUser.setLastName(lastName);
        customerUser.getBankAccount().setBalance(BigDecimal.valueOf(balance));
    }

    @Then("when the customer is created")
    public void when_the_customer_is_created() {
        boolean isSuccess = accountService.registerUser(customerUser);
        Assert.assertTrue(isSuccess);
    }

    @Then("the new customer exists in the system")
    public void the_new_customer_exists_in_the_system() {
        customerFromSystem = accountService.getUserByCpr(customerUser.getCprNumber());
        Assert.assertNotNull(customerFromSystem);
    }

    @Then("the customer requests {int} tokens")
    public void the_customer_requests_tokens(Integer tokenAmount) {
        boolean isSuccess = customerApp.requestTokens(customerFromSystem.getId(), tokenAmount);
        Assert.assertTrue(isSuccess);
    }
/*
    @Then("the customer has {int} tokens available for use")
    public void the_customer_has_tokens_available_for_use(Integer tokenAmount) {
       // CustomerTokens customerTokens = tokenService.(systemCustomer.getId());
    }

 */

    @Given("a new merchant with cpr {string}, first name {string}, last name {string} and a balance of {int}")
    public void a_new_merchant_with_cpr_first_name(String cpr, String firstName, String lastName, Integer balance) {
        merchantUser = new User();
        merchantBank = new BankAccount();
        merchantUser.setBankAccount(merchantBank);

        merchantUser.setCprNumber(cpr);
        merchantUser.setFirstName(firstName);

        merchantUser.setLastName(lastName);
        merchantUser.getBankAccount().setBalance(BigDecimal.valueOf(balance));
    }

    @Then("when the merchant is created")
    public void when_the_merchant_is_created() {
        boolean isSuccess = accountService.registerUser(merchantUser);
        Assert.assertTrue(isSuccess);
    }

    @Then("the new merchant exists in the system")
    public void the_new_merchant_exists_in_the_system() {
        merchantFromSystem = accountService.getUserByCpr(merchantUser.getCprNumber());
        Assert.assertNotNull(customerFromSystem);
    }

    @When("the merchant initiates a payment for {int} by the customer")
    public void the_merchant_initiates_a_payment_for_by_the_customer(Integer paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    @Then("the merchant asks for a token from the customer")
    public void the_merchant_asks_for_a_token_from_the_customer() {
        customerToken = merchantApp.merchantRequestCustomerToken(customerFromSystem.getId());
        Assert.assertNotNull(customerToken);
    }

    @And("the payment is successful")
    public void the_payment_is_successful() {
        // hvor skal der betales igennem?
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

    @Then("the latest transaction contain the amount {int} for both accounts")
    public void the_latest_transaction_contain_the_amount_for_both_accounts(Integer transactionAmount) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Then("the latest transaction related to the customer contain balance {int}")
    public void the_latest_transaction_related_to_the_customer_contain_balance(Integer balance) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Then("the latest transaction related to the merchant contain balance {int}")
    public void the_latest_transaction_related_to_the_merchant_contain_balance(Integer balance) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

}