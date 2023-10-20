package com.cosmote.ms.subscriberservice.advice;

import com.cosmote.ms.subscriberservice.exception.EmailAlreadyExistsException;
import com.cosmote.ms.subscriberservice.exception.EmptyFieldsException;
import com.cosmote.ms.subscriberservice.exception.SubscriberNotFoundException;
import com.cosmote.ms.subscriberservice.exception.ValidationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    @Autowired
    private ObjectMapper mapper;

    @ExceptionHandler(value = {EmptyFieldsException.class})
    protected ResponseEntity<Object> handleBadRequest(ValidationException ex, WebRequest request)
            throws JsonProcessingException {
        String bodyOfResponse = mapper.writeValueAsString(ex.getErrorMessage());
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value = {EmailAlreadyExistsException.class})
    protected ResponseEntity<Object> handleConflict(ValidationException ex, WebRequest request)
            throws JsonProcessingException {
        String bodyOfResponse = mapper.writeValueAsString(ex.getErrorMessage());
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(value = {SubscriberNotFoundException.class})
    protected ResponseEntity<Object> handleNotFound(ValidationException ex, WebRequest request)
            throws JsonProcessingException {
        String bodyOfResponse = mapper.writeValueAsString(ex.getErrorMessage());
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }
}
