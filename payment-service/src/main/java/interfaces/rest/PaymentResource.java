package interfaces.rest;

import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import services.interfaces.IPaymentService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Tag(ref = "ExampleResource")
@Path("/payment")
public class PaymentResource {

    @Inject
    IPaymentService ps;

    /**
     * Pay x amount to a merchant
     * @param cid - Customer id.
     * @param mid - Merchant id.
     * @Param amount - amount of money to be payed.
     */

    @Tag(ref = "Pay amount")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response pay(@QueryParam("cid") String cid,@QueryParam("mid") String mid,@QueryParam("amount") float amount) {
        return Response.ok().build();
    }

}