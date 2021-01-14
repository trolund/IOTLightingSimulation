package interfaces.rest;

import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import services.interfaces.IAccountService;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import domain.UserAccount;
import exceptions.*;

@Tag(ref = "AccountResource")
@Path("/users")
public class AccountResource {
    @Inject
    IAccountService service;

    @Tag(ref = "getUserById")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Response getUser(@PathParam("id") String id) {
        try {
            UserAccount user = service.getById(id);
            return Response.ok().entity(user).build();
        // TODO: add correct exception
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                           .entity(e.getMessage())
                           .build();
        }
    }

    @Tag(ref = "registerUser")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response registerUser(UserAccount user) {
        try {
            service.add(user);
            return Response.ok().build();
        } catch (Exception e) {
            // TODO: add correct exception
            return Response.status(Response.Status.BAD_REQUEST)
                           .entity(e.getMessage())
                           .build();
        }
    }

    @Tag(ref = "getUserByCpr")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/by-cpr/{cprNumber}")
    public Response getUserByCpr(@PathParam("cprNumber") String cprNumber) {
        try {
            UserAccount user = service.getByCpr(cprNumber);
            return Response.ok().entity(user).build();
        } catch (Exception e) {
            // TODO: throw correct exception
            return Response.status(Response.Status.BAD_REQUEST)
                           .entity(e.getMessage())
                           .build();
        }
    }

    @Tag(ref = "getAllUsers")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsers() {
        try {
            List<UserAccount> accounts = service.getAll();
            return Response.ok().entity(accounts).build();
        } catch (Exception e) {
            // TODO correct exception
            return Response.status(Response.Status.BAD_REQUEST)
                           .entity(e.getMessage())
                           .build();
        }
    }
}
