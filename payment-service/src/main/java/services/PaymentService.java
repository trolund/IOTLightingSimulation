package services;

import dto.PaymentRequest;
import dto.TransactionDTO;
import dto.Transaction;
import dto.UserAccountDTO;
import exceptions.account.AccountNotFoundException;
import exceptions.token.InvalidTokenException;
import exceptions.transaction.TransactionException;
import exceptions.transaction.TransactionNotFoundException;
import infrastructure.bank.BankService;
import interfaces.rabbitmq.payment.RabbitMQPaymentAdapterFactory;
import interfaces.rabbitmq.token.RabbitMQTokenAdapterFactory;
import services.interfaces.IPaymentService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * @primary-author Troels (s161791)
 * @co-author Daniel (s151641)
 */
@ApplicationScoped
public class PaymentService implements IPaymentService {

    BankService bs;
    MapperService mapper;

    @Inject
    public PaymentService(Bank bank, MapperService mapper) {
        this.bs = bank.getBankServicePort();
        this.mapper = mapper;
    }

    @Override
    public void processPayment(PaymentRequest paymentRequest)
            throws AccountNotFoundException, TransactionException, InvalidTokenException {
        PaymentEventService eventService = new RabbitMQPaymentAdapterFactory().getService();

        UserAccountDTO customer = null;
        UserAccountDTO merchant = null;
        String customerId = paymentRequest.getCustomerId();
        String merchantId = paymentRequest.getMerchantId();
        String token = paymentRequest.getToken();
        int amount = paymentRequest.getAmount();
        TransactionDTO dto = null;

        String desc = "Transaction between Customer (" + customerId + ")" +
                " and Merchant (" + merchantId + ") for amount " + amount +
                " with token " + token;

        try {
            // Checks if the token is valid
            TokenEventService service = new RabbitMQTokenAdapterFactory().getService();
            if (!service.validateToken(token).equals(token)) {
                throw new InvalidTokenException(token);
            }

            /*
             * This is incorrect. This should be done through
             * rabbitMQ. However, as we're soon going to
             * re-implement the way we use rabbitMQ,
             * we will just use rest for now.
             */
            // TODO get with account service through rabbitMQ
            AccountServiceStub accountService = new AccountServiceStub();
            customer = accountService.getAccount(customerId);
            merchant = accountService.getAccount(merchantId);

            dto = new TransactionDTO(BigDecimal.valueOf(amount), customer.getBankAccount().getBalance(), merchantId, customerId, desc, new Date());

            bs.transferMoneyFromTo(
                    customer.getBankAccount().getId(),
                    merchant.getBankAccount().getId(),
                    BigDecimal.valueOf(amount),
                    desc);
            try {
                eventService.sendTransactionDone(dto, true);
            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (InvalidTokenException e) {
            throw new InvalidTokenException(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();

            dto = new TransactionDTO(BigDecimal.valueOf(amount), BigDecimal.valueOf(-1), merchantId, customerId, desc, new Date());
            dto.setAmount(BigDecimal.valueOf(amount));

            try {
                eventService.sendTransactionDone(dto, false);

                if (customer == null || merchant == null) {
                    throw new AccountNotFoundException("Account with id (" + merchantId + ") is not found!");
                }

            } catch (Exception exception) {
                exception.printStackTrace();
            }
            throw new TransactionException(e.getMessage());
        }
    }

    @Override
    public void refund(PaymentRequest paymentRequest)
            throws AccountNotFoundException, TransactionException, InvalidTokenException {
        // reverse the request
        String tempCustomerId = paymentRequest.getCustomerId();
        paymentRequest.setCustomerId(paymentRequest.getMerchantId());
        paymentRequest.setMerchantId(tempCustomerId);

        processPayment(paymentRequest);
    }

}