package exceptions.customer;

/**
 * @author Troels (s161791)
 * UserNotFoundException to use when a user cannot be found.
 */
public class CustomerException extends Exception {

    public CustomerException(String message) {
        super(message, null);
    }

    public CustomerException(String message, Throwable cause) {
        super(message, cause);
    }

}