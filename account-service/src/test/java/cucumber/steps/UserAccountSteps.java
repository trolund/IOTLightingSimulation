/*package cucumber.steps;

import io.cucumber.java.en.When;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import exceptions.account.*;
import dto.BankAccountDTO;
import dto.UserAccountDTO;

import static org.junit.jupiter.api.Assertions.*;

public class UserAccountSteps {
    UserAccountDTO userAccount;
    BankAccountDTO bankAccount;

    @When("a user with first name {string}, last name {string}, CPR {string} requests to create an account")
    public void a_user_with_first_name_last_name_cpr_requests_to_create_an_account(String string, String string2, String string3) {
        userAccount = new UserAccountDTO();
        bankAccount = new BankAccountDTO();
        userAccount.setFirstName(string);
        userAccount.setLastName(string2);
        userAccount.setCpr(string3);
        userAccount.setBankAccount(bankAccount);
        userAccount.setId("some string");

    }

    @Then("a new account with first name {string}, last name {string} and CPR {string} is created")
    public void a_new_account_with_first_name_last_name_and_cpr_is_created(String string, String string2, String string3) {
        assertEquals(userAccount.getFirstName(), string);
        assertEquals(userAccount.getLastName(), string2);
        assertEquals(userAccount.getCpr(), string3);
    }

    @Then("a unique identifier for the account is some string")
    public void a_unique_identifier_for_the_account_is_some_string() {
        assertEquals("some string", userAccount.getId());
    }


    @Then("the account has a bank account attached")
    public void the_account_has_a_bank_account_attached() {
        assertNotNull(userAccount.getBankAccount());
    }

//    @Given("a user with CPR {string} has been created")
//    public void a_user_with_cpr_has_been_created(String string) {
//        userAccount = new UserAccount("Bjarne", "Ivertsen", "123456-7890");
//    }
//
//    @When("the bank account is fetched from the server")
//    public void the_bank_account_is_fetched_from_the_server() {
//        bankAccount = userAccount.getBankAccount();
//    }
//
//    @Then("the CPR number of the bank account is same as that of the user")
//    public void the_cpr_number_of_the_bank_account_is_same_as_that_of_the_user() {
//        String userCpr = userAccount.getCprNumber();
//        String bankAccountCpr = bankAccount.getCprNumber();
//        assertEquals(userCpr, bankAccountCpr);
//    }
//
//    @When("a user requests to create an account supplying the name {string}")
//    public void a_user_requests_to_create_an_account_supplying_the_name(String string) {
//        assertThrows(EmptyNameException.class, () -> {
//            userAccount = new UserAccount("", "Ivertsen", "123456-7890");
//        });
//    }
//
//    /* Should the exception be caught down here?
//     *
//    @Then("an empty name exception is thrown")
//    public void an_empty_name_exception_is_thrown() {
//        throw new io.cucumber.java.PendingException();
//    }
//    */
//
//    @When("a user requests to create an account giving {string} as the CPR number")
//    public void a_user_requests_to_create_an_account_giving_as_the_cpr_number(String string) {
//        assertThrows(EmptyCprException.class, () -> {
//            userAccount = new UserAccount("Bjarne", "Ivertsen", "");
//        });
//    }
//
//    /* Should the exception be caught down here?
//     *
//    @Then("a missing CPR exception is thrown")
//    public void a_missing_cpr_exception_is_thrown() {
//        throw new io.cucumber.java.PendingException();
//    }
//    */
//
//    @Given("that the system contains an account with CPR {string}")
//    public void that_the_system_contains_an_account_with_cpr(String string) {
//        userAccount = new UserAccount("Bjarne", "Ivertsen", "123456-7890");
//    }
//
//    @When("the account CPR {string} is requested to be disabled")
//    public void the_account_cpr_is_requested_to_be_disabled(String string) {
//        userAccount.setDisabled(true);
//    }
//
//    @Then("the account is disabled")
//    public void the_account_is_disabled() {
//        assertTrue(userAccount.getDisabled());
//    }
//
//}
