package com.client;

import dto.PaymentRequest;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class PaymentServiceClient {

    private final WebTarget baseUrl;

    public PaymentServiceClient() {
        javax.ws.rs.client.Client client = ClientBuilder.newClient();
        baseUrl = client.target("http://localhost:8080/api/v1/");
    }

    public boolean processPayment(PaymentRequest paymentRequest) {
        Response r = baseUrl.path("payment")
                .request()
                .post(Entity.entity(paymentRequest, MediaType.APPLICATION_JSON_TYPE));
        return r.getStatus() == Response.Status.OK.getStatusCode();
    }

}