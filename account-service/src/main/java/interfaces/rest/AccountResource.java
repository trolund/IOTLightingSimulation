package interfaces.rest;

import dto.UserAccountDTO;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import services.AccountService;
import services.interfaces.IAccountService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

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
            UserAccountDTO user = service.getById(id);
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
    @Produces(MediaType.TEXT_PLAIN)
    public Response registerUser(UserAccountDTO user) {
        try {
            String userId = service.add(user);
            return Response.ok().entity(userId).build();
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
            UserAccountDTO user = service.getByCpr(cprNumber);
            return Response.ok().entity(user).build();
        } catch (Exception e) {
            // TODO: throw correct exception
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage())
                    .build();
        }
    }

    @Tag(ref = "retireUserByCpr")
    @DELETE
    @Path("/by-cpr/{cprNumber}")
    public Response retireUserByCpr(@PathParam("cprNumber") String cpr) {
        try {

            service.retireAccountByCpr(cpr);
            return Response.ok().build();
            // TODO: add correct exception
        } catch (Exception e) {
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
            List<UserAccountDTO> accounts = service.getAll();
            return Response.ok().entity(accounts).build();
        } catch (Exception e) {
            // TODO correct exception
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage())
                    .build();
        }
    }

    @Tag(ref = "retireUser")
    @DELETE
    @Path("{id}")
    public Response retireUser(@PathParam("id") String id) {
        try {
            service.retireAccount(id);
            return Response.ok().build();
            // TODO: add correct exception
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage())
                    .build();
        }
    }

}