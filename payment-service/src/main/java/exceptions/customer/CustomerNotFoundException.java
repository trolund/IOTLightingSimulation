package exceptions.customer;

/**
 * @author Troels (s161791)
 * CustomerNotFoundException to use when a Customer cannot be found.
 */
public class CustomerNotFoundException extends CustomerException {

    public CustomerNotFoundException(String message) {
        super(message);
    }

    public CustomerNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}