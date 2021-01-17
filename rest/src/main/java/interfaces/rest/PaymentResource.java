package interfaces.rest;

import dto.PaymentRequest;
import exceptions.account.AccountNotFoundException;
import exceptions.token.InvalidTokenException;
import exceptions.transaction.TransactionException;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import services.interfaces.IPaymentService;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @primary-author Troels (s161791)
 * @co-author Daniel (s151641)
 */
@Tag(ref = "Payment")
@Path("/payment")
public class PaymentResource {

    @Inject
    IPaymentService service;

    /**
     * Pay x amount to a merchant
     */
    @Operation(summary = "Pay x amount to a merchant")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response processPayment(PaymentRequest paymentRequest) {
        try {
            service.processPayment(paymentRequest);
            return Response
                    .ok()
                    .build();
        } catch (AccountNotFoundException e) {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .build();
        } catch (TransactionException | InvalidTokenException e) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .build();
        }
    }

    /**
     * Refund amount to customer
     */
    @Operation(summary = "Refund amount to customer")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("refund")
    public Response refund(PaymentRequest paymentRequest) {
        try {
            service.refund(paymentRequest);
            return Response
                    .ok()
                    .build();
        } catch (AccountNotFoundException e) {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .build();
        } catch (TransactionException | InvalidTokenException e) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .build();
        }
    }

}