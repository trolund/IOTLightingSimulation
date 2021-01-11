package exceptions.customer;

/**
 * @author Troels (s161791)
 * CustomerException to use when a customer cannot be found.
 */
public class CustomerException extends Exception {

    public CustomerException(String message) {
        super(message, null);
    }

    public CustomerException(String message, Throwable cause) {
        super(message, cause);
    }

}