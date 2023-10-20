package com.cosmote.ms.subscriberservice.functionaltest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import com.cosmote.ms.subscriberservice.entities.Subscriber;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CreateSubscriberFunctionalTest {
    private static final String CREATE_SUBSCRIBER = "/api/subscribers";
    private static final String DELETE_SUBSCRIBER = "/api/subscribers/{id}";
    private static final String EMAIL_ALREADY_EXISTS_ERROR = "{\"error\":\"Email already exists\"}";
    private static final String GET_SUBSCRIBER = "/api/subscribers/{id}";
    private static final String ID = "id";
    private static final String EMPTY_FIELD_ERROR = "{\"error\":\"Field should not be empty\"}";
    private static final Subscriber SUBSCRIBER = new Subscriber("john.doe@example.org", "John", "Doe", "0123456789");

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void testCreateSubscriber() {
        ResponseEntity<String> createSubscriberResponse =
                testRestTemplate.postForEntity(CREATE_SUBSCRIBER, new HttpEntity<>(SUBSCRIBER), String.class);
        assertEquals(HttpStatus.CREATED, createSubscriberResponse.getStatusCode());
        String id = createSubscriberResponse.getBody();
        assertNotNull(id);
        ResponseEntity<Subscriber> getSubscriberResponse =
                testRestTemplate.getForEntity(GET_SUBSCRIBER, Subscriber.class, Map.of(ID, id));
        assertEquals(HttpStatus.OK, getSubscriberResponse.getStatusCode());
        Subscriber subscriber = getSubscriberResponse.getBody();
        assertNotNull(subscriber);
        assertEquals(SUBSCRIBER.getEmail(), subscriber.getEmail());
        assertEquals(SUBSCRIBER.getFirstName(), subscriber.getFirstName());
        assertEquals(SUBSCRIBER.getLastName(), subscriber.getLastName());
        assertEquals(SUBSCRIBER.getPhoneNumber(), subscriber.getPhoneNumber());
        testRestTemplate.delete(DELETE_SUBSCRIBER, Map.of(ID, id));
    }

    @Test
    public void testCreateSubscriberMissingFields() {
        ResponseEntity<String> createSubscriberResponse = testRestTemplate.postForEntity(CREATE_SUBSCRIBER,
                new HttpEntity<>(new Subscriber(null, SUBSCRIBER.getFirstName(), SUBSCRIBER.getLastName(),
                        SUBSCRIBER.getPhoneNumber())), String.class);
        assertEquals(HttpStatus.BAD_REQUEST, createSubscriberResponse.getStatusCode());
        assertEquals(EMPTY_FIELD_ERROR, createSubscriberResponse.getBody());
    }

    @Test
    public void testCreateSubscriberSameEmail() {
        String id =
                testRestTemplate.postForEntity(CREATE_SUBSCRIBER, new HttpEntity<>(SUBSCRIBER), String.class).getBody();
        ResponseEntity<String> createSubscriberSameEmailResponse =
                testRestTemplate.postForEntity(CREATE_SUBSCRIBER, new HttpEntity<>(SUBSCRIBER), String.class);
        assertEquals(HttpStatus.CONFLICT, createSubscriberSameEmailResponse.getStatusCode());
        assertEquals(EMAIL_ALREADY_EXISTS_ERROR, createSubscriberSameEmailResponse.getBody());
        testRestTemplate.delete(DELETE_SUBSCRIBER, Map.of(ID, id));
    }
}
