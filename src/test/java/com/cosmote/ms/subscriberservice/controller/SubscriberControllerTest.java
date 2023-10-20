package com.cosmote.ms.subscriberservice.controller;

import com.cosmote.ms.subscriberservice.entities.Subscriber;
import com.cosmote.ms.subscriberservice.service.SubscriberService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith( {MockitoExtension.class})
public class SubscriberControllerTest {
    private static final String ID = "ID";

    @Mock
    SubscriberService subscriberService;

    @InjectMocks
    SubscriberController subscriberController;

    @Test
    public void createSubscriber() {
        Subscriber subscriber = mock(Subscriber.class);
        when(subscriberService.createSubscriber(subscriber)).thenReturn(ID);
        String result = subscriberController.createSubscriber(subscriber);
        assertEquals(ID, result);
    }

    @Test
    public void getSubscribers() {
        List<Subscriber> subscribers = mock(List.class);
        when(subscriberService.getSubscribers()).thenReturn(subscribers);
        List<Subscriber> result = subscriberController.getSubscribers();
        assertSame(subscribers, result);
    }

    @Test
    public void getSubscriberById() {
        Subscriber subscriber = mock(Subscriber.class);
        when(subscriberService.getSubscriberById(ID)).thenReturn(subscriber);
        Subscriber result = subscriberController.getSubscriberById(ID);
        assertSame(subscriber, result);
    }

    @Test
    public void testUpdateSubscriber() {
        Subscriber subscriber = mock(Subscriber.class);
        subscriberController.updateSubscriber(ID, subscriber);
        verify(subscriberService).updateSubscriber(ID, subscriber);
    }

    @Test
    public void deleteSubscriber() {
        subscriberController.deleteSubscriber(ID);
        verify(subscriberService).deleteSubscriber(ID);
    }
}
