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
        /*
         * TODO what is the correct url here? how do we access another
         * docker-container from within another?
         */
        baseUrl = client.target("http://account:8082/api/v1/");
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
