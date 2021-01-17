package com.client;

import dto.Token;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

public class TokenServiceClient {

    private final WebTarget baseUrl;

    public TokenServiceClient() {
        javax.ws.rs.client.Client client = ClientBuilder.newClient();
        baseUrl = client.target("http://localhost:8081/api/v1/");
    }

    public boolean requestTokens(String customerId, int amount) {
        Response r = baseUrl.path("token")
                .queryParam("customerId", customerId)
                .queryParam("amount", amount)
                .request()
                .post(null);
        return r.getStatus() == Response.Status.OK.getStatusCode();
    }

    public Token getToken(String customerId) {
        Response r = baseUrl.path("token/" + customerId).request().get();
        Token t = r.readEntity(Token.class);
        System.out.println("cus: " + customerId + " token" + t);
        return t;
    }

    public boolean validateToken(String tokenId) {
        Response r = baseUrl.path("token/validate/" + tokenId)
                .request()
                .post(null);
        System.out.println("Validate : " + tokenId);
        return r.getStatus() == Response.Status.OK.getStatusCode();
    }

    public boolean deleteCustomerTokens(String customerId) {
        Response r = baseUrl.path("token/" + customerId).request().delete();
        return r.getStatus() == Response.Status.OK.getStatusCode();
    }

}