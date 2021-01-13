package exceptions;

public class TooManyTokensException extends Exception {
    public TooManyTokensException(String customerId, int amount) {
        super("Customer (" + customerId + ") cannot request " + amount + " tokens"); // TODO: Make specific errors
    }
}
