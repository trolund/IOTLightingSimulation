package cucumber.steps;

import dto.TransactionDTO;
import infrastructure.bank.*;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;

public final class TestClient {

    private WebTarget baseUrl;
    private BankService bs;

    public TestClient() {
        Client client = ClientBuilder.newClient();
        baseUrl = client.target("http://localhost:8080/api/v1/");
        bs = new BankServiceService().getBankServicePort();
    }

    public boolean createTransaction(String cId, String mId, Integer amount) {
        Response r = baseUrl.path("payment").queryParam("cid", cId)
                .queryParam("mid", mId)
                .queryParam("amount", amount)
                .request().post(null);
        return r.getStatus() == Response.Status.OK.getStatusCode();
    }

    public String createAccount(String cpr, String firstName, String lastName, Integer balance) {
        User user = new User();
        user.setCprNumber(cpr);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        try {
            return bs.createAccountWithBalance(user, BigDecimal.valueOf(balance));
        } catch (BankServiceException_Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void retireAccount(String accountId) {
        try {
            bs.retireAccount(accountId);
        } catch (BankServiceException_Exception e) {
            e.printStackTrace();
        }
    }

    public Account getAccount(String accountId) {
        try {
            return bs.getAccount(accountId);
        } catch (BankServiceException_Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public TransactionDTO getLatestTransaction(String accountId){
        Response r = baseUrl.path("transactions/" + accountId + "/latest")
                .request().get();
        if(r.getStatus() == 200){
            return  r.readEntity(new GenericType<TransactionDTO>() {});
        }
        return null;
    }

}