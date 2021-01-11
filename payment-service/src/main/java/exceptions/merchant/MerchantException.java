package exceptions.merchant;

/**
 * @author Troels (s161791)
 * UserNotFoundException to use when a user cannot be found.
 */
public class MerchantException extends Exception {

    public MerchantException(String message) {
        super(message, null);
    }

    public MerchantException(String message, Throwable cause) {
        super(message, cause);
    }

}