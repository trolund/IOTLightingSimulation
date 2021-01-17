package services;

import dto.UserAccountDTO;
import dto.UserRegistrationDTO;
import exceptions.EventFailedException;
import exceptions.SendEventFailedException;
import messaging.Event;
import messaging.EventReceiver;
import messaging.EventSender;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @primary-author Daniel (s151641)
 * @co-author Troels (s161791)
 */
public class AccountEventService implements EventReceiver {

    private final static Logger LOGGER = Logger.getLogger(AccountEventService.class.getName());

    private final EventSender eventSender;
    private CompletableFuture<String> registerResult;
    private CompletableFuture<UserAccountDTO> getAccountResult;
    private CompletableFuture<UserAccountDTO> getAccountByCprResult;
    private CompletableFuture<Boolean> retireAccountResult;
    private CompletableFuture<Boolean> retireAccountByCprResult;
    private CompletableFuture<List<UserAccountDTO>> getAllAccountsResult;

    public AccountEventService(EventSender eventSender) {
        this.eventSender = eventSender;
    }

    @Override
    public void receiveEvent(Event event) {
        switch (event.getEventType()) {
            case "getLatestTransaction":
                break;
            case "getTransactions":
                break;
            default:
                LOGGER.log(Level.WARNING, "Ignored event with type: " + event.getEventType() + ". Event: " + event.toString());
                break;
        }
    }

    public String sendRegisterEvent(UserRegistrationDTO userRegistrationDTO)
            throws SendEventFailedException, EventFailedException {
        String eventType = "Register";
        Object[] arguments = new Object[]{userRegistrationDTO};
        Event event = new Event(eventType, arguments);
        registerResult = new CompletableFuture<>();

        try {
            eventSender.sendEvent(event);
            String id = registerResult.join();

            if (id == null) {
                throw new EventFailedException(eventType + " event has failed for account: " + userRegistrationDTO.toString());
            }

            return id;
        } catch (EventFailedException e) {
            throw new EventFailedException(e.getMessage());
        } catch (Exception e) {
            throw new SendEventFailedException("Failed to send " + eventType + " event with account: " + userRegistrationDTO.toString());
        }
    }

    public UserAccountDTO sendGetAccountEvent(String id)
            throws SendEventFailedException, EventFailedException {
        String eventType = "GetAccount";
        Object[] arguments = new Object[]{id};
        Event event = new Event(eventType, arguments);
        getAccountResult = new CompletableFuture<>();

        try {
            eventSender.sendEvent(event);
            UserAccountDTO userAccountDTO = getAccountResult.join();

            if (userAccountDTO == null) {
                throw new EventFailedException(eventType + " event has failed for account with id: " + id);
            }

            return userAccountDTO;
        } catch (EventFailedException e) {
            throw new EventFailedException(e.getMessage());
        } catch (Exception e) {
            throw new SendEventFailedException("Failed to send " + eventType + " event for account with id " + id);
        }
    }

    public UserAccountDTO sendGetAccountByCprEvent(String cpr)
            throws SendEventFailedException, EventFailedException {
        String eventType = "GetAccountByCpr";
        Object[] arguments = new Object[]{cpr};
        Event event = new Event(eventType, arguments);
        getAccountByCprResult = new CompletableFuture<>();

        try {
            eventSender.sendEvent(event);
            UserAccountDTO userAccountDTO = getAccountByCprResult.join();

            if (userAccountDTO == null) {
                throw new EventFailedException(eventType + " event has failed for account with cpr: " + cpr);
            }

            return userAccountDTO;
        } catch (EventFailedException e) {
            throw new EventFailedException(e.getMessage());
        } catch (Exception e) {
            throw new SendEventFailedException("Failed to send " + eventType + " event for cpr " + cpr);
        }
    }

    public void sendRetireAccountEvent(String id)
            throws SendEventFailedException, EventFailedException {
        String eventType = "RetireAccount";
        Object[] arguments = new Object[]{id};
        Event event = new Event(eventType, arguments);
        retireAccountResult = new CompletableFuture<>();

        try {
            eventSender.sendEvent(event);
            boolean isSuccessful = retireAccountResult.join();

            if (!isSuccessful) {
                throw new EventFailedException(eventType + " event has failed for account with id: " + id);
            }
        } catch (EventFailedException e) {
            throw new EventFailedException(e.getMessage());
        } catch (Exception e) {
            throw new SendEventFailedException("Failed to send retire account event with id " + id);
        }
    }

    public void sendRetireAccountByCprEvent(String cpr)
            throws SendEventFailedException, EventFailedException {
        String eventType = "RetireAccountByCpr";
        Object[] arguments = new Object[]{cpr};
        Event event = new Event(eventType, arguments);
        retireAccountByCprResult = new CompletableFuture<>();

        try {
            eventSender.sendEvent(event);
            boolean isSuccessful = retireAccountByCprResult.join();

            if (!isSuccessful) {
                throw new EventFailedException(eventType + " event has failed for account with cpr: " + cpr);
            }
        } catch (EventFailedException e) {
            throw new EventFailedException(e.getMessage());
        } catch (Exception e) {
            throw new SendEventFailedException("Failed to send retire account event with cpr " + cpr);
        }
    }

    public List<UserAccountDTO> sendGetAllAccountsEvent()
            throws SendEventFailedException, EventFailedException {
        String eventType = "GetAllAccounts";
        Event event = new Event(eventType);
        getAllAccountsResult = new CompletableFuture<>();

        try {
            eventSender.sendEvent(event);
            List<UserAccountDTO> userAccountDTOs = getAllAccountsResult.join();

            if (userAccountDTOs == null) {
                throw new EventFailedException(eventType + " event has failed");
            }

            return userAccountDTOs;
        } catch (EventFailedException e) {
            throw new EventFailedException(e.getMessage());
        } catch (Exception e) {
            throw new SendEventFailedException("Failed to send get all accounts event");
        }
    }

}