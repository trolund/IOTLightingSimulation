package com.client;

import com.dto.Token;
import com.dto.Transaction;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

public class PaymentServiceClient {

    private final WebTarget baseUrl;

    public PaymentServiceClient() {
        javax.ws.rs.client.Client client = ClientBuilder.newClient();
        baseUrl = client.target("http://localhost:8080/api/v1/");
    }

    public boolean processPayment(String customerId, String merchantId, Integer amount) {
        Response r = baseUrl.path("payment")
                .queryParam("customerId", customerId)
                .queryParam("merchantId", merchantId)
                .queryParam("amount", amount)
                .request()
                .post(null);
        return r.getStatus() == Response.Status.OK.getStatusCode();
    }

    public Transaction getLatestTransaction(String accountId) {
        Response r = baseUrl.path("transactions/" + accountId + "/latest").request().get();
        return r.readEntity(Transaction.class);
    }

}