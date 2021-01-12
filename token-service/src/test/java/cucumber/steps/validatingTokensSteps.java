package cucumber.steps;

import domain.Token;
import exceptions.TokenNotFoundException;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.junit.jupiter.api.Assertions;
import services.TokenService;

import java.util.ArrayList;
import java.util.List;

public class validatingTokensSteps {
    String customerId, foundCustomerId;
    TokenService es = new TokenService();
    List<Token> tokens = new ArrayList<>();
    Exception e;

    @Given("a customer with id {string}")
    public void aCustomerWithId(String cid) {
        customerId = cid;
        es.addCustomer(cid);
    }

    @And("has an unused token")
    public void hasAnUnusedToken() {
        try {
            tokens.addAll(es.addTokens(customerId, 1));
        } catch (Exception e) {
            Assertions.fail(e.getMessage());
        }
    }

    @io.cucumber.java.en.And("^a token is received$")
    public void aTokenIsReceived() {
        try {
            foundCustomerId = es.getCustomerFromToken(tokens.get(0).getId()).getId();
        } catch (Exception e) {
            this.e = e;
        }
    }

    @io.cucumber.java.en.Then("^the token is invalidated$")
    public void theTokenIsInvalidated() {
        Assertions.assertThrows(TokenNotFoundException.class, () -> {es.getCustomerFromToken(tokens.get(0).getId());});
    }

    @io.cucumber.java.en.And("^the customer \"([^\"]*)\" is returned$")
    public void theCustomerIsReturned(String cid) {
        Assertions.assertEquals(cid, foundCustomerId);
    }

    @Then("a TokenNotFound exception is returned")
    public void aTokenNotFoundExceptionIsReturned() {
        Assertions.assertEquals("This token can not be found: " + tokens.get(0).getId(), e.getMessage());
    }
}
