package exceptions;

public class MerchantAlreadyExistsException extends MerchantException {

    public MerchantAlreadyExistsException(String message) {
        super(message);
    }

    public MerchantAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

}
