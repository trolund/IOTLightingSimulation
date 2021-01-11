package exceptions.merchant;

/**
 * @author Troels (s161791)
 * MerchantException to use when a customer cannot be found.
 */
public class MerchantException extends Exception {

    public MerchantException(String message) {
        super(message, null);
    }

    public MerchantException(String message, Throwable cause) {
        super(message, cause);
    }

}