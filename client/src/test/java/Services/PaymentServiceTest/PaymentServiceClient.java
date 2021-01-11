package Services.PaymentServiceTest;

import Services.PaymentServiceTest.dao.Account;
import Services.PaymentServiceTest.dao.Transaction;
import Services.PaymentServiceTest.dto.ErrorType;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * @author Troels (s161791)
 * client to Payment service test.
 */

public class PaymentServiceClient {

    WebTarget baseUrl;

    public PaymentServiceClient() {
        Client client = ClientBuilder.newClient();
        baseUrl = client.target("http://localhost:1617/");
    }

    public String pay(int amount, String cid, String mid) {
        Response r = baseUrl.path("payments").queryParam("cid", cid)
                                .queryParam("mid", mid)
                                .queryParam("amount", amount)
                                .request().post(null);

        if(r.getStatus() == 200) {
            return null;
        }else {
            return r.readEntity(new GenericType<ErrorType>() {}).errorMessage;
        }

    }

    public List<Transaction> getTransactionList(String accountId) {
        Response r = baseUrl.path("payments/transactions/" + accountId).request().get();
        return r.readEntity(new GenericType<List<Transaction>>() {});
    }

    public Account getAccount(String cpr){
        Response r = baseUrl.path("/account/cpr/" + cpr).request().get();
        return r.readEntity(new GenericType<Account>() {});
    }

    public Account getCustomer(String cpr){
        Response r = baseUrl.path("/account/cpr/" + cpr).request().get();
        return r.readEntity(new GenericType<Account>() {});
    }
}
