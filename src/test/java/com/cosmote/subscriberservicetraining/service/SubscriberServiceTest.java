package com.cosmote.subscriberservicetraining.service;

import com.cosmote.subscriberservicetraining.domain.Subscriber;
import com.cosmote.subscriberservicetraining.dto.SubscriberDto;
import com.cosmote.subscriberservicetraining.exception.EmailAlreadyExistsException;
import com.cosmote.subscriberservicetraining.repository.SubscriberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SubscriberServiceTest {

    @Test
    public void saveSubscriberHappyPath() {
        SubscriberRepository subscriberRepository = mock(SubscriberRepository.class);

        SubscriberService subscriberService = new SubscriberService(subscriberRepository);

        Subscriber returnedSubscriber = new Subscriber();
        returnedSubscriber.setId("id123");

        when(subscriberRepository.save(any())).thenReturn(returnedSubscriber);

        SubscriberDto subscriberDto = new SubscriberDto();
        subscriberDto.setFirstName("Kostas");
        subscriberDto.setLastName("Sidis");
        subscriberDto.setEmail("test@test.com");
        subscriberDto.setPhoneNumber("697123456");

        SubscriberDto savedDto = subscriberService.saveSubscriber(subscriberDto);

        assertEquals(savedDto.getId(), "id123");
        assertEquals(savedDto.getFirstName(), "Kostas");
        assertEquals(savedDto.getLastName(), "Sidis");
        assertEquals(savedDto.getPhoneNumber(), "697123456");
        assertEquals(savedDto.getEmail(), "test@test.com");
    }

    @Test
    public void saveSubscriberWithExistingEmail() {
        SubscriberRepository subscriberRepository = mock(SubscriberRepository.class);

        SubscriberService subscriberService = new SubscriberService(subscriberRepository);

        Subscriber returnedSubscriber = new Subscriber();
        returnedSubscriber.setId("id123");

        when(subscriberRepository.findByEmail("test@test.com")).thenReturn(returnedSubscriber);

        SubscriberDto subscriberDto = new SubscriberDto();
        subscriberDto.setFirstName("Kostas");
        subscriberDto.setLastName("Sidis");
        subscriberDto.setEmail("test@test.com");
        subscriberDto.setPhoneNumber("697123456");

        Assertions.assertThrowsExactly(EmailAlreadyExistsException.class,
                () -> subscriberService.saveSubscriber(subscriberDto));
    }
}
