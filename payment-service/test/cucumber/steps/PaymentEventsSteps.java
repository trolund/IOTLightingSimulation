package cucumber.steps;

import dto.PaymentAccounts;
import dto.UserAccountDTO;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import messaging.Event;
import services.PaymentService;

import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class PaymentEventsSteps {

    PaymentService s;
    Event event;
    CompletableFuture<Boolean> result = new CompletableFuture<>();

    private String successToken;

    public PaymentEventsSteps() {
        s = new PaymentService(ev -> event = ev);
    }

    @When("a colleague working with accounts is done he notify me with {string}")
    public void sendAccountEvent(String eventType){
        try {
            UserAccountDTO customerAccount = new UserAccountDTO();
            UserAccountDTO merchantAccount = new UserAccountDTO();

            PaymentAccounts paymentAccounts = new PaymentAccounts();
            paymentAccounts.setCustomer(customerAccount);
            paymentAccounts.setMerchant(merchantAccount);
            paymentAccounts.setAmount(12);
            paymentAccounts.setToken("123");

            s.SendTestEvent(eventType, new Object[]{paymentAccounts});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Then("i receive a message {string} it means that one or both accounts does not exist in the bank")
    public void receive_accounts_fail(String eventType) {
            assertTrue(event.getEventType().equals(eventType));
    }

    @Then("i notify the parties that the transaction failed with {string}")
    public void sendsEvent(String eventType) {
        try {
            s.SendTestEvent("PaymentFailed", new Object[] {});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @And("the message should be {string}")
    public void typeCheck(String eventType){
        assertTrue(event.getEventType().equals(eventType));
    }


    @When("a colleague working with tokens is done he notify me with {string}")
    public void endTokenEvent(String eventType){
        try {
            s.SendTestEvent(eventType, new Object[] {});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Then("i receive {string}")
    public void receive(String eventType){
        assertTrue(event.getEventType().equals(eventType));
    }

    @Then("i have to notify the parties that the transaction failed with {string}")
    public void notify(String eventType){
        try {
            s.SendTestEvent(eventType, new Object[] {});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Then("i notify bank that the money should be moved"){

    }
}
