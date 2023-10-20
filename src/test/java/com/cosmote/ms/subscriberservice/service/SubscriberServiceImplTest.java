package com.cosmote.ms.subscriberservice.service;

import com.cosmote.ms.subscriberservice.entities.Subscriber;
import com.cosmote.ms.subscriberservice.exception.EmailAlreadyExistsException;
import com.cosmote.ms.subscriberservice.exception.EmptyFieldsException;
import com.cosmote.ms.subscriberservice.exception.SubscriberNotFoundException;
import com.cosmote.ms.subscriberservice.repository.SubscriberRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SubscriberServiceImplTest {
    private static final String EMAIL = "email";
    private static final String FIRST_NAME = "first name";
    private static final String ID = "ID";
    private static final String LAST_NAME = "last name";
    private static final String NEW_EMAIL = "new email";
    private static final String NEW_FIRST_NAME = "new first name";
    private static final String NEW_LAST_NAME = "new last name";
    private static final String NEW_PHONE_NUMBER = "new phone number";
    private static final String PHONE_NUMBER = "phone number";

    @Mock
    SubscriberRepository subscriberRepository;

    @InjectMocks
    SubscriberServiceImpl subscriberService;

    @Test
    public void createSubscriber() {
        Subscriber newSubscriber = new Subscriber(EMAIL, FIRST_NAME, LAST_NAME, PHONE_NUMBER);
        Subscriber savedSubscriber = new Subscriber();
        savedSubscriber.setId(ID);
        when(subscriberRepository.save(newSubscriber)).thenReturn(savedSubscriber);
        String result = subscriberService.createSubscriber(newSubscriber);
        assertEquals(ID, result);
    }

    @MethodSource("subscribersEmptyFields")
    @ParameterizedTest
    public void createSubscriberEmptyFields(String email, String firstName, String lastName, String phoneNumber) {
        assertThrows(EmptyFieldsException.class, () -> {
            subscriberService.createSubscriber(new Subscriber(email, firstName, lastName, phoneNumber));
        });
    }

    @Test
    public void createSubscriberAlreadyExists() {
        Subscriber subscriber = new Subscriber(EMAIL, FIRST_NAME, LAST_NAME, PHONE_NUMBER);
        when(subscriberRepository.findByEmail(EMAIL)).thenReturn(mock(Subscriber.class));
        assertThrows(EmailAlreadyExistsException.class, () -> {
            subscriberService.createSubscriber(subscriber);
        });
    }

    @Test
    public void getSubscribers() {
        List<Subscriber> subscribers = mock(List.class);
        when(subscriberRepository.findAll()).thenReturn(subscribers);
        List<Subscriber> result = subscriberService.getSubscribers();
        assertSame(subscribers, result);
    }

    @Test
    public void getSubscriberById() {
        Subscriber subscriber = mock(Subscriber.class);
        when(subscriberRepository.findById(ID)).thenReturn(Optional.of(subscriber));
        Subscriber result = subscriberService.getSubscriberById(ID);
        assertSame(subscriber, result);
    }

    @Test
    public void getSubscriberByIdNotFound() {
        when(subscriberRepository.findById(ID)).thenReturn(Optional.empty());
        assertThrows(SubscriberNotFoundException.class, () -> {
            subscriberService.getSubscriberById(ID);
        });
    }

    @Test
    public void updateSubscriber() {
        Subscriber subscriber = new Subscriber(EMAIL, FIRST_NAME, LAST_NAME, PHONE_NUMBER);
        SubscriberService subscriberService = spy(this.subscriberService);
        doReturn(subscriber).when(subscriberService).getSubscriberById(ID);
        subscriberService
                .updateSubscriber(ID, new Subscriber(NEW_EMAIL, NEW_FIRST_NAME, NEW_LAST_NAME, NEW_PHONE_NUMBER));
        assertEquals(NEW_EMAIL, subscriber.getEmail());
        assertEquals(NEW_FIRST_NAME, subscriber.getFirstName());
        assertEquals(NEW_LAST_NAME, subscriber.getLastName());
        assertEquals(NEW_PHONE_NUMBER, subscriber.getPhoneNumber());
        verify(subscriberRepository).save(subscriber);
    }

    @Test
    public void updateSubscriberNullEmail() {
        Subscriber subscriber = new Subscriber(EMAIL, FIRST_NAME, LAST_NAME, PHONE_NUMBER);
        SubscriberService subscriberService = spy(this.subscriberService);
        doReturn(subscriber).when(subscriberService).getSubscriberById(ID);
        subscriberService.updateSubscriber(ID, new Subscriber(null, NEW_FIRST_NAME, NEW_LAST_NAME, NEW_PHONE_NUMBER));
        assertEquals(EMAIL, subscriber.getEmail());
        assertEquals(NEW_FIRST_NAME, subscriber.getFirstName());
        assertEquals(NEW_LAST_NAME, subscriber.getLastName());
        assertEquals(NEW_PHONE_NUMBER, subscriber.getPhoneNumber());
        verify(subscriberRepository).save(subscriber);
    }

    @Test
    public void updateSubscriberNullFirstName() {
        Subscriber subscriber = new Subscriber(EMAIL, FIRST_NAME, LAST_NAME, PHONE_NUMBER);
        SubscriberService subscriberService = spy(this.subscriberService);
        doReturn(subscriber).when(subscriberService).getSubscriberById(ID);
        subscriberService.updateSubscriber(ID, new Subscriber(NEW_EMAIL, null, NEW_LAST_NAME, NEW_PHONE_NUMBER));
        assertEquals(NEW_EMAIL, subscriber.getEmail());
        assertEquals(FIRST_NAME, subscriber.getFirstName());
        assertEquals(NEW_LAST_NAME, subscriber.getLastName());
        assertEquals(NEW_PHONE_NUMBER, subscriber.getPhoneNumber());
        verify(subscriberRepository).save(subscriber);
    }

    @Test
    public void updateSubscriberNullLastName() {
        Subscriber subscriber = new Subscriber(EMAIL, FIRST_NAME, LAST_NAME, PHONE_NUMBER);
        SubscriberService subscriberService = spy(this.subscriberService);
        doReturn(subscriber).when(subscriberService).getSubscriberById(ID);
        subscriberService.updateSubscriber(ID, new Subscriber(NEW_EMAIL, NEW_FIRST_NAME, null, NEW_PHONE_NUMBER));
        assertEquals(NEW_EMAIL, subscriber.getEmail());
        assertEquals(NEW_FIRST_NAME, subscriber.getFirstName());
        assertEquals(LAST_NAME, subscriber.getLastName());
        assertEquals(NEW_PHONE_NUMBER, subscriber.getPhoneNumber());
        verify(subscriberRepository).save(subscriber);
    }

    @Test
    public void updateSubscriberNullPhoneNumber() {
        Subscriber subscriber = new Subscriber(EMAIL, FIRST_NAME, LAST_NAME, PHONE_NUMBER);
        SubscriberService subscriberService = spy(this.subscriberService);
        doReturn(subscriber).when(subscriberService).getSubscriberById(ID);
        subscriberService.updateSubscriber(ID, new Subscriber(NEW_EMAIL, NEW_FIRST_NAME, NEW_LAST_NAME, null));
        assertEquals(NEW_EMAIL, subscriber.getEmail());
        assertEquals(NEW_FIRST_NAME, subscriber.getFirstName());
        assertEquals(NEW_LAST_NAME, subscriber.getLastName());
        assertEquals(PHONE_NUMBER, subscriber.getPhoneNumber());
        verify(subscriberRepository).save(subscriber);
    }

    @Test
    public void updateSubscriberNotFound() {
        SubscriberService subscriberService = spy(this.subscriberService);
        doThrow(SubscriberNotFoundException.class).when(subscriberService).getSubscriberById(ID);
        assertThrows(SubscriberNotFoundException.class, () -> {
            subscriberService.updateSubscriber(ID, new Subscriber(EMAIL, FIRST_NAME, LAST_NAME, PHONE_NUMBER));
        });
        verify(subscriberRepository, never()).save(any(Subscriber.class));
    }

    @Test
    public void deleteSubscriber() {
        SubscriberService subscriberService = spy(this.subscriberService);
        doReturn(mock(Subscriber.class)).when(subscriberService).getSubscriberById(ID);
        subscriberService.deleteSubscriber(ID);
        verify(subscriberRepository).deleteById(ID);
    }

    @Test
    public void deleteSubscriberNotFound() {
        SubscriberService subscriberService = spy(this.subscriberService);
        doThrow(SubscriberNotFoundException.class).when(subscriberService).getSubscriberById(ID);
        assertThrows(SubscriberNotFoundException.class, () -> {
            subscriberService.deleteSubscriber(ID);
        });
        verify(subscriberRepository, never()).deleteById(anyString());
    }

    private static Stream<Arguments> subscribersEmptyFields() {
        return Stream.of(Arguments.of(null, FIRST_NAME, LAST_NAME, PHONE_NUMBER),
                Arguments.of(EMAIL, null, LAST_NAME, PHONE_NUMBER), Arguments.of(EMAIL, FIRST_NAME, null, PHONE_NUMBER),
                Arguments.of(EMAIL, FIRST_NAME, LAST_NAME, null));
    }
}
