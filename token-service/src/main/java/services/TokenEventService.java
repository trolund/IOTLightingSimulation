package services;

import dto.PaymentAccounts;
import dto.Token;
import exceptions.TokenNotFoundException;
import messaging.Event;
import messaging.EventReceiver;
import messaging.EventSender;
import services.interfaces.ITokenService;

public class TokenEventService implements EventReceiver {

    ITokenService rs;
    EventSender eventSender;

    public TokenEventService(EventSender eventSender) {
        this.eventSender = eventSender;
        this.rs = new TokenService();
    }

    @Override
    public void receiveEvent(Event event) throws Exception {
        System.out.println("handling event: " + event);
        if (event.getEventType().equals("RequestTokens")) {
            try {
                String customerId = rs.requestTokens((String) event.getArguments()[0], (Integer) event.getArguments()[1]);
                eventSender.sendEvent(new Event("RequestTokensSuccessful", new Object[]{customerId}));
            } catch (TokenNotFoundException e) {
                eventSender.sendEvent(new Event("RequestTokensFailed", new Object[]{e.getMessage().split(" ")[1]})); // TODO: Extract this
            }
        }
        if (event.getEventType().equals("GetToken")) {
            try {
                Token token = rs.getToken((String) event.getArguments()[0]);
                eventSender.sendEvent(new Event("GetTokenSuccessful", new Object[]{token.getId(), event.getArguments()[0]}));
            } catch (Exception e) {
                eventSender.sendEvent(new Event("GetTokenFailed", new Object[]{e.getMessage().split(" ")[1]}));
            }
        }
        /*
        if (event.getEventType().equals("ValidateToken")) {
            try {
                Token token = rs.validateToken((String) event.getArguments()[0]);
                eventSender.sendEvent(new Event("TokenValidationSuccessful", new Object[]{token.getId()}));
            } catch (Exception e) {
                logger.severe("validateToken threw an exception: " + e.getMessage());
                e.printStackTrace();
                eventSender.sendEvent(new Event("TokenValidationFailed", new Object[]{e.getMessage().split(" ")[1]}));
            }
        }
         */
        if (event.getEventType().equals("RetireCustomerTokens")) {
            try {
                String customerId = rs.deleteCustomer((String) event.getArguments()[0]);
                eventSender.sendEvent(new Event("CustomerRetirementSuccessful", new Object[]{customerId}));
            } catch (Exception e) {
                eventSender.sendEvent(new Event("CustomerRetirementFailed", new Object[]{e.getMessage().split(" ")[1]}));
            }
        }
        if (event.getEventType().equals("PaymentAccountsSuccessful")) {
            PaymentAccounts paymentAccounts = (PaymentAccounts) event.getArguments()[0];
            try {
                Token token = rs.validateToken(paymentAccounts.getToken());

                if (token == null) {
                    eventSender.sendEvent(new Event("TokenValidationFailed", new Object[]{"Token is null"}));
                    return;
                }

                Event eventOut = new Event("TokenValidationSuccessful", new Object[]{paymentAccounts});
                eventSender.sendEvent(eventOut);
            } catch (Exception e) {
                eventSender.sendEvent(new Event("TokenValidationFailed", new Object[]{e.getMessage().split(" ")[1]}));
            }
        }
    }

}