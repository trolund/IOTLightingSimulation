package interfaces.rabbitmq;

import domain.UserAccount;
import interfaces.rest.RootApplication;
import messaging.Event;
import messaging.EventReceiver;
import messaging.EventSender;
import services.interfaces.IAccountService;

import javax.inject.Inject;
import java.util.List;
import java.math.BigDecimal;

// authors: help from group
public class AccountEventReceiver implements EventReceiver {

    private final EventSender eventSender;

    @Inject
    IAccountService accountService;
    
    public AccountEventReceiver(EventSender eventSender) {
        this.eventSender = eventSender;
    }

    @Override
    public void receiveEvent(Event in) throws Exception {

        // TODO: add correct exceptions

        switch (in.getEventType()) {
            case "getAllUsers":
                try {
                    List<UserAccount> users = accountService.getAll();
                    eventSender.sendEvent(new Event("GetAllUsersSuccessful", new Object[]{users}));
                } catch (Exception e) {
                    eventSender.sendEvent(new Event("GetAllUsersFailed", new Object[]{e.getMessage()}));
                }
                break;

            case "getUserById":
                try {
                    UserAccount userAccount = accountService.getById((String) in.getArguments()[0]);
                    eventSender.sendEvent(new Event("GetUserByIdSuccessful", new Object[]{userAccount}));
                } catch (Exception e) {
                    eventSender.sendEvent(new Event("GetUserByIdFailed", new Object[]{e.getMessage()}));
                }
                break;

            case "getUserByCpr":
                try {
                    UserAccount userAccount = accountService.getByCpr((String) in.getArguments()[0]);
                    eventSender.sendEvent(new Event("GetUserByCprSuccesful", new Object[]{userAccount}));
                } catch (Exception e) {
                    eventSender.sendEvent(new Event("GetUserByCprFailed", new Object[]{e.getMessage()}));
                }
                break;

            case "registerUser":
                try {
                    UserAccount userAccount = (UserAccount) in.getArguments()[0];
                    BigDecimal balance = (BigDecimal) in.getArguments()[1];
                    accountService.add(userAccount);
                    eventSender.sendEvent(new Event("RegisterUserSuccessful", new Object[]{userAccount}));
                } catch (Exception e) {
                    eventSender.sendEvent(new Event("RegisterUserFailed", new Object[]{e.getMessage()}));
                }
                break;

            default:
                    eventSender.sendEvent(new Event("Event ignored. Type: " + in.getEventType() + " and Event: " + in.toString()));
                break;
        }
    }
}
