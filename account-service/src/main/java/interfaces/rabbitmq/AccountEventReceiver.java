package interfaces.rabbitmq;

import dto.UserAccountDTO;
import messaging.Event;
import messaging.EventReceiver;
import messaging.EventSender;
import services.interfaces.IAccountService;
import javax.inject.Inject;
import java.util.List;

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
                    List<UserAccountDTO> users = accountService.getAll();
                    eventSender.sendEvent(new Event("GetAllUsersSuccessful", new Object[]{users}));
                } catch (Exception e) {
                    eventSender.sendEvent(new Event("GetAllUsersFailed", new Object[]{e.getMessage()}));
                }
                break;

            case "getUserById":
                try {
                    UserAccountDTO userAccount = accountService.getById((String) in.getArguments()[0]);
                    eventSender.sendEvent(new Event("GetUserByIdSuccessful", new Object[]{userAccount}));
                } catch (Exception e) {
                    eventSender.sendEvent(new Event("GetUserByIdFailed", new Object[]{e.getMessage()}));
                }
                break;

            case "getUserByCpr":
                try {
                    UserAccountDTO userAccount = accountService.getByCpr((String) in.getArguments()[0]);
                    eventSender.sendEvent(new Event("GetUserByCprSuccesful", new Object[]{userAccount}));
                } catch (Exception e) {
                    eventSender.sendEvent(new Event("GetUserByCprFailed", new Object[]{e.getMessage()}));
                }
                break;

            case "registerUser":
                try {
                    UserAccountDTO userAccount = (UserAccountDTO) in.getArguments()[0];
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
