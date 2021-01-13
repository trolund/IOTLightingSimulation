package cucumber.steps;

import domain.Token;
import exceptions.CustomerAlreadyRegisteredException;
import exceptions.CustomerNotFoundException;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import services.TokenService;

import java.util.List;

public class requestTokensSteps {
    String customerId;
    TokenService es = new TokenService();
    Exception e;
    Token token;

    @Given("^the customer with id \"([^\"]*)\"$")
    public void theCustomerWithId(String cid) throws CustomerAlreadyRegisteredException {
        customerId = "1234";
        es.registerCustomer(customerId);
    }

    @And("^the customer has (\\d+) tokens$")
    public void theCustomerHasTokens(int amount) {
        try {
            es.requestTokens(customerId, amount);
        } catch (Exception e) {
            Assertions.fail(e.getMessage());
        }
    }

    @When("^the customer requests (\\d+) tokens$")
    public void theCustomerRequestsTokens(int amount) {
        try {
            es.requestTokens(customerId, amount);
        } catch (Exception e) {
            this.e = e;
        }
    }

    @Then("^the customer owns (\\d+) tokens$")
    public void theCustomerOwnsTokens(int amount) {
        Assertions.assertNull(e);
        try {
            Assertions.assertEquals(amount, es.getCustomer(customerId).getTokens().size());
        } catch (CustomerNotFoundException e) {
            Assertions.fail(e.getMessage());
        }
    }

    @Then("^an exception is returned with the message \"([^\"]*)\"$")
    public void anExceptionIsReturnedWithTheMessage(String errormessage) {
        Assertions.assertEquals(errormessage, e.getMessage());
        e = null;
    }

    @Given("no customer exists with id {string}")
    public void noCustomerExistsWithId(String cid) {
        customerId = cid;
    }

    @When("the customer is deleted")
    public void theCustomerIsDeleted() {
        try {
            es.deleteCustomer(customerId);
        } catch (Exception e) {
            this.e = e;
        }
    }

    @Then("the customer is not found")
    public void theCustomerIsNotFound() {
        Assertions.assertNull(e);
        Assertions.assertThrows(CustomerNotFoundException.class, () -> es.getCustomer(customerId));
    }

    @When("another customer with id {string} is registered")
    public void anotherCustomerWithIdIsRegistered(String cid) {
        try {
            es.registerCustomer(cid);
        } catch (Exception e) {
            this.e = e;
        }
    }
}
