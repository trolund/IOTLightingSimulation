package exceptions;

public class TooManyTokensException extends Exception {
    public TooManyTokensException(String customerId, int size) {
        super(customerId + " has " + size + " tokens, and cannot request more.");
    }
}
