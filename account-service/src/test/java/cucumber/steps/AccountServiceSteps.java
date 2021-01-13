package cucumber.steps;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Given;
import services.AccountService;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AccountServiceSteps {
    @Given("there exists {int} users in the system")
    public void there_exists_users_in_the_system(Integer int1) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @When("a new user is created")
    public void a_new_user_is_created() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Then("a new user is instantiated")
    public void a_new_user_is_instantiated() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Then("the user is added to the list of users")
    public void the_user_is_added_to_the_list_of_users() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Then("the system contains {int} user")
    public void the_system_contains_user(Integer int1) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Given("there exists {int} or more users")
    public void there_exists_or_more_users(Integer int1) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @When("another user is added")
    public void another_user_is_added() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Then("the identifier for the new user is distinct from the existing users")
    public void the_identifier_for_the_new_user_is_distinct_from_the_existing_users() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Given("there exists an account with CPR number {string}")
    public void there_exists_an_account_with_cpr_number(String string) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @When("a user requests to create a new account with CPR number {string}")
    public void a_user_requests_to_create_a_new_account_with_cpr_number(String string) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Then("the account is not created")
    public void the_account_is_not_created() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Then("a duplicate account exception is thrown")
    public void a_duplicate_account_exception_is_thrown() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }
}
