package com.cosmote.subscriberservicetraining.advice;

import com.cosmote.subscriberservicetraining.exception.EmailAlreadyExistsException;
import com.cosmote.subscriberservicetraining.exception.SubscriberNotFoundException;
import com.cosmote.subscriberservicetraining.exception.ValidationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {SubscriberNotFoundException.class})
    protected ResponseEntity<Object> handleNotFound(ValidationException ex, WebRequest request) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String bodyOfResponse = mapper.writeValueAsString(ex.getErrorMessage());
        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(value = {EmailAlreadyExistsException.class})
    protected ResponseEntity<Object> handleEmailExists(ValidationException ex, WebRequest request) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String bodyOfResponse = mapper.writeValueAsString(ex.getErrorMessage());
        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.CONFLICT, request);
    }
}
