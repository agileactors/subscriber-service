package com.cosmote.subscriberservicetraining.exception;

public class SubscriberNotFoundException extends ValidationException {

    private static final String ERROR_MESSAGE = "Subscriber not found";

    public SubscriberNotFoundException() {
        super(ERROR_MESSAGE);
    }
}
