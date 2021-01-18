package cucumber;

import com.client.AccountServiceClient;
import dto.BankRegistrationDTO;
import dto.UserAccountDTO;
import dto.UserRegistrationDTO;
import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

import java.math.BigDecimal;

import static org.junit.Assert.assertNotNull;

public class AccountSteps {

    private final AccountServiceClient accountService = new AccountServiceClient();

    private UserRegistrationDTO userRegistrationDTO;

    private String currentAccountId;
    private String createdAccountId;

    private UserAccountDTO accountDTO;

    @After
    public void cleanup() {
        accountService.retireAccount(createdAccountId);
    }

    @Given("a new account with cpr {string}, first name {string}, last name {string} and an initial balance of {int}")
    public void a_new_account_with_cpr_first_name_last_name_and_an_initial_balance_of(String cpr, String firstName, String lastName, Integer balance) {
        userRegistrationDTO = new UserRegistrationDTO();
        userRegistrationDTO.setBankAccount(new BankRegistrationDTO());

        userRegistrationDTO.setCprNumber(cpr);
        userRegistrationDTO.setFirstName(firstName);
        userRegistrationDTO.setLastName(lastName);
        userRegistrationDTO.getBankAccount().setBalance(BigDecimal.valueOf(balance));
    }

    @When("the account is registered")
    public void the_account_is_registered() {
        currentAccountId = accountService.registerAccount(userRegistrationDTO);
        createdAccountId = currentAccountId;
    }

    @Then("the registration should be successful")
    public void the_registration_should_be_successful() {
        assertNotNull(currentAccountId);
    }

    @Then("the new account should exist in the system with correct information")
    public void the_new_account_should_exist_in_the_system_with_the_information() {
        accountDTO = accountService.getAccount(currentAccountId);
        Assert.assertNotNull(accountDTO);
    }

    @Then("the account is registered again")
    public void the_account_is_registered_again() {
        currentAccountId = accountService.registerAccount(userRegistrationDTO);
        createdAccountId = currentAccountId;
    }

    @Then("the registration should be unsuccessful")
    public void the_registration_should_be_unsuccessful() {
        Assert.assertNull(currentAccountId);
    }


}