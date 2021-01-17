package com.client;

import dto.UserAccountDTO;
import dto.UserRegistrationDTO;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class AccountServiceClient {

    private final WebTarget baseUrl;

    public AccountServiceClient() {
        javax.ws.rs.client.Client client = ClientBuilder.newClient();
        baseUrl = client.target("http://localhost:8080/api/v1/");
    }

    public String registerAccount(UserRegistrationDTO registrationDTO) {
        Response r = baseUrl.path("account")
                .request()
                .post(Entity.entity(registrationDTO, MediaType.APPLICATION_JSON_TYPE));
        return r.readEntity(String.class);
    }

    public UserAccountDTO getAccount(String id) {
        Response r = baseUrl.path("account/" + id)
                .request()
                .get();

        if (r.getStatus() == 200) {
            return r.readEntity(new GenericType<>() {
            });
        }

        return null;
    }

    public UserAccountDTO getAccountByCpr(String cpr) {
        Response r = baseUrl.path("account/by-cpr/" + cpr)
                .request()
                .get();

        if (r.getStatus() == 200) {
            return r.readEntity(new GenericType<>() {
            });
        }

        return null;
    }

    public boolean retireAccount(String id) {
        Response r = baseUrl.path("account/" + id)
                .request()
                .delete(new GenericType<>() {
                });
        return r.getStatus() == Response.Status.OK.getStatusCode();
    }

}