package exceptions.merchant;

/**
 * @author Troels (s161791)
 * MerchantException to use when a customer cannot be found.
 */
public class MerchantNotFoundException extends MerchantException {

    public MerchantNotFoundException(String message) {
        super(message);
    }

    public MerchantNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}