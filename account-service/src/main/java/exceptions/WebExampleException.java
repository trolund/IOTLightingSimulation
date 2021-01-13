package exceptions;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

public class WebExampleException extends WebApplicationException {

    public WebExampleException(String message) {
        super(Response.status(500).entity(message).build());
    }

}
