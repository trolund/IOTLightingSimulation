package cucumber.steps;

import dto.PaymentAccounts;
import dto.UserAccountDTO;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import messaging.Event;
import services.PaymentService;

import java.util.concurrent.CompletableFuture;

public class PaymentEventsSteps {

    PaymentService s;
    Event event;
    CompletableFuture<String> result = new CompletableFuture<>();

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

    @When("i receive a message {string} it means that one or both accounts does not exist in the bank")
    public void receive_accounts_fail(String eventType) {
        try {
            s.receiveEvent(new Event(eventType, new Object[]{}));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Then("i notify the merchant that payment has failed")
    public void sendsEvent(String eventType) {
        try {
            s.receiveEvent(new Event(eventType, new Object[]{}));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
