package exceptions;

public class InvalidTokenException extends Exception{
    public InvalidTokenException(String id) {
        super("Token " + id + " is invalid!");
    }
}
