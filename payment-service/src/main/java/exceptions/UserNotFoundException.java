package exceptions;

/**
 * @author Troels (s161791)
 * UserNotFoundException to use when a user cannot be found.
 */

public class UserNotFoundException extends Exception {

    public UserNotFoundException(String message) {
        super(message, null);
    }

    public UserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}

