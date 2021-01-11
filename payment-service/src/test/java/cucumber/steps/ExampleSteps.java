package cucumber.steps;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExampleSteps {

    // private final IExampleService service = new ExampleService();
    private String actual;

    @When("I check my health")
    public void iCheckMyHealth() {
        //actual = service.hello();
    }

    @When("I read my example")
    public void iReadMyExample() {
        //   actual = service.readExample().getMsg();
    }

    @Then("The result should be {string}")
    public void theResultShouldBe(String expected) {
        // The third argument to assertEquals is a message displayed when the
        // test fails. This is useful to rapidly understand what the test expects.
        assertEquals(expected, actual, "The two strings should be equal!");
    }

}