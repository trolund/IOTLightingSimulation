package interfaces.rest;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import services.interfaces.IPaymentService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @author Troels (s161791)
 * UserNotFoundException to use when a user cannot be found.
 */

@Tag(ref = "Transactions")
@Path("/transactions")
public class TransactionResource {

    @Inject
    IPaymentService ps;

    /**
     * Get all transactions for a account
     * @param id - account GUID
     * @Param amount - amount of money to be payed.
     */
    @Operation(summary = "Get all transactions for a account")
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTransactions(@PathParam("id") String id) {
        try {
            return Response
                    .status(Response.Status.OK)
                    .entity(ps.getTransactions(id))
                    .build();
        } catch (Exception e){
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .build();
        }
    }

    /**
     * Get the latest transaction for a account
     * @param id - account GUID
     */
    @Operation(summary = "Get the latest transaction for a account")
    @GET
    @Path("{id}/latest")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLatestTransactions(@PathParam("id") String id) {
        try {
            return Response
                    .status(Response.Status.OK)
                    .entity(ps.getLatestTransaction(id))
                    .build();
        } catch (Exception e){
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .build();
        }
    }
}
