package services;

import dto.UserAccountDTO;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

public class AccountServiceStub {

    private final WebTarget baseUrl;

    public AccountServiceStub () {
        javax.ws.rs.client.Client client = ClientBuilder.newClient();
        baseUrl = client.target("http://localhost:8082/api/v1/");
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

}
