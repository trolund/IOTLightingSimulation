package interfaces.rest;

import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import services.interfaces.IAccountService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Tag(ref = "AccountResource")
@Path("/account")
public class AccountResource {

    @Inject
    IAccountService service;

//    @Tag(ref = "hello")
//    @GET
//    @Produces(MediaType.TEXT_PLAIN)
//    public String hello() {
//        return service.hello();
//    }

//    @Tag(ref = "entity")
//    @GET
//    @Produces(MediaType.APPLICATION_JSON)
//    @Path("read")
//    public Response get() {
//        return Response
//                .ok()
//                .entity(service.readAccount())
//                .build();
//    }
//
}