package cucumber.steps;

import dto.CustomerTokens;
import exceptions.CustomerAlreadyRegisteredException;
import exceptions.CustomerNotFoundException;
import interfaces.rabbitmq.TokenListener;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import messaging.Event;
import org.junit.jupiter.api.Assertions;
import services.TokenEventService;
import services.TokenService;

public class requestTokensSteps {
    String customerId;
    TokenService ts = new TokenService();
    //private final CompletableFuture<List<TransactionDTO>> result = new CompletableFuture<>();
    Exception e;
    Event event;
    TokenListener tl;
    TokenEventService tes;

    public requestTokensSteps(){
        tes = new TokenEventService(ev -> event = ev, ts);

    }

    @Given("^the customer with id \"([^\"]*)\"$")
    public void theCustomerWithId(String cid) throws CustomerAlreadyRegisteredException {
        this.customerId = cid;
        ts.registerCustomer(cid);
    }

    @And("^the customer has (\\d+) tokens$")
    public void theCustomerHasTokens(int amount) {
        try {
            tes.receiveEvent(new Event("RequestTokens", new Object[]{customerId, amount}));
        } catch (Exception e) {
            throw new Error(e);
        }
    }

    @Then("^the customer owns (\\d+) tokens$")
    public void theCustomerOwnsTokens(int amount) throws CustomerNotFoundException {
        CustomerTokens customer = ts.getCustomer(customerId);
        Assertions.assertEquals(amount, customer.getTokens().size());
    }

    @Then("^an exception is returned with the message \"([^\"]*)\"$")
    public void anExceptionIsReturnedWithTheMessage(String errormessage) {
        Assertions.assertEquals(errormessage, e.getMessage());
        e = null;
    }

    @Given("no customer exists with id {string}")
    public void noCustomerExistsWithId(String cid) {
        customerId = cid;
        Assertions.assertFalse(ts.customerExists(cid));
    }

    @When("the customer is deleted")
    public void theCustomerIsDeleted() throws Exception {
        tes.receiveEvent(new Event("RetireCustomerTokens", new Object[]{customerId}));
    }

    @Then("the customer is not found")
    public void theCustomerIsNotFound() {
        Assertions.assertEquals("CustomerRetirementSuccessful", event.getEventType());
        Assertions.assertThrows(CustomerNotFoundException.class, () -> ts.getCustomer(customerId));
    }

    @When("the customer requests {int} tokens")
    public void theCustomerRequestsTokens(int amount) {
        try {
            tes.receiveEvent(new Event("RequestTokens", new Object[]{customerId, amount}));
        } catch (Exception e) {
            this.e = e;
        }
    }

}

