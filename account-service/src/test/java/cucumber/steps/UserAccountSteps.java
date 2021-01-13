package cucumber.steps;

import io.cucumber.java.en.When;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import domain.UserAccount;
import exceptions.EmptyNameException;
import exceptions.EmptyCprException;
import services.AccountService;

import static org.junit.jupiter.api.Assertions.*;

public class UserAccountSteps {
    UserAccount u;
    @When("a user with first name {string}, last name {string}, CPR {string} requests to create an account")
    public void a_user_with_first_name_last_name_cpr_requests_to_create_an_account(String string, String string2, String string3) {
        u = new UserAccount(string, string2, string3);
    }

    @Then("a new account with first name {string}, last name {string} and CPR {string} is created")
    public void a_new_account_with_first_name_last_name_and_cpr_is_created(String string, String string2, String string3) {
        assertEquals(u.getFirstName(), string);
        assertEquals(u.getLastName(), string2);
        assertEquals(u.getCpr(), string3);
    }

    @Then("a unique identifier for the account is some string")
    public void a_unique_identifier_for_the_account_is_some_string() {
        u.setId("some string");
        assertEquals("some string", u.getId());
    }

    @Then("the account is not disabled")
    public void the_account_is_not_disabled() {
        assertFalse(u.getDisabled());
    }

    @Then("the account has a bank account attached")
    public void the_account_has_a_bank_account_attached() {
        throw new io.cucumber.java.PendingException();
    }

    @Given("a user with CPR {string} has been created")
    public void a_user_with_cpr_has_been_created(String string) {
        u = new UserAccount("Bjarne", "Ivertsen", "123456-7890");
    }

    @When("the bank account is fetched from the server")
    public void the_bank_account_is_fetched_from_the_server() {
        // bankservice
        throw new io.cucumber.java.PendingException();
    }

    @Then("the CPR number of the bank account is same as that of the user")
    public void the_cpr_number_of_the_bank_account_is_same_as_that_of_the_user() {
        // bankservice
        throw new io.cucumber.java.PendingException();
    }

    @When("a user requests to create an account supplying the name {string}")
    public void a_user_requests_to_create_an_account_supplying_the_name(String string) {
        u = new UserAccount("", "Ivertsen", "123456-7890");
    }

    @Then("an empty name exception is thrown")
    public void an_empty_name_exception_is_thrown() {
        throw new io.cucumber.java.PendingException();
    }

    @When("a user requests to create an account giving {string} as the CPR number")
    public void a_user_requests_to_create_an_account_giving_as_the_cpr_number(String string) {
        u = new UserAccount("Bjarne", "Ivertsen", "");
    }

    @Then("a missing CPR exception is thrown")
    public void a_missing_cpr_exception_is_thrown() {
        throw new io.cucumber.java.PendingException();
    }

    @Given("that the system contains an account with CPR {string}")
    public void that_the_system_contains_an_account_with_cpr(String string) {
        u = new UserAccount("Bjarne", "Ivertsen", "123456-7890");
    }

    @When("the account CPR {string} is requested to be disabled")
    public void the_account_cpr_is_requested_to_be_disabled(String string) {
        u.setDisabled(true);
    }

    @Then("the account is disabled")
    public void the_account_is_disabled() {
        assertTrue(u.getDisabled());
    }

}
