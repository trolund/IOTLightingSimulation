package interfaces.rest;

import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import services.interfaces.IReportService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Tag(ref = "ExampleResource")
@Path("/example")
public class ExampleResource {

    @Inject
    IReportService service;

    @Tag(ref = "hello")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return service.hello();
    }

}