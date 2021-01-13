package exceptions;

public class MerchantException extends Exception {

    public MerchantException(String message) {
        super(message, null);
    }

    public MerchantException(String message, Throwable cause) {
        super(message, cause);
    }

}