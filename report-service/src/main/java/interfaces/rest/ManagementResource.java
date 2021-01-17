package interfaces.rest;

import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import services.interfaces.IReportService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.datatype.DatatypeConfigurationException;
import java.text.ParseException;

/**
 * @author Troels (s161791)
 */

@Tag(ref = "ManagementResource")
@Path("/management")
public class ManagementResource {

    @Inject
    IReportService service;

    @Tag(ref = "All transactions and summery")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response allTransactions() {
            return Response.ok().entity(service.getSummary()).build();
    }

    @Tag(ref = "Get costumer report for a given period")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("report/costumer/{costumerId}")
    public Response customerReport(@PathParam("costumerId") String costumerId, @QueryParam("start") String startDate, @QueryParam("end") String endDate ) {
        try {
            return Response.ok().entity(service.customerReport(costumerId, startDate, endDate)).build();
        } catch (DatatypeConfigurationException e) {
            e.printStackTrace();
            throw new BadRequestException(e.getMessage());
        } catch (ParseException e) {
            e.printStackTrace();
            throw new BadRequestException(e.getMessage());
        }
    }

    @Tag(ref = "Get merchant report for a given period")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("report/merchant/{merchantId}")
    public Response merchantReport(@PathParam("merchantId") String merchantId, @QueryParam("start") String startDate, @QueryParam("end") String endDate ) {
        try {
            return Response.ok().entity(service.merchantReport(merchantId, startDate, endDate)).build();
        } catch (DatatypeConfigurationException e) {
            e.printStackTrace();
            throw new BadRequestException(e.getMessage());
        } catch (ParseException e) {
            e.printStackTrace();
            throw new BadRequestException(e.getMessage());
        }
    }


}