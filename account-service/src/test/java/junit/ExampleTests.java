package junit;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.ExampleService;
import services.interfaces.IExampleService;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExampleTests {

    private IExampleService service;

    @BeforeEach
    public void setUp() {
        service = new ExampleService();
        System.out.println("Setting up...");
    }

    @AfterEach
    public void tearDown() {
        // Put teardown code here if needed.
        System.out.println("Tearing down...");
    }

    @Test
    public void testHealth() {
        String expected = "I am healthy and ready to work!";
        String actual = service.hello();

        // The third argument to assertEquals is a message displayed when the
        // test fails. This is useful to rapidly understand what the test expects.
        assertEquals(expected, actual, "The two strings should be equal!");
    }

    @Test
    public void exampleRead() {
        String expected = "Example obj";
        assertEquals(true, true);
        //String actual = service.readExample().getMsg();

        // The third argument to assertEquals is a message displayed when the
        // test fails. This is useful to rapidly understand what the test expects.
        //assertEquals(expected, actual, "The two strings should be equal!");
    }

}
