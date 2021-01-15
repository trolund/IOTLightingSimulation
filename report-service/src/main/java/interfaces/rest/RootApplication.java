package interfaces.rest;

import dto.TransactionDTO;
import exceptions.TransactionException;
import interfaces.rabbitmq.ReportFactory;
import interfaces.rabbitmq.TransactionFactory;
import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import services.ReportReceiverService;
import services.ReportService;
import services.TransactionSpyService;

import javax.enterprise.event.Observes;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigDecimal;
import java.util.logging.Logger;

@OpenAPIDefinition(
        info = @Info(
                title = "Group 11 - report-service API",
                version = "3.0.3"
        ))

@ApplicationPath("/api/v1")
public class RootApplication extends Application {

    private final static Logger LOGGER = Logger.getLogger(RootApplication.class.getName());

    public RootApplication() {
        super();
    }

    void onStart(@Observes StartupEvent ev) throws DatatypeConfigurationException, TransactionException {
        ReportService reportService = new ReportService();
        ReportReceiverService reportReceiverService = new ReportFactory().getService(reportService);
        TransactionSpyService transactionSpyService = new TransactionFactory().getService(reportService);
        XMLGregorianCalendar calendar = DatatypeFactory.newInstance().newXMLGregorianCalendar("2021-01-15");
        reportService.addToRepo(new TransactionDTO(new BigDecimal(100), new BigDecimal(1000), "1234", "2345", "test", calendar));
        LOGGER.info("The application is starting...");
    }

    void onStop(@Observes ShutdownEvent ev) {
        LOGGER.info("The application is stopping...");
    }

}
