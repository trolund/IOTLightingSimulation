package exceptions;

public class CustomerHasNoTokensException extends Exception {
    public CustomerHasNoTokensException(String customerId) {
        super("Customer " + customerId + " has no tokens.");
    }
}
