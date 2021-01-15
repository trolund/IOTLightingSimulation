package cucumber.steps;

import dto.TransactionDTO;
import services.ReportReceiverService;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import messaging.Event;
import org.junit.jupiter.api.Assertions;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class AdminSteps {
    ReportReceiverService rr;
    Event event;
    List<TransactionDTO> inputTransactions = new ArrayList<>();
    private final CompletableFuture<List<TransactionDTO>> result = new CompletableFuture<>();
    private final CompletableFuture<Map<String, BigDecimal>> summary = new CompletableFuture<>();

    public AdminSteps() {
        rr = new ReportReceiverService(ev -> event = ev);
    }

    @Given("a list of transactions")
    public void aListOfTransactions(DataTable table) throws Exception {
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
        rr.receiveEvent();
    }

    @When("a request to see all transactions is made")
    public void aRequestToSeeAllTransactionsIsMade() throws Exception {
        new Thread(() -> {try {
            result.complete(rr.requestAllTransactions());
        } catch (Exception e) {
            throw new Error(e);
        }}).start();
    }

    @Then("the list of transactions is shown")
    public void theListOfTransactionsIsShown() {
        Assertions.assertEquals(inputTransactions, result.join());
    }

    @When("a request for summary is made")
    public void aRequestForSummaryIsMade() {
        new Thread(() -> {try {
            summary.complete(rr.requestSummary());
        } catch (Exception e) {
            throw new Error(e);
        }}).start();
    }

    @Then("a summary is made based on the transactions")
    public void aSummaryIsMadeBasedOnTheTransactions(DataTable table) {
        Assertions.assertEquals(table.asMaps(String.class, BigDecimal.class).get(0), summary.join());
    }

    @Then("an event {string} has been sent")
    public void anEventHasBeenSent(String eventType) {
        Assertions.assertEquals(eventType,event.getEventType());
    }

    @When("an event {string} has been sent back")
    public void anEventHasBeenSentBack(String eventType) throws Exception {
        rr.receiveEvent(new Event(eventType, new Object[] {inputTransactions}));
    }
}
