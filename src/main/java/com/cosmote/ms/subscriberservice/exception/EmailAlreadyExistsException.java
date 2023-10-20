package com.cosmote.ms.subscriberservice.exception;

public class EmailAlreadyExistsException extends ValidationException {

    private static final String ERROR_MESSAGE = "Email already exists";

    public EmailAlreadyExistsException() {
        super(ERROR_MESSAGE);
    }
}
