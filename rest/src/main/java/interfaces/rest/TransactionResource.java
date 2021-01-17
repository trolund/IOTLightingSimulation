package interfaces.rest;

import dto.TransactionDTO;
import exceptions.account.AccountNotFoundException;
import exceptions.transaction.TransactionNotFoundException;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import services.interfaces.ITransactionService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * @primary-author Daniel (s151641)
 * @co-author Troels (s161791)
 */
@Tag(ref = "Transaction")
@Path("/transaction")
public class TransactionResource {

    @Inject
    ITransactionService service;

    /**
     * Get all transactions for a account
     *
     * @param id - account GUID
     */
    @Operation(summary = "Get all transactions for a account")
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTransactions(@PathParam("id") String id) {
        try {
            List<TransactionDTO> transactions = service.getTransactions(id);
            return Response
                    .ok()
                    .entity(transactions)
                    .build();
        } catch (AccountNotFoundException e) {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .build();
        }
    }

    /**
     * Get the latest transaction for a account
     *
     * @param id - account GUID
     */
    @Operation(summary = "Get the latest transaction for an account")
    @GET
    @Path("{id}/latest")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLatestTransaction(@PathParam("id") String id) {
        try {
            TransactionDTO transaction = service.getLatestTransaction(id);
            return Response
                    .status(Response.Status.OK)
                    .entity(transaction)
                    .build();
        } catch (AccountNotFoundException | TransactionNotFoundException e) {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .build();
        }
    }

}