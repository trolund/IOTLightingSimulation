package interfaces.rest;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import services.interfaces.IMerchantService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Tag(ref = "MerchantResource")
@Path("/merchant")
public class MerchantResource {

    @Inject
    IMerchantService service;

    /**
     * Pay x amount to a merchant
     * @param cid - Customer id.
     * @param mid - Merchant id.
     * @Param amount - amount of money to be payed.
     */
    @Operation(summary = "Pay x amount to a merchant")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response pay(@QueryParam("cid") String cid,
                        @QueryParam("mid") String mid,
                        @QueryParam("amount") int amount) {
            //ps.createTransaction(cid, mid, amount);
            return Response
                    .ok()
                    .build();
    }

    /**
     * Refund amount to customer
     * @param cid - Customer id.
     * @param mid - Merchant id.
     * @Param amount - amount of money to be refunded.
     */
    @Operation(summary = "Refund amount to customer")
    @POST
    @Path("refund")
    @Produces(MediaType.APPLICATION_JSON)
    public Response refund(@QueryParam("cid") String cid,
                           @QueryParam("mid") String mid,
                           @QueryParam("amount") int amount) {
        /*
        try{

            ps.refund(cid, mid, amount);
            return Response
                    .ok()
                    .build();
        }catch (CustomerException | MerchantException e){
            throw new NotFoundException(e.getMessage());
        }catch (TransactionException e){
            throw new BadRequestException(e.getMessage());
        }
         */
        return null;
    }

}