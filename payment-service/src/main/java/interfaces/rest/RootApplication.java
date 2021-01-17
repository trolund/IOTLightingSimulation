package interfaces.rest;

import interfaces.rabbitmq.token.RabbitMQTokenAdapterFactory;
import interfaces.rabbitmq.payment.RabbitMQPaymentAdapterFactory;
import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Info;

import javax.enterprise.event.Observes;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @primary-author Troels (s161791)
 * @co-author Daniel (s151641)
 */
@OpenAPIDefinition(
        info = @Info(
                title = "Group 11 - payment-service API",
                version = "3.0.3"
        ))
@ApplicationPath("/api/v1")
public class RootApplication extends Application {

    private final static Logger LOGGER = Logger.getLogger(RootApplication.class.getName());

    public RootApplication() {
        super();
    }

    void onStart(@Observes StartupEvent ev) {
        LOGGER.info("The application is starting...");
        try {
            // starting the RabbitMQ listeners
            new RabbitMQPaymentAdapterFactory().getService();
            new RabbitMQTokenAdapterFactory().getService();
            LOGGER.log(Level.INFO, "RabbitMQ listening...");
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.log(Level.SEVERE, "RabbitMQ failed to start: " + e.getMessage());
        }
    }

    void onStop(@Observes ShutdownEvent ev) {
        LOGGER.info("The application is stopping...");
    }

}