package cucumber.steps;

import io.cucumber.java.PendingException;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Assertions;
import services.ExampleService;

public class requestTokensSteps {
    String customerId;
    ExampleService es;

    @io.cucumber.java.en.Given("^the customer with id \"([^\"]*)\"$")
    public void theCustomerWithId(String cid){
        customerId = "1234";
        es = new ExampleService();
        es.addCustomer(cid);
    }

    @io.cucumber.java.en.And("^the customer has (\\d+) tokens$")
    public void theCustomerHasTokens(int amount) {
        try {
            es.addTokens(customerId, amount);
        } catch (Exception e) {
            Assertions.fail(e.getMessage());
        }
    }

    @io.cucumber.java.en.When("^the customer request (\\d+) tokens$")
    public void theCustomerRequestTokens(int arg0) {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @io.cucumber.java.en.Then("^(\\d+) tokens are created$")
    public void tokensAreCreated(int arg0) {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @io.cucumber.java.en.And("^the tokens are given to the customer$")
    public void theTokensAreGivenToTheCustomer() {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @io.cucumber.java.en.Then("^an exception is returned with the message \"([^\"]*)\"$")
    public void anExceptionIsReturnedWithTheMessage(String arg0) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }
}
