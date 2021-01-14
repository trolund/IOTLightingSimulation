package exceptions;

public class CustomerAlreadyRegisteredException extends Exception {
    public CustomerAlreadyRegisteredException(String customerId) {
        super("Customer " + customerId + " is already registered.");
    }
}