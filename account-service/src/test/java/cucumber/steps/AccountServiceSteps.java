package cucumber.steps;

import dto.UserAccount;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Given;
import io.quarkus.test.junit.QuarkusTest;
import services.AccountService;

import javax.inject.Inject;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
public class AccountServiceSteps {

    @Inject
    AccountService as;
    UserAccount ua;
    
//
//    @Given("there exists {int} users in the system")
//    public void there_exists_users_in_the_system(Integer int1) {
//        assertEquals(as.getUsers().size(), int1);
//    }
//
//    @When("a new user is created")
//    public void a_new_user_is_created() {
//        as.createUser("Bob", "Amaze", "666666-6666");
//    }
//
//    @Then("the system contains {int} user")
//    public void the_system_contains_user(Integer int1) {
//        assertEquals(as.getUsers().size(), int1);
//    }
//
//    @Given("there exists users in the system")
//    public void there_exists_or_more_users(Integer int1) {
//        as.createUser("Mallory", "John", "777777-7777");
//    }
//
//    @When("another user is added")
//    public void another_user_is_added() {
//        // Write code here that turns the phrase above into concrete actions
//        throw new io.cucumber.java.PendingException();
//    }
//
//    @Then("the identifier for the new user is distinct from the existing users")
//    public void the_identifier_for_the_new_user_is_distinct_from_the_existing_users() {
//        // Write code here that turns the phrase above into concrete actions
//        throw new io.cucumber.java.PendingException();
//    }
//
//    @Given("there exists an account with CPR number {string}")
//    public void there_exists_an_account_with_cpr_number(String string) {
//        // Write code here that turns the phrase above into concrete actions
//        throw new io.cucumber.java.PendingException();
//    }
//
//    @When("a user requests to create a new account with CPR number {string}")
//    public void a_user_requests_to_create_a_new_account_with_cpr_number(String string) {
//        // Write code here that turns the phrase above into concrete actions
//        throw new io.cucumber.java.PendingException();
//    }
//
//    @Then("the account is not created")
//    public void the_account_is_not_created() {
//        // Write code here that turns the phrase above into concrete actions
//        throw new io.cucumber.java.PendingException();
//    }
//
//    @Then("a duplicate account exception is thrown")
//    public void a_duplicate_account_exception_is_thrown() {
//        // Write code here that turns the phrase above into concrete actions
//        throw new io.cucumber.java.PendingException();
//    }
}
