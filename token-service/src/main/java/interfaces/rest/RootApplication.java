package interfaces.rest;

import interfaces.TokenReceiver;
import interfaces.rabbitmq.RabbitMQReceiver;
import interfaces.rabbitmq.RabbitMQSender;
import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Info;

import javax.enterprise.event.Observes;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.logging.Level;
import java.util.logging.Logger;

@OpenAPIDefinition(
        info = @Info(
                title = "Group 11 - token-service API",
                version = "3.0.3"
        ))

@ApplicationPath("/api/v1")
public class RootApplication extends Application {

    private final static Logger LOGGER = Logger.getLogger(RootApplication.class.getName());
    private RabbitMQReceiver receiver = new RabbitMQReceiver(new TokenReceiver(new RabbitMQSender()));

    public RootApplication() {
        super();
    }

    void onStart(@Observes StartupEvent ev) {
        LOGGER.info("The application is starting...");
        try {
            RabbitMQSender sender = new RabbitMQSender();
            sender.initConnection();
            TokenReceiver tokenReceiver = new TokenReceiver(sender);
            RabbitMQReceiver receiver = new RabbitMQReceiver(tokenReceiver);
            receiver.initConnection();
            LOGGER.log(Level.INFO, "RabbitMQ initConnection() started successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.log(Level.SEVERE, "RabbitMQ initConnection() failed: " + e.getMessage());
        }
    }

    void onStop(@Observes ShutdownEvent ev) {
        LOGGER.info("The application is stopping...");
    }

}
