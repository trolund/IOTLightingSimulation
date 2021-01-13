package exceptions;

public class TokenNotFoundException extends Exception {
    public TokenNotFoundException(String id) {
        super("Token (" + id + ") can not be found.");
    }
}