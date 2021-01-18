package cucumber.steps;

import dto.PaymentAccounts;
import dto.Token;
import exceptions.CustomerAlreadyRegisteredException;
import exceptions.TokenNotFoundException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import messaging.Event;
import org.junit.jupiter.api.Assertions;
import services.TokenEventService;
import services.TokenService;

public class validatingTokensSteps {
    String customerId, foundCustomerId;
    TokenService ts = new TokenService();
    Token token;
    Exception e;
    Event event;
    TokenEventService tes;

    public validatingTokensSteps(){
        tes = new TokenEventService(ev -> event = ev, ts);
    }

    @Given("a customer with id {string}")
    public void aCustomerWithId(String cid) throws CustomerAlreadyRegisteredException {
        customerId = cid;
        ts.registerCustomer(cid);
    }

    @And("has an unused token")
    public void hasAnUnusedToken() {
        try {
            tes.receiveEvent(new Event("RequestTokens", new Object[]{customerId, 1}));
        } catch (Exception e) {
            throw new Error(e);
        }
    }

    @And("^a token is sent to the server")
    public void aTokenIsSentToTheServer() {
        Assertions.assertEquals("GetTokenSuccessful", event.getEventType());
        try {
            foundCustomerId = ts.getCustomerFromToken(token.getId()).getCustomerId();
            PaymentAccounts paymentAccounts = new PaymentAccounts();
            paymentAccounts.setToken(token.getId());
            tes.receiveEvent(new Event("PaymentAccountsSuccessful", new Object[]{paymentAccounts}));
        } catch (Exception e) {
            this.e = e;
        }
    }

    @Then("^the token is invalidated$")
    public void theTokenIsInvalidated() {
        System.out.println(event.getEventType());
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
            tes.receiveEvent(new Event("GetToken", new Object[]{customerId}));
            token = new Token((String) event.getArguments()[0]);
        } catch (Exception e) {
            throw new Error(e);
        }
    }

    @When("the customer tries to receive a token")
    public void theCustomerTriesToReceiveAToken() {
        try {
            tes.receiveEvent(new Event("GetToken", new Object[]{customerId}));
            token = new Token((String) event.getArguments()[0]);
        } catch (Exception e) {
            throw new Error(e);
        }
    }

    @Then("an event of type {string} is received")
    public void anEventOfTypeIsReceived(String eventType) {
        Assertions.assertEquals(eventType, event.getEventType());
    }

    @And("a {string} exception is returned")
    public void aExceptionIsReturned(String error) {
        Assertions.assertEquals(error, "Customer " + event.getArguments()[0] + " has no tokens.");
    }
}