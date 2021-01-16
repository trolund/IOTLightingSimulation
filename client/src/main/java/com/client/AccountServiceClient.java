package com.client;

import dto.UserAccountDTO;
import dto.UserRegistrationDTO;

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
        baseUrl = client.target("http://localhost:8082/api/v1/");
    }

    public String registerUser(UserRegistrationDTO registrationDTO) {
        Response r = baseUrl.path("users")
                .request()
                .post(Entity.entity(registrationDTO, MediaType.APPLICATION_JSON_TYPE));
        return r.readEntity(String.class);
    }

    public UserAccountDTO getUserById(String userId) {
        return baseUrl.path("users/" + userId)
                .request()
                .get(new GenericType<>() {
                });
    }

    public UserAccountDTO getUserByCpr(String cpr) {
        return baseUrl.path("users/by-cpr/" + cpr)
                .request()
                .get(new GenericType<>() {
                });
    }

    public boolean updateUser(UserAccountDTO userAccount) {
        Response r = baseUrl.path("users/" + userAccount.getCprNumber())
                .request()
                .post(Entity.entity(userAccount, MediaType.APPLICATION_JSON_TYPE));
        return r.getStatus() == Response.Status.OK.getStatusCode();
    }

    public List<UserAccountDTO> getAllUsers() {
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