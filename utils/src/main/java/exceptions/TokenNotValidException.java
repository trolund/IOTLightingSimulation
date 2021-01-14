package exceptions;

/**
 * @author Troels (s161791), Daniel (s151641)
 * TokenNotValidException to use when a token is not valid.
 */

public class TokenNotValidException extends Exception {

    public TokenNotValidException(String message) {
        super(message, null);
    }

    public TokenNotValidException(String message, Throwable cause) {
        super(message, cause);
    }
}
