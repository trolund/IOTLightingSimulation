package cucumber.steps;

import dto.TransactionDTO;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.PendingException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import messaging.Event;
import org.junit.jupiter.api.Assertions;
import services.ReportReceiverService;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class CustomerReportSteps {
    ReportReceiverService rr;
    List<TransactionDTO> inputTransactions = new ArrayList<>();
    private final CompletableFuture<List<TransactionDTO>> result = new CompletableFuture<>();
    Event event;
    private String customerId;

    public CustomerReportSteps() {
        rr = new ReportReceiverService(ev -> event = ev);
    }

    @Given("a list of customer transactions")
    public void aListOfCustomerTransactions(DataTable table) throws DatatypeConfigurationException {
        for (Map<Object, Object> row : table.asMaps(String.class, String.class)) {
            TransactionDTO transaction = new TransactionDTO();
            transaction.setAmount(new BigDecimal((String) row.get("amount")));
            transaction.setBalance(new BigDecimal((String) row.get("balance")));
            transaction.setCreditor((String) row.get("creditor"));
            transaction.setDebtor((String) row.get("debtor"));
            transaction.setDescription((String) row.get("description"));
            XMLGregorianCalendar calendar = DatatypeFactory.newInstance().newXMLGregorianCalendar((String) row.get("time"));
            transaction.setTime(calendar);
            transaction.setToken((String) row.get("token"));
            inputTransactions.add(transaction);
        }
    }

    @When("the customer {string} requests all their transactions")
    public void theCustomerRequestsAllTheirTransactions(String customerId) {
        this.customerId = customerId;
        new Thread(() -> {try {
            result.complete(rr.requestAllCustomerTransactions(customerId));
        } catch (Exception e) {
            throw new Error(e);
        }}).start();
    }

    @Then("a customer event {string} has been sent")
    public void aCustomerEventHasBeenSent(String eventType) {
        Assertions.assertEquals(eventType,event.getEventType());
    }

    @When("a customer event {string} has been sent back")
    public void aCustomerEventHasBeenSentBack(String eventType) throws Exception {
        rr.receiveEvent(new Event(eventType, new Object[] {inputTransactions}));
    }

    @Then("the customers transactions are displayed")
    public void theCustomersTransactionsAreDisplayed(DataTable table) throws DatatypeConfigurationException {
        ArrayList<TransactionDTO> outputTransactions = new ArrayList<>();
        for (Map<Object, Object> row : table.asMaps(String.class, String.class)) {
            TransactionDTO transaction = new TransactionDTO();
            transaction.setAmount(new BigDecimal((String) row.get("amount")));
            transaction.setBalance(new BigDecimal((String) row.get("balance")));
            transaction.setCreditor((String) row.get("creditor"));
            transaction.setDebtor((String) row.get("debtor"));
            transaction.setDescription((String) row.get("description"));
            XMLGregorianCalendar calendar = DatatypeFactory.newInstance().newXMLGregorianCalendar((String) row.get("time"));
            transaction.setTime(calendar);
            transaction.setToken((String) row.get("token"));
            outputTransactions.add(transaction);
        }
        Assertions.assertEquals(outputTransactions.toString(), result.join().toString());
    }

    @When("the customer {string} requests their transactions between {string} and {string}")
    public void theCustomerRequestsTheirTransactionsBetweenAnd(String customerId, String beg, String end) {
        this.customerId = customerId;
        new Thread(() -> {try {
            result.complete(rr.requestAllCustomerTransactionsBetween(customerId, beg, end));
        } catch (Exception e) {
            throw new Error(e);
        }}).start();
    }

    @Then("the customers transactions are displayed between two days")
    public void theCustomersTransactionsAreDisplayedBetweenTwoDays(DataTable table) throws DatatypeConfigurationException {
        ArrayList<TransactionDTO> outputTransactions = new ArrayList<>();
        for (Map<Object, Object> row : table.asMaps(String.class, String.class)) {
            TransactionDTO transaction = new TransactionDTO();
            transaction.setAmount(new BigDecimal((String) row.get("amount")));
            transaction.setBalance(new BigDecimal((String) row.get("balance")));
            transaction.setCreditor((String) row.get("creditor"));
            transaction.setDebtor((String) row.get("debtor"));
            transaction.setDescription((String) row.get("description"));
            XMLGregorianCalendar calendar = DatatypeFactory.newInstance().newXMLGregorianCalendar((String) row.get("time"));
            transaction.setTime(calendar);
            transaction.setToken((String) row.get("token"));
            outputTransactions.add(transaction);
        }
        Assertions.assertEquals(outputTransactions.toString(), result.join().toString());
    }
}
