package Services.PaymentServiceTest.dto;

import java.io.Serializable;

public class ErrorType implements Serializable {

    public String errorMessage;

    public ErrorType() {

    }

    public ErrorType(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
