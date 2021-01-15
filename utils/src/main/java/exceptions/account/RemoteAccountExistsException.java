package exceptions.account;

// Custom exceptions for each resource.
// For example, if you have a CustomerResource and PaymentResource,
// then you want both PaymentException and CustomerException in different classes.
// You can make use of further inheritance by making a more specific
// exception like CustomerNotFoundException extend CustomerException.
//
// If its an exception for web, perhaps extending the WebException is a better idea.
public class RemoteAccountExistsException extends Exception {

    public RemoteAccountExistsException() {
        super();
    }

    public RemoteAccountExistsException(String errorMessage) {
        super(errorMessage);
    }
}
