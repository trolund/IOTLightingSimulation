package exceptions;

public class CustomerNotFoundException extends Exception {
    public CustomerNotFoundException(String id) {
        super("Customer (" + id + ") was not found.");
    }
}
