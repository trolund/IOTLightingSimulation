package cucumber.steps;

import dto.TransactionDTO;
import interfaces.ReportReceiver;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.PendingException;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import messaging.Event;
import messaging.EventSender;
import org.junit.jupiter.api.Assertions;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AdminSteps {
    ReportReceiver rr;
    Event event;
    List<TransactionDTO> inputTransactions = new ArrayList<>();
    List<TransactionDTO> outputTransactions = new ArrayList<>();

    public AdminSteps() {
        rr = new ReportReceiver(new EventSender() {
            @Override
            public void sendEvent(Event ev) throws Exception {
                event = ev;
            }
        });
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
            transaction.setTokenId((String) row.get("token"));
            inputTransactions.add(transaction);
        }
    }

    @When("a request to see all transactions is made")
    public void aRequestToSeeAllTransactionsIsMade() throws Exception {
        Object[] output = new Object[1];
        output[0] = inputTransactions;
        rr.receiveEvent(new Event("requestAllTransactions", output));

    }

    @Then("the list of transactions is shown")
    public void theListOfTransactionsIsShown() {
        Assertions.assertEquals(inputTransactions, event.getArguments()[0]);
    }

    @When("a request for summary is made")
    public void aRequestForSummaryIsMade() {
        throw new PendingException();
    }

    @Then("a summary is made based on the transactions")
    public void aSummaryIsMadeBasedOnTheTransactions() {
        throw new PendingException();
    }
}
