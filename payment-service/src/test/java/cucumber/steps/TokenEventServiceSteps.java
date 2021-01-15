package cucumber.steps;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import messaging.Event;
import services.TokenEventService;

import java.util.concurrent.CompletableFuture;

import static org.junit.Assert.assertEquals;

/**
 * @primary-author Daniel (s151641)
 * @co-author Troels (s161791)
 */
public class TokenEventServiceSteps {

    TokenEventService s;
    Event event;
    CompletableFuture<String> result = new CompletableFuture<>();

    private String successToken;

    public TokenEventServiceSteps() {
        s = new TokenEventService(ev -> event = ev);
    }

    @When("I validate a token {string}")
    public void i_validate_a_token(String token) {
    	this.successToken = token;
        new Thread(() -> {
            try {
                result.complete(s.validateToken(token));
            } catch (Exception e) {
                throw new Error(e);
            }
        }).start();
    }

    @Then("I have sent event {string}")
    public void i_have_sent_event(String eventType) {
        assertEquals(eventType, event.getEventType());
    }

    @When("I receive event {string} with token {string}")
    public void i_receive_event(String eventType, String token) {
        try {
            s.receiveEvent(new Event(eventType, new Object[]{token}));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Then("token validation is successful")
    public void token_validation_is_successful() {
        assertEquals(successToken, result.join());
    }

    @When("I receive event {string}")
    public void i_receive_event(String eventType) {
        try {
            s.receiveEvent(new Event(eventType));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Then("token validation has failed")
    public void token_validation_has_failed() {
        assertEquals("", result.join());
    }

}