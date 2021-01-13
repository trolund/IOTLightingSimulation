package cucumber.steps;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Given;
import services.AccountService;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MessagesSteps {
    @Given("a message containing an account creation request is waiting")
    public void a_message_containing_an_account_creation_request_is_waiting() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @When("the request to create is handled")
    public void the_request_to_create_is_handled() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Then("the account service creates the account")
    public void the_account_service_creates_the_account() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Given("that a user with CPR {string} has been created")
    public void that_a_user_with_cpr_has_been_created(String string) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Given("the account is active")
    public void the_account_is_active() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @When("the request to disable the account is handled")
    public void the_request_to_disable_the_account_is_handled() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @When("an external service requests the details of the user with CPR {string}")
    public void an_external_service_requests_the_details_of_the_user_with_cpr(String string) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Then("the details of the user is sent in a response")
    public void the_details_of_the_user_is_sent_in_a_response() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Then("the response status code is {int} OK")
    public void the_response_status_code_is_ok(Integer int1) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }
}
