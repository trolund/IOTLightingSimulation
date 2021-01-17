package interfaces.rest;

import dto.Token;
import exceptions.EventFailedException;
import exceptions.SendEventFailedException;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import services.TokenEventService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Tag(ref = "TokenResource")
@Path("/token")
public class TokenResource {

    @Inject
    TokenEventService service;

    @Tag(ref = "requestTokens")
    @POST
    public Response requestTokens(@QueryParam("id") String id,
                                  @QueryParam("amount") int amount) {
        try {
            service.sendRequestTokensEvent(id, amount);
            return Response.ok().build();
        } catch (SendEventFailedException e) {
            throw new InternalServerErrorException(e.getMessage());
        } catch (EventFailedException e) {
            throw new BadRequestException(e.getMessage());
        }

    }

    @Tag(ref = "getToken")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Response getToken(@PathParam("id") String id) {
        try {
            Token token = service.sendGetTokenEvent(id);
            return Response.ok().entity(token).build();
        } catch (SendEventFailedException e) {
            throw new InternalServerErrorException(e.getMessage());
        } catch (EventFailedException e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    @Tag(ref = "retireCustomerTokens")
    @DELETE
    @Path("{id}")
    public Response retireCustomerTokens(@PathParam("id") String id) {
        try {
            service.sendRetireCustomerTokensEvent(id);
            return Response.ok().build();
        } catch (SendEventFailedException e) {
            throw new InternalServerErrorException(e.getMessage());
        } catch (EventFailedException e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    /*
    @Tag(ref = "validateToken")
    @POST
    @Path("validate/{tokenId}")
    public Response validateToken(@PathParam("tokenId") String tokenId) {
        try {
            service.validateToken(tokenId);
            return Response
                    .status(Response.Status.OK)
                    .build();
        } catch (CustomerNotFoundException | TokenNotFoundException | InvalidTokenException e) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage())
                    .build();
        }
    }
     */

}