package cucumber.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import messaging.Event;
import messaging.EventSender;
import services.*;

import java.util.concurrent.CompletableFuture;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TokenEventServiceSteps {
	TokenEventService s;
	Event event;
	CompletableFuture<String> result = new CompletableFuture<>();

	public TokenEventServiceSteps() {
		s = new TokenEventService(new EventSender() {

			@Override
			public void sendEvent(Event ev) throws Exception {
				System.out.println(ev);
				event = ev;
			}});
	}
	@Given("a token {string}")
	public void given_a_token(String token) {
		new Thread(() -> {try {
			s.validateToken(token);
			result.complete("42");
		} catch (Exception e) {
			throw new Error(e);
		}}).start();
	}
	
	@Then("I have sent event {string}")
	public void iHaveSentEvent(String string) {
		assertEquals(string,event.getEventType());
	}
	
	@When("I receive event {string}")
	public void iReceiveEvent(String string) {
		try {
			s.receiveEvent(new Event(string));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Then("do something is successful")
	public void doSomethingIsSuccessful() {

		assertTrue( result.join() == "42");
	}

}

