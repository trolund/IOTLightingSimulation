package exceptions.customer;

/**
 * @author Troels (s161791)
 * UserNotFoundException to use when a user cannot be found.
 */
public class CustomerNotFoundException extends CustomerException {

    public CustomerNotFoundException(String message) {
        super(message);
    }

    public CustomerNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}