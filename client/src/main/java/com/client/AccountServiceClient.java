package com.client;

import com.dto.User;

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

    public boolean registerUser(User user) {
        Response r = baseUrl.path("users")
                .request()
                .post(Entity.entity(user, MediaType.APPLICATION_JSON_TYPE));
        return r.getStatus() == Response.Status.OK.getStatusCode();
    }

    public User getUser(String userId) {
        return baseUrl.path("users/" + userId)
                .request()
                .get(new GenericType<>() {
                });
    }

    public User getUserByCpr(String cpr) {
        return baseUrl.path("users/cpr/" + cpr)
                .request()
                .get(new GenericType<>() {
                });
    }

    public boolean updateUser(User user) {
        Response r = baseUrl.path("users/" + user.getCprNumber())
                .request()
                .post(Entity.entity(user, MediaType.APPLICATION_JSON_TYPE));
        return r.getStatus() == Response.Status.OK.getStatusCode();
    }

    public List<User> getUsers() {
        return baseUrl.path("users")
                .request()
                .get(new GenericType<>() {
                });
    }

}