package com.client;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

public class AccountServiceClient {

    private final WebTarget baseUrl;

    public AccountServiceClient() {
        javax.ws.rs.client.Client client = ClientBuilder.newClient();
        baseUrl = client.target("http://localhost:8080/api/v1/");
    }

    public boolean registerUser(UserAccount userAccount) {
        Response r = baseUrl.path("users")
                .request()
                .post(Entity.entity(userAccount, MediaType.APPLICATION_JSON_TYPE));
        return r.getStatus() == Response.Status.OK.getStatusCode();
    }

    public UserAccount getUserById(String userId) {
        return baseUrl.path("users/" + userId)
                .request()
                .get(new GenericType<>() {
                });
    }

    public UserAccount getUserByCpr(String cpr) {
        return baseUrl.path("users/by-cpr/" + cpr)
                .request()
                .get(new GenericType<>() {
                });
    }

    public boolean updateUser(UserAccount userAccount) {
        Response r = baseUrl.path("users/" + userAccount.getCprNumber())
                .request()
                .post(Entity.entity(userAccount, MediaType.APPLICATION_JSON_TYPE));
        return r.getStatus() == Response.Status.OK.getStatusCode();
    }

    public List<UserAccount> getAllUsers() {
        return baseUrl.path("users")
                .request()
                .get(new GenericType<>() {
                });
    }

    public boolean retireUser(String userId) {
        Response r = baseUrl.path("users/" + userId)
                .request()
                .delete(new GenericType<>() {
                });
        return r.getStatus() == Response.Status.OK.getStatusCode();
    }

}