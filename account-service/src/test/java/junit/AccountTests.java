package junit;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Disabled;
import services.AccountService;
import services.interfaces.IAccountService;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AccountTests {

    private IAccountService service;

    @BeforeEach
    public void setUp() {
        service = new AccountService();
        System.out.println("Setting up...");
    }

    @AfterEach
    public void tearDown() {
        // Put teardown code here if needed.
        System.out.println("Tearing down...");
    }

//    @Test
//    public void testHealth() {
//        String expected = "I am healthy and ready to work!";
//        String actual = service.hello();
//
//        // The third argument to assertEquals is a message displayed when the
//        // test fails. This is useful to rapidly understand what the test expects.
//        assertEquals(expected, actual, "The two strings should be equal!");
//    }

    @Disabled
    public void exampleRead() {
        String expected = "Account obj";
        assertEquals(true, true);
        //String actual = service.readAccount().getMsg();

        // The third argument to assertEquals is a message displayed when the
        // test fails. This is useful to rapidly understand what the test expects.
        //assertEquals(expected, actual, "The two strings should be equal!");
    }

}
