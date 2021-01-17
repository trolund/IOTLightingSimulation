package interfaces.rest;

import interfaces.rabbitmq.RabbitMqReceiver;
import interfaces.rabbitmq.RabbitMqSender;
import interfaces.rabbitmq.AccountEventReceiver;
import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Info;

import javax.enterprise.event.Observes;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.logging.Logger;

@OpenAPIDefinition(
        info = @Info(
                title = "Group 11 - account-service API",
                version = "3.0.3"
        ))
@ApplicationPath("/api/v1")
public class RootApplication extends Application {

    private final static Logger LOGGER = Logger.getLogger(RootApplication.class.getName());

    public RootApplication() {
        super();
    }

    void onStart(@Observes StartupEvent ev) {
        LOGGER.info("The application is starting..." + System.getenv("RABBITMQ_HOST"));
        try {
            RabbitMqSender s = new RabbitMqSender();
            LOGGER.info("RabbitMqSender up");
            AccountEventReceiver service = new AccountEventReceiver(s);
            LOGGER.info("AccountReceiver up");
            new RabbitMqReceiver(service).listen();
            LOGGER.info("RabbitMqReceiver up");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void onStop(@Observes ShutdownEvent ev) {
        LOGGER.info("The application is stopping...");
    }
}