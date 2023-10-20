package com.cosmote.ms.subscriberservice.exception;

public class EmptyFieldsException extends ValidationException {

    private static final String ERROR_MESSAGE = "Field should not be empty";

    public EmptyFieldsException() {
        super(ERROR_MESSAGE);
    }
}
