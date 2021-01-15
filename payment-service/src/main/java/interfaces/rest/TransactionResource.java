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
 * @primary-author Daniel (s151641)
 * @co-author Troels (s161791)
 */
@Tag(ref = "Transactions")
@Path("/transactions")
public class TransactionResource {

    @Inject
    IPaymentService service;

    /**
     * Get all transactions for a account
     *
     * @param accountId - account GUID
     */
    @Operation(summary = "Get all transactions for a account")
    @GET
    @Path("{accountId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTransactions(@PathParam("accountId") String accountId) {
        try {
            return Response
                    .ok()
                    .entity(service.getTransactions(accountId))
                    .build();
        } catch (Exception e) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .build();
        }
    }

    /**
     * Get the latest transaction for a account
     *
     * @param accountId - account GUID
     */
    @Operation(summary = "Get the latest transaction for a account")
    @GET
    @Path("{accountId}/latest")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLatestTransactions(@PathParam("accountId") String accountId) {
        try {
            return Response
                    .status(Response.Status.OK)
                    .entity(service.getLatestTransaction(accountId))
                    .build();
        } catch (Exception e) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .build();
        }
    }
}