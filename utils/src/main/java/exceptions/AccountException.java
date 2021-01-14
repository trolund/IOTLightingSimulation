package exceptions;

public class AccountException extends Exception {

    public AccountException(String message) {
        super(message, null);
    }

    public AccountException(String message, Throwable cause) {
        super(message, cause);
    }
}
