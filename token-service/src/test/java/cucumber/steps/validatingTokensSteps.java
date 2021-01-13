package cucumber.steps;

import domain.Token;
import exceptions.CustomerNotFoundException;
import exceptions.TokenNotFoundException;

import exceptions.TooManyTokensException;
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
        es.registerCustomer(cid);
    }

    @And("has an unused token")
    public void hasAnUnusedToken() {
        try {
            tokens.addAll(es.requestTokens(customerId, 1));
        } catch (Exception e) {
            Assertions.fail(e.getMessage());
        }
    }

    @io.cucumber.java.en.And("^a token is received$")
    public void aTokenIsReceived() {
        try {
            foundCustomerId = es.getCustomerFromToken(tokens.get(0).getId()).getCustomerId();
        } catch (Exception e) {
            this.e = e;
        }
    }

    @io.cucumber.java.en.Then("^the token is invalidated$")
    public void theTokenIsInvalidated() {
        Assertions.assertThrows(TokenNotFoundException.class, () -> {es.getCustomerFromToken(tokens.get(0).getId());});
        Assertions.assertNull(e);
    }

    @io.cucumber.java.en.And("^the customer \"([^\"]*)\" is returned$")
    public void theCustomerIsReturned(String cid) {
        Assertions.assertEquals(cid, foundCustomerId);
    }

    @Then("a TokenNotFound exception is returned")
    public void aTokenNotFoundExceptionIsReturned() {
        Assertions.assertEquals("Token (" + tokens.get(0).getId() + ") can not be found.", e.getMessage());
    }

    @And("the token is deleted")
    public void theTokenIsDeleted() {
        try {
            es.invalidateToken(customerId, tokens.get(0).getId());
        } catch (Exception e) {
            Assertions.fail(e.getMessage());
        }
    }

    @And("the customer receives a token")
    public void theCustomerReceivesAToken() {
        try {
            es.requestTokens(customerId, 1);
        } catch (Exception e) {
            Assertions.fail(e.getMessage());
        }
    }
}
