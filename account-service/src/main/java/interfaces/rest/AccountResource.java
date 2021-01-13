package interfaces.rest;

import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import services.interfaces.IAccountService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import domain.UserAccount;
import exceptions.*;

@Tag(ref = "AccountResource")
@Path("/users")
public class AccountResource {

    @Inject
    IAccountService service;

    @Tag(ref = "retrieve a single user by id")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Response getUser(@PathParam("id") String id) {

        /* TODO: userAccount =  service.getUser(id);
         * using dummy user to pass compilation
         */

        UserAccount userAccount = new UserAccount("Bjarne", "Ivertsen", "123456-7890");
        userAccount.setId("88");

        try{
            return Response
                    .status(Response.Status.OK)
                    .entity(userAccount)
                    .build();
        // TODO: add correct exception
        } catch (Exception e) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage())
                    .build();
        }
    }


    @Tag(ref = "register a user")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response registerUser(@QueryParam("firstName") String firstName,
                                 @QueryParam("lastName") String lastName,
                                 @QueryParam("cprNumber") String cprNumber) {
        try {
            // TODO: service.createUser(firstName, lastName, cprNumber);
            return Response
                    .ok()
                    .build();
        // TODO: add correct exception
        } catch (Exception e) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage())
                    .build();
        }

    }


    @Tag(ref = "retrieve user by cpr number")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{cprNumber}")
    public Response getUserByCpr(@QueryParam("cprNumber") String cprNumber) {
        // dummy account to pass compilation
        UserAccount userAccount = new UserAccount("Dummy", "Account", cprNumber);

        try {
            // TODO: service.getUserByCpr(cprNumber);
            return Response
                    .ok()
                    .entity(userAccount)
                    .build();
            // add correct exception
        } catch (Exception e) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage())
                    .build();
        }
    }

    @Tag(ref = "retrieve all users")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsers() {
        try {
            // TODO: service.getUsers();
            return Response
                    .ok()
                    .build();
        } catch (Exception e) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage())
                    .build();
        }
    }
}
