package cucumber.steps;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Given;

import java.math.BigDecimal;

import infrastructure.bank.*;

import static org.junit.jupiter.api.Assertions.*;

public class BankAccountSteps {
//    
//    BankService bs = new BankServiceService().getBankServicePort();
//
//    BankAccount ba;
//
//    String firstName;
//    String lastName;
//    String cprNumber;
//    int initialBalance;
//
//    @Given("that the bank account with CPR {string} does not exist with the bank")
//    public void that_the_bank_account_with_cpr_does_not_exist_with_the_bank(String string) {
//        try {
//            Account a = bs.getAccountByCprNumber(string);
//            bs.retireAccount(a.getId());
//        } catch (Exception e) {
//            // exception thrown meaning account doesn't exist
//        }
//    }
//
//    @When("requesting to get the details for the account with CPR {string}")
//    public void requesting_to_get_the_details_for_the_account_with_cpr(String string) {
//        ba = new BankAccount();
//        ba.getFromBankByCpr(string);
//    }
//
//    @Then("the account is not modified")
//    public void the_account_is_not_modified() {
//        assertNotNull(ba.getBankId());
//    }
//
//    @Given("that the account object has not been initialized")
//    public void that_the_account_has_not_been_initialized() {
//        ba = new BankAccount();
//    }
//
//    @When("requesting to create a new account with the bank for first and last name {string} {string}, CPR {string} and balance {int}")
//    public void requesting_to_create_a_new_account_with_the_bank_for_first_and_last_name_cpr_and_balance(String string, String string2, String string3, Integer int1) {
//        firstName = string;
//        lastName = string2;
//        cprNumber = string3;
//        initialBalance = int1;
//
//        ba.createAtBank(firstName, lastName, cprNumber, initialBalance);
//    }
//
//    @Then("the bank account is created with bank account ID set")
//    public void the_bank_account_is_created_with_bank_account_id_set() {
//        assertNotNull(ba.getBankId());
//    }
//
//    @Then("the first name, last name, CPR and initial balance are set")
//    public void the_first_name_last_name_cpr_and_initial_balance_are_set() {
//        assertEquals(firstName, ba.getFirstName());
//        assertEquals(lastName, ba.getLastName());
//        assertEquals(cprNumber, ba.getCprNumber());
//    }
//
//    @When("requesting to create a new account with the bank with CPR {string}")
//    public void requesting_to_create_a_new_account_with_the_bank_with_cpr(String string) {
//        ba = new BankAccount();
//        ba.createAtBank("Some dude", "Cool last name", cprNumber, 0);
//    }
//
//    @Then("the bank account is not created and the bank account ID is not set")
//    public void the_bank_account_is_not_created_and_the_bank_account_id_is_not_set() {
//        assertNull(ba.getBankId());
//    }
//
//    @Given("that a bank account belonging to CPR {string} exists with the bank")
//    public void that_a_bank_account_belonging_to_cpr_exists_with_the_bank(String string) {
//        try {
//            // ensure that the account exists
//            User user = new User();
//            user.setCprNumber(cprNumber);
//            user.setFirstName("some dude");
//            user.setLastName("cool last name");
//            bs.createAccountWithBalance(user, new BigDecimal(0));
//        } catch (Exception e) { }
//    }
//
//    @When("requesting to get the account details for CPR {string}")
//    public void requesting_to_get_the_account_details_for_cpr(String string) {
//        ba.getFromBankByCpr(string);
//    }
//
//    @Then("the account is initialized with a copy of the details and CPR {string}")
//    public void the_account_is_initialized_with_a_copy_of_the_details_and_cpr(String string) {
//        assertEquals(ba.getCprNumber(), string);
//        assertNotNull(ba.getBankId());
//        assertNotNull(ba.getFirstName());
//        assertNotNull(ba.getLastName());
//    }
}
