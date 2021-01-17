package cucumber.steps;

import dto.Token;
import exceptions.CustomerAlreadyRegisteredException;
import exceptions.TokenNotFoundException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import services.TokenService;

public class validatingTokensSteps {
    String customerId, foundCustomerId;
    TokenService ts = new TokenService();
    Token token;
    Exception e;

    @Given("a customer with id {string}")
    public void aCustomerWithId(String cid) throws CustomerAlreadyRegisteredException {
        customerId = cid;
        ts.registerCustomer(cid);
    }

    @And("has an unused token")
    public void hasAnUnusedToken() {
        try {
            ts.requestTokens(customerId, 1);
        } catch (Exception e) {
            Assertions.fail(e.getMessage());
        }
    }

    @And("^a token is sent to the server")
    public void aTokenIsSentToTheServer() {
        try {
            foundCustomerId = ts.getCustomerFromToken(token.getId()).getCustomerId();
            ts.validateToken(token.getId());
        } catch (Exception e) {
            this.e = e;
        }
    }

    @Then("^the token is invalidated$")
    public void theTokenIsInvalidated() {
        Assertions.assertThrows(TokenNotFoundException.class, () -> ts.validateToken(token.getId()));
        Assertions.assertNull(e);
    }

    @And("^the customer \"([^\"]*)\" is returned$")
    public void theCustomerIsReturned(String cid) {
        Assertions.assertEquals(cid, foundCustomerId);
    }

    @Then("a TokenNotFound exception is returned")
    public void aTokenNotFoundExceptionIsReturned() {
        Assertions.assertEquals("Token " + token.getId() + " can not be found.", e.getMessage());
    }

    @And("the token is deleted")
    public void theTokenIsDeleted() {
        try {
            ts.validateToken(token.getId());
        } catch (Exception e) {
            Assertions.fail(e.getMessage());
        }
    }

    @And("the customer receives a token")
    public void theCustomerReceivesAToken() {
        try {
            token = ts.getToken(customerId);
        } catch (Exception e) {
            Assertions.fail(e.getMessage());
        }
    }

    @When("the customer tries to receive a token")
    public void theCustomerTriesToReceiveAToken() {
        try {
            ts.getToken(customerId);
        } catch (Exception e) {
            this.e = e;
        }
    }

    @Then("a {string} exception is returned")
    public void aExceptionIsReturned(String error) {
        Assertions.assertEquals(error, e.getMessage());
    }

}