package services;

import dto.PaymentAccounts;
import dto.TransactionDTO;
import exceptions.SendEventFailedException;
import infrastructure.bank.BankService;
import infrastructure.bank.BankServiceException_Exception;
import infrastructure.bank.BankServiceService;
import messaging.Event;
import messaging.EventReceiver;
import messaging.EventSender;

import java.math.BigDecimal;
import java.util.Date;
import java.util.logging.Logger;

/**
 * @primary-author Daniel (s151641)
 * @co-author Troels (s161791)
 */
public class PaymentEventService implements EventReceiver {

    private final static Logger LOGGER = Logger.getLogger(PaymentEventService.class.getName());
    private final EventSender eventSender;
    private final BankService bs = new BankServiceService().getBankServicePort();

    public PaymentEventService(EventSender eventSender) {
        this.eventSender = eventSender;
    }

    @Override
    public void receiveEvent(Event event) {
        switch (event.getEventType()) {
            case "PaymentAccountsFailed":
                // send event ud med transactionFailed
                break;
            case "TokenValidationSuccessful":
                try {
                    receivedTokenValidationSuccessful(event);
                } catch (SendEventFailedException e) {
                    LOGGER.severe(e.getMessage());
                    e.printStackTrace();
                }
                break;
            case "TokenValidationFailed":
                // send event ud med transactionFailed
                break;
            default:
                LOGGER.info("Ignored event with type: " + event.getEventType() + ". Event: " + event.toString());
                break;
        }
    }

    private void receivedTokenValidationSuccessful(Event event) throws SendEventFailedException {
        PaymentAccounts paymentAccounts = (PaymentAccounts) event.getArguments()[0];

        String customerId = paymentAccounts.getCustomer().getId();
        String merchantId = paymentAccounts.getMerchant().getId();
        String customerBankId = paymentAccounts.getCustomer().getBankAccount().getId();
        String merchantBankId = paymentAccounts.getMerchant().getBankAccount().getId();
        String token = paymentAccounts.getToken();
        int amount = paymentAccounts.getAmount();

        String desc = "Transaction between Customer (Id: " + customerId + ") " +
                " (BankId: " + customerBankId + ")" +
                " and Merchant (Id: " + merchantId + ")" +
                " (BankId: " + merchantBankId + " for amount " + amount +
                " with token " + token;

        try {
            bs.transferMoneyFromTo(customerBankId, merchantBankId, BigDecimal.valueOf(paymentAccounts.getAmount()), desc);

            BigDecimal newCustomerBalance = BigDecimal.valueOf(paymentAccounts.getCustomer().getBankAccount().getBalance().intValue() - amount);
            TransactionDTO dto = new TransactionDTO(BigDecimal.valueOf(amount), newCustomerBalance, merchantId, customerId, desc, new Date(), true);

            Event eventOut = new Event("PaymentSuccessful", new Object[]{dto});
            eventSender.sendEvent(eventOut);
        } catch (BankServiceException_Exception e) {
            TransactionDTO dto = new TransactionDTO(BigDecimal.valueOf(amount), paymentAccounts.getCustomer().getBankAccount().getBalance(), merchantId, customerId, desc, new Date(), false);
            Event eventOut = new Event("PaymentFailed", new Object[]{dto});
            try {
                eventSender.sendEvent(eventOut);
            } catch (Exception exception) {
                throw new SendEventFailedException(e.getMessage());
            }
        } catch (Exception e) {
            throw new SendEventFailedException(e.getMessage());
        }
    }


    public void sendTransactionDone(TransactionDTO dto, boolean successful) throws Exception {
        Event event = null;
        if (successful) {
            event = new Event("TransactionSuccessful", new Object[]{dto});
        } else {
            event = new Event("TransactionFailed", new Object[]{dto});
        }
        eventSender.sendEvent(event);
    }

    /*
    private void getLatestTransaction(Event event) {
        Object[] arguments;
        Event eventToSend;

        String accountId = (String) event.getArguments()[0];

        try {
            arguments = new Object[]{ts.getLatestTransaction(accountId)};
            eventToSend = new Event("getLatestTransactionSuccessful", arguments);
        } catch (Exception e) {
            e.printStackTrace();
            eventToSend = new Event("getLatestTransactionFailed");
        }

        try {
            eventSender.sendEvent(eventToSend);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.log(Level.WARNING, "Event send failed: " + e.getMessage());
        }
    }

    private void getTransactions(Event event) {
        Object[] arguments;
        Event eventToSend;

        String accountId = (String) event.getArguments()[0];

        try {
            List<TransactionDTO> dtos = ts.getTransactions(accountId);

            List<TransactionDTO> dtosMapped = dtos.stream().peek(t -> {

                String[] tokenarray = t.getDescription().split(" ");
                String token = tokenarray[tokenarray.length - 1];

                t.setToken(token);

            }).collect(Collectors.toList());
            arguments = new Object[]{dtosMapped};
            eventToSend = new Event("getAllTransactionsSuccessful", arguments);
        } catch (AccountNotFoundException e) {
            e.printStackTrace();
            eventToSend = new Event("getAllTransactionsFailed");
        }

        try {
            eventSender.sendEvent(eventToSend);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.log(Level.WARNING, "Event send failed: " + e.getMessage());
        }

}
*/
}