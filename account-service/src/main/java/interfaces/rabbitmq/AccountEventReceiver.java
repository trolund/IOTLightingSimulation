package interfaces.rabbitmq;

import dto.UserAccountDTO;
import dto.UserRegistrationDTO;
import messaging.Event;
import messaging.EventReceiver;
import messaging.EventSender;
import services.interfaces.IAccountService;

import javax.inject.Inject;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

// authors: help from group
public class AccountEventReceiver implements EventReceiver {

    private final static Logger LOGGER = Logger.getLogger(AccountEventReceiver.class.getName());
    private final EventSender eventSender;

    @Inject
    IAccountService service;

    public AccountEventReceiver(EventSender eventSender) {
        this.eventSender = eventSender;
    }

    @Override
    public void receiveEvent(Event eventIn) throws Exception {

        switch (eventIn.getEventType()) {
            case "Register":
                try {
                    UserRegistrationDTO userRegistrationDTO = (UserRegistrationDTO) eventIn.getArguments()[0];
                    String internalId = service.register(userRegistrationDTO);
                    Event eventOut = new Event("RegisterSuccessful", new Object[]{internalId});
                    eventSender.sendEvent(eventOut);
                } catch (Exception e) {
                    Event eventOut = new Event("RegisterFailed", new Object[]{e.getMessage()});
                    eventSender.sendEvent(eventOut);
                }
                break;
            case "GetAccount":
                try {
                    String id = (String) eventIn.getArguments()[0];
                    UserAccountDTO userAccountDTO = service.get(id);
                    Event eventOut = new Event("GetAccountSuccessful", new Object[]{userAccountDTO});
                    eventSender.sendEvent(eventOut);
                } catch (Exception e) {
                    Event eventOut = new Event("GetAccountFailed", new Object[]{e.getMessage()});
                    eventSender.sendEvent(eventOut);
                }
                break;
            case "GetAccountByCpr":
                try {
                    String cpr = (String) eventIn.getArguments()[0];
                    UserAccountDTO userAccountDTO = service.getByCpr(cpr);
                    Event eventOut = new Event("GetAccountByCprSuccessful", new Object[]{userAccountDTO});
                    eventSender.sendEvent(eventOut);
                } catch (Exception e) {
                    Event eventOut = new Event("GetAccountByCprFailed", new Object[]{e.getMessage()});
                    eventSender.sendEvent(eventOut);
                }
                break;
            case "RetireAccount":
                try {
                    String id = (String) eventIn.getArguments()[0];
                    service.retireAccount(id);
                    Event eventOut = new Event("RetireAccountSuccessful");
                    eventSender.sendEvent(eventOut);
                } catch (Exception e) {
                    Event eventOut = new Event("RetireAccountFailed", new Object[]{e.getMessage()});
                    eventSender.sendEvent(eventOut);
                }
                break;
            case "RetireAccountByCpr":
                try {
                    String cpr = (String) eventIn.getArguments()[0];
                    service.retireAccountByCpr(cpr);
                    Event eventOut = new Event("RetireAccountByCprSuccessful");
                    eventSender.sendEvent(eventOut);
                } catch (Exception e) {
                    Event eventOut = new Event("RetireAccountByCprFailed", new Object[]{e.getMessage()});
                    eventSender.sendEvent(eventOut);
                }
                break;
            case "GetAllAccounts":
                try {
                    List<UserAccountDTO> userAccountDTOs = service.getAll();
                    Event eventOut = new Event("GetAllAccountsSuccessful", new Object[]{userAccountDTOs});
                    eventSender.sendEvent(eventOut);
                } catch (Exception e) {
                    Event eventOut = new Event("GetAllAccountsFailed", new Object[]{e.getMessage()});
                    eventSender.sendEvent(eventOut);
                }
                break;
            default:
                LOGGER.log(Level.WARNING, "Ignored event with type: " + eventIn.getEventType() + ". Event: " + eventIn.toString());
                break;
        }
    }
}
