package cucumber.steps;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Given;

import infrastructure.bank.*;

import static org.junit.jupiter.api.Assertions.*;

import domain.BankAccount;

public class BankAccountSteps {

    BankService bs = new BankServiceService().getBankServicePort();
    BankAccount ba;

    String firstName;
    String lastName;
    String cprNumber;
    int initialBalance;

    @Given("that the bank account with CPR {string} does not exist with the bank")
    public void that_the_bank_account_with_cpr_does_not_exist_with_the_bank(String string) {
        // TODO: send delete request to the bank ensuring that the account does not exist
        throw new io.cucumber.java.PendingException();
    }

    @When("requesting to get the details for the account with CPR {string}")
    public void requesting_to_get_the_details_for_the_account_with_cpr(String string) {
        ba = new BankAccount();
        ba.getFromBankByCpr(string);
    }

    @Then("the account is not modified")
    public void the_account_is_not_modified() {
        assertNotNull(ba.getBankId());
    }

    @Given("that the account has not been initialized")
    public void that_the_account_has_not_been_initialized() {
        ba = new BankAccount();
    }

    @When("requesting to create a new account with the bank for first and last name {string} {string}, CPR {string} and balance {int}")
    public void requesting_to_create_a_new_account_with_the_bank_for_first_and_last_name_cpr_and_balance(String string, String string2, String string3, Integer int1) {
        firstName = string;
        lastName = string2;
        cprNumber = string3;
        initialBalance = int1;

        ba.createAtBank(firstName, lastName, cprNumber, initialBalance);
    }

    @Then("the bank account is created with bank account ID set")
    public void the_bank_account_is_created_with_bank_account_id_set() {
        assertNotNull(ba.getBankId());
        assertEquals(firstName, ba.getFirstName());
        assertEquals(lastName, ba.getLastName());
        assertEquals(cprNumber, ba.getCprNumber());
    }

    @When("requesting to create a new account with the bank with CPR {string}")
    public void requesting_to_create_a_new_account_with_the_bank_with_cpr(String string) {
        ba = new BankAccount();
        ba.createAtBank("Some dude", "Cool last name", cprNumber, 0);
    }

    @Then("the bank account is not created and the bank account ID is not set")
    public void the_bank_account_is_not_created_and_the_bank_account_id_is_not_set() {
        assertNull(ba.getBankId());
    }

}
