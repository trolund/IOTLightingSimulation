package exceptions;

public class TooManyTokensException extends Exception {
    public TooManyTokensException(String customerId, int amount) {
        super("Customer (" +  customerId + ") has " + amount + " tokens, and cannot request more.");
    }
}
