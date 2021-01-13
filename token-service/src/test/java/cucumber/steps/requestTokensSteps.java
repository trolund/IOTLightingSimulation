package cucumber.steps;

import domain.Token;
import exceptions.CustomerNotFoundException;

import org.junit.jupiter.api.Assertions;
import services.TokenService;

import java.util.List;

public class requestTokensSteps {
    String customerId;
    TokenService es;
    Exception e;
    List<Token> tokens;

    @io.cucumber.java.en.Given("^the customer with id \"([^\"]*)\"$")
    public void theCustomerWithId(String cid){
        customerId = "1234";
        es = new TokenService();
        es.registerCustomer(customerId);
    }

    @io.cucumber.java.en.And("^the customer has (\\d+) tokens$")
    public void theCustomerHasTokens(int amount) {
        try {
            tokens = es.requestTokens(customerId, amount);
        } catch (Exception e) {
            this.e = e;
        }
    }

    @io.cucumber.java.en.When("^the customer requests (\\d+) tokens$")
    public void theCustomerRequestsTokens(int amount) {
        try {
            tokens = es.requestTokens(customerId, amount);
        } catch (Exception e) {
            this.e = e;
        }
    }

    @io.cucumber.java.en.Then("^the customer owns (\\d+) tokens$")
    public void theCustomerOwnsTokens(int amount) {
        if (e != null) {
            Assertions.fail(e.getMessage());
        }
        try {
            Assertions.assertEquals(amount, es.getTokens(customerId).getTokens().size());
            Assertions.assertEquals(tokens, es.getTokens(customerId).getTokens());
        } catch (CustomerNotFoundException e) {
            Assertions.fail(e.getMessage());
        }
    }

    @io.cucumber.java.en.Then("^an exception is returned with the message \"([^\"]*)\"$")
    public void anExceptionIsReturnedWithTheMessage(String errormessage) {
        Assertions.assertEquals(errormessage, e.getMessage());
    }
}
