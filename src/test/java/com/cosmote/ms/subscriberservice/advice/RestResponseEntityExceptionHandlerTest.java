package com.cosmote.ms.subscriberservice.advice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.cosmote.ms.subscriberservice.exception.EmptyFieldsException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.cosmote.ms.subscriberservice.exception.EmailAlreadyExistsException;
import com.cosmote.ms.subscriberservice.exception.SubscriberNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;

@ExtendWith(MockitoExtension.class)
public class RestResponseEntityExceptionHandlerTest {
    private static final String ERROR = "error";

    @Mock
    ObjectMapper objectMapper;

    @InjectMocks
    RestResponseEntityExceptionHandler handler;

    @Test
    public void handleBadRequestTest() throws JsonProcessingException {
        EmptyFieldsException ex = new EmptyFieldsException();
        when(objectMapper.writeValueAsString(ex.getErrorMessage())).thenReturn(ERROR);
        ResponseEntity<Object> response = handler.handleBadRequest(ex, mock(WebRequest.class));
        assertNotNull(response);
        assertEquals(ERROR, response.getBody());
        assertTrue(response.getHeaders().isEmpty());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void handleConflictTest() throws JsonProcessingException {
        EmailAlreadyExistsException ex = new EmailAlreadyExistsException();
        when(objectMapper.writeValueAsString(ex.getErrorMessage())).thenReturn(ERROR);
        ResponseEntity<Object> response = handler.handleConflict(ex, mock(WebRequest.class));
        assertNotNull(response);
        assertEquals(ERROR, response.getBody());
        assertTrue(response.getHeaders().isEmpty());
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    @Test
    public void handleNotFoundTest() throws JsonProcessingException {
        SubscriberNotFoundException ex = new SubscriberNotFoundException();
        when(objectMapper.writeValueAsString(ex.getErrorMessage())).thenReturn(ERROR);
        ResponseEntity<Object> response = handler.handleNotFound(ex, mock(WebRequest.class));
        assertNotNull(response);
        assertEquals(ERROR, response.getBody());
        assertTrue(response.getHeaders().isEmpty());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
