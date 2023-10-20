package com.cosmote.ms.subscriberservice.exception;

public class ValidationException extends RuntimeException {

    private ErrorMessage errorMessage;

    public ValidationException(String error) {
        super(error);
        this.errorMessage = new ErrorMessage(error);
    }

    public ErrorMessage getErrorMessage() {
        return errorMessage;
    }
}
