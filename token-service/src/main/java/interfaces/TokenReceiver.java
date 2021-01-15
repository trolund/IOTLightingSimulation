package interfaces;

import domain.Token;
import exceptions.TokenNotFoundException;
import messaging.Event;
import messaging.EventReceiver;
import messaging.EventSender;
import services.TokenService;
import services.interfaces.ITokenService;

public class TokenReceiver implements EventReceiver {
    ITokenService rs = new TokenService();

    EventSender eventSender;

    public TokenReceiver(EventSender eventSender) {
        this.eventSender = eventSender;
    }

    @Override
    public void receiveEvent(Event event) throws Exception {
        System.out.println("handling event: "+event);
        if (event.getEventType().equals("requestTokens")) {
            try {
                String customerId = rs.requestTokens((String) event.getArguments()[0], (Integer) event.getArguments()[1]);
                eventSender.sendEvent(new Event("TokenRequestSuccessful", new Object[]{customerId}));
            } catch (TokenNotFoundException e) {
                eventSender.sendEvent(new Event("TokenRequestFailed", new Object[]{e.getMessage().split(" ")[1]})); // TODO: Extract this
            }
        }
        if (event.getEventType().equals("getToken")) {
            try {
                Token token = rs.getToken((String) event.getArguments()[0]);
                eventSender.sendEvent(new Event("GetTokenSuccessful", new Object[]{token.getId(), event.getArguments()[0]}));
            } catch (Exception e) {
                eventSender.sendEvent(new Event("GetTokenFailed", new Object[]{e.getMessage().split(" ")[1]}));
            }
        }
        if (event.getEventType().equals("validateToken")) {
            try {
                Token token = rs.validateToken((String) event.getArguments()[0]);
                eventSender.sendEvent(new Event("TokenValidationSuccessful", new Object[]{token.getId()}));
            } catch (Exception e) {
                eventSender.sendEvent(new Event("TokenValidationFailed", new Object[]{e.getMessage().split(" ")[1]}));
            }
        }
        if (event.getEventType().equals("deleteCustomer")) {
            try {
                String customerId = rs.deleteCustomer((String) event.getArguments()[0]);
                eventSender.sendEvent(new Event("CustomerDeletionSuccessful", new Object[]{customerId}));
            } catch (Exception e) {
                eventSender.sendEvent(new Event("CustomerDeletionFailed", new Object[]{e.getMessage().split(" ")[1]}));
            }
        }
    }
}
