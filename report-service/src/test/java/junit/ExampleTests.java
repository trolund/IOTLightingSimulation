package junit;

import messaging.Event;
import messaging.EventSender;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.ReportService;
import services.interfaces.IReportService;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExampleTests {

    private IReportService rs;
    Event event;

    @BeforeEach
    public void setUp() {
        rs = new ReportService(new EventSender() {
            @Override
            public void sendEvent(Event ev) throws Exception {
                event = ev;
            }
        });
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
        String actual = rs.hello();

        // The third argument to assertEquals is a message displayed when the
        // test fails. This is useful to rapidly understand what the test expects.
        assertEquals(expected, actual, "The two strings should be equal!");
    }

}
