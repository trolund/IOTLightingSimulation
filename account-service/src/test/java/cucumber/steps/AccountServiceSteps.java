package cucumber.steps;

import io.quarkus.test.junit.QuarkusTest;
import dto.*;
import services.AccountService;
import infrastructure.repositories.*;
import infrastructure.bank.*;
import exceptions.account.*;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Given;

import javax.inject.Inject;
import java.util.List;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class AccountServiceSteps {
    AccountService as;

    String currentUserId = null;

    @Given("An instance of the account service has been initialized")
    public void an_instance_of_the_account_service_has_been_initialized() {
        as = new AccountService();
        as.clear(); // clear the internal list of users
    }

    @Then("The system contains no users")
    public void the_system_contains_no_users() {
        try {
            List<UserAccountDTO> dtos = as.getAll();
            assertEquals(0, dtos.size());
        } catch (BankAccountException e) {
            e.getMessage();
        }
    }

    @When("A user with first name {string}, last name {string}, CPR {string} is added")
    public void a_user_with_first_name_last_name_cpr_is_added(String string, String string2, String string3) {
        UserRegistrationDTO userRegister = new UserRegistrationDTO(string, string2, string3);
        BankRegistrationDTO bank = new BankRegistrationDTO(new BigDecimal(0));
        userRegister.setBankAccount(bank);
        try {
            currentUserId = as.register(userRegister);
        } catch (AccountExistsException e) {
            e.getMessage();
        }

    }

    @Then("The user is added to the list of users and the number of users is {int}")
    public void the_user_is_added_to_the_list_of_users_and_the_number_of_users_is(Integer int1) {
        try {
            // check length of the list in the repository
            assertEquals(int1, as.getAll().size());
        } catch (BankAccountException e) {
            e.getMessage();
        }
    }

    @Given("That user's ID is {string}")
    public void that_user_s_id_is(String string) {
        try {
            UserAccountDTO userAccount = as.get(currentUserId);
            assertEquals(userAccount.getId(), currentUserId);
        } catch (AccountNotFoundException e) {
            e.getMessage();
        }
    }

    @When("A user is requested retrieved using the ID {string}")
    public void a_user_is_requested_retrieved_using_the_id(String string) {
        try {
            UserAccountDTO userAccount = as.get(currentUserId);
        } catch (AccountNotFoundException e) {
            e.getMessage();
        }
    }

    @Then("The user is retrieved")
    public void the_user_is_retrieved() {
        try {
            UserAccountDTO userAccount = as.get(currentUserId);
            assertNotNull(userAccount);
        } catch (AccountNotFoundException e) {
            e.getMessage();
        }
    }


    @Then("The CPR of that user is {string}")
    public void the_cpr_of_that_user_is(String string) {
        try {
            UserAccountDTO userAccount = as.get(currentUserId);
            assertEquals(userAccount.getCpr(), string);
        } catch (AccountNotFoundException e) {
            e.getMessage();
        }
    }

    @When("A user is requested retrieved using the CPR {string}")
    public void a_user_is_requested_retrieved_using_the_cpr(String string) {
        try {
            UserAccountDTO userAccount = as.getByCpr(string);
        } catch (AccountNotFoundException e) {
            e.getMessage();
        }
    }

    @Then("The ID of that user is {string}")
    public void the_id_of_that_user_is(String string) {
        String cpr = "123456-7890";
        try {
            UserAccountDTO userAccount = as.getByCpr(cpr);
            assertNotNull(userAccount.getId());
        } catch (AccountNotFoundException e) {
            e.getMessage();
        }
    }

    @Given("Two users are registered")
    public void two_users_are_registered() {
        UserRegistrationDTO user1 = new UserRegistrationDTO("Bjarne", "Ivertsen", "123456-7890");
        user1.setBankAccount(new BankRegistrationDTO(new BigDecimal(0)));

        UserRegistrationDTO user2 = new UserRegistrationDTO("Soeren", "Soerensen", "654321-0987");
        user2.setBankAccount(new BankRegistrationDTO(new BigDecimal(0)));

        try {
            as.register(user1);
            as.register(user2);
       } catch (AccountExistsException e) {
           e.getMessage();
       }

       try {
           assertEquals(2, as.getAll().size());
       } catch (BankAccountException e) {
           e.getMessage();
       }
    }

    @When("A request to retrieve all users is received")
    public void a_request_to_retrieve_all_users_is_received() {
        try {
            as.getAll();
        } catch (Exception e) {
            e.getMessage();
        }
    }

    @Then("A list of all the users is retrieved")
    public void a_list_of_all_the_users_is_retrieved() {
        try {
            assertNotNull(as.getAll());
            assertEquals(2, as.getAll().size());
        } catch (BankAccountException e) {
            e.getMessage();
        }
    }

    @Then("The number of users in the list is {int}")
    public void the_number_of_users_in_the_list_is(Integer int1) {
        try {
            assertEquals(int1, as.getAll().size());
        } catch (BankAccountException e) {
            e.getMessage();
        }
    }

    @When("A user requests to retire an account using CPR {string}")
    public void a_user_requests_to_retire_an_account_using_cpr(String string) {
        try {
            as.retireAccountByCpr(string);
        } catch (BankAccountException e) {
            e.getMessage();
        }
    }

    @Then("The account with CPR {string} is retired")
    public void the_account_with_cpr_is_retired(String string) {
        try {
            assertNull(as.getByCpr(string));
        } catch (AccountNotFoundException e) {
            e.getMessage();
        }
    }

    @When("A user requests to retire an account using ID {string}")
    public void a_user_requests_to_retire_an_account_using_id(String string) {
        try {
            as.retireAccount(currentUserId);
        } catch (BankAccountException e) {
            e.getMessage();
        }
    }

    @Then("The account with ID {string} is retired")
    public void the_account_with_id_is_retired(String string) {
        try {
            assertNull(as.get(currentUserId));
        } catch (AccountNotFoundException e) {
            e.getMessage();
        }
    }

    @When("A request to register a user using the same information is requested")
    public void a_request_to_register_a_user_using_the_same_information_is_requested() {
        UserRegistrationDTO original = new UserRegistrationDTO("Bjarne", "Ivertsen", "123456-7890");
        original.setBankAccount(new BankRegistrationDTO(new BigDecimal(0)));

        UserRegistrationDTO duplicate = new UserRegistrationDTO("Bjarne", "Ivertsen", "123456-7890");
        duplicate.setBankAccount(new BankRegistrationDTO(new BigDecimal(0)));

        try {
            as.register(original);
            as.register(duplicate);
        } catch (AccountExistsException e) {
            assertNotNull(e.getMessage());
        }
    }

    @Then("The duplicate user is not registered")
    public void the_duplicate_user_is_not_registered() {
        try {
            List<UserAccountDTO> users = as.getAll();
            assertNotEquals(users.size(), 2);
        } catch (BankAccountException e) {
            e.getMessage();
        }
    }
    @Then("An exception is thrown")
    public void an_exception_is_thrown() {
        // Write code here that turns the phrase above into concrete actions
    }


    //    @Given("An instance of the bank service has been initialized")
    //    public void an_instance_of_the_bank_service_has_been_initialized() {
    //        BankService bs = new BankService();
    //    }
    //
    //    @When("A bank account is to be created for the user")
    //    public void a_bank_account_is_to_be_created_for_the_user() {
    //        UserRegistrationDTO u = new UserRegistrationDTO("Bjarne", "Ivertsen", "123456-7890");
    //        try {
    //            as.registerBankAccount(u);
    //        } catch (BankAccountException e) {
    //            e.getMessage();
    //        }
    //    }
    //
    //@Then("The bank account is created for the user")
    //public void the_bank_account_is_created_for_the_user() {
    //}
    //
    //@Given("A user with ID {string} is registered in the system")
    //public void a_user_with_id_is_registered_in_the_system(String string) {
    //// Write code here that turns the phrase above into concrete actions
    //}
    //
    //@Given("The user has a bank account with an ID {string}")
    //public void the_user_has_a_bank_account_with_an_id(String string) {
    //// Write code here that turns the phrase above into concrete actions
    //}
    //
    //@When("The owner of the bank account is requested")
    //public void the_owner_of_the_bank_account_is_requested() {
    //// Write code here that turns the phrase above into concrete actions
    //}
    //
    //@Then("The owner with ID {string} is retrieved")
    //public void the_owner_with_id_is_retrieved(String string) {
    //// Write code here that turns the phrase above into concrete actions
    //}
    //
    //@Given("An account with ID {string} is registered in the system")
    //public void an_account_with_id_is_registered_in_the_system(String string) {
    //// Write code here that turns the phrase above into concrete actions
    //}
    //
    //@When("The account is requested to be retired")
    //public void the_account_is_requested_to_be_retired() {
    //// Write code here that turns the phrase above into concrete actions
    //}
    //
    //@Then("The it is retired within the bank service")
    //public void the_it_is_retired_within_the_bank_service() {
    //// Write code here that turns the phrase above into concrete actions
    //}
    //
    //@Then("removed from the account service")
    //public void removed_from_the_account_service() {
    //// Write code here that turns the phrase above into concrete actions
    //}
    //
    //@When("a user with first name {string}, last name {string}, CPR {string} requests to create an account")
    //public void a_user_with_first_name_last_name_cpr_requests_to_create_an_account(String string, String string2, String string3) {
    //}
    //
    //@Then("a new account with first name {string}, last name {string} and CPR {string} is created")
    //public void a_new_account_with_first_name_last_name_and_cpr_is_created(String string, String string2, String string3) {
    //// Write code here that turns the phrase above into concrete actions
    //}
    //
    //@Then("a unique identifier for the account is some string")
    //public void a_unique_identifier_for_the_account_is_some_string() {
    //// Write code here that turns the phrase above into concrete actions
    //}
    //        
    //@Then("the account has a bank account attached")
    //public void the_account_has_a_bank_account_attached() {
    //// Write code here that turns the phrase above into concrete actions
    //}





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
