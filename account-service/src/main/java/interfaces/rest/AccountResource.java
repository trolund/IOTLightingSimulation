package interfaces.rest;

import dto.UserAccountDTO;
import dto.UserRegistrationDTO;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import services.interfaces.IAccountService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Tag(ref = "AccountResource")
@Path("/account")
public class AccountResource {

    @Inject
    IAccountService service;

    @Tag(ref = "getAccountById")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Response getAccount(@PathParam("id") String id) {
        try {
            UserAccountDTO user = service.get(id);
            return Response.ok().entity(user).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage())
                    .build();
        }
    }

    @Tag(ref = "register")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response register(UserRegistrationDTO user) {
        try {
            String userId = service.register(user);
            return Response.ok().entity(userId).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage())
                    .build();
        }
    }

    @Tag(ref = "getAccountByCpr")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/by-cpr/{cpr}")
    public Response getAccountByCpr(@PathParam("cpr") String cpr) {
        try {
            UserAccountDTO user = service.getByCpr(cpr);
            return Response.ok().entity(user).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage())
                    .build();
        }
    }

    @Tag(ref = "retireAccountByCpr")
    @DELETE
    @Path("/by-cpr/{cpr}")
    public Response retireAccountByCpr(@PathParam("cpr") String cpr) {
        try {
            service.retireAccountByCpr(cpr);
            return Response.ok().build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage())
                    .build();
        }
    }

    @Tag(ref = "getAllAccounts")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllAccounts() {
        try {
            List<UserAccountDTO> accounts = service.getAll();
            return Response.ok().entity(accounts).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage())
                    .build();
        }
    }

    @Tag(ref = "retireAccount")
    @DELETE
    @Path("{id}")
    public Response retireUser(@PathParam("id") String id) {
        try {
            service.retireAccount(id);
            return Response.ok().build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage())
                    .build();
        }
    }

}