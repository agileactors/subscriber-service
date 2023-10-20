package com.cosmote.subscriberservicetraining.service;

import com.cosmote.subscriberservicetraining.domain.Subscriber;
import com.cosmote.subscriberservicetraining.dto.SubscriberDto;
import com.cosmote.subscriberservicetraining.exception.EmailAlreadyExistsException;
import com.cosmote.subscriberservicetraining.exception.SubscriberNotFoundException;
import com.cosmote.subscriberservicetraining.repository.SubscriberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SubscriberService {

    private SubscriberRepository subscriberRepository;

    public SubscriberService(SubscriberRepository subscriberRepository) {
        this.subscriberRepository = subscriberRepository;
    }

    public SubscriberDto getSubscriberDto(String id) {
        Optional<Subscriber> optionalSubscriber = subscriberRepository.findById(id);
        if (optionalSubscriber.isEmpty()) {
            throw new SubscriberNotFoundException();
        }
        Subscriber subscriber = optionalSubscriber.get();
        SubscriberDto subscriberDto = new SubscriberDto();
        subscriberDto.setId(subscriber.getId());
        subscriberDto.setFirstName(subscriber.getFirstName());
        subscriberDto.setLastName(subscriber.getLastName());
        return subscriberDto;

    }

    public SubscriberDto saveSubscriber(SubscriberDto subscriberDto) {
        if (subscriberRepository.findByEmail(subscriberDto.getEmail()) != null) {
            throw new EmailAlreadyExistsException();
        }
        Subscriber subscriber = new Subscriber();
        subscriber.setFirstName(subscriberDto.getFirstName());
        subscriber.setLastName(subscriberDto.getLastName());
        subscriber.setEmail(subscriberDto.getEmail());
        subscriber.setPhoneNumber(subscriberDto.getPhoneNumber());
        Subscriber subscriber1 = subscriberRepository.save(subscriber);
        subscriberDto.setId(subscriber1.getId());
        return subscriberDto;
    }

    public List<SubscriberDto> getAllSubscribers() {
        List<Subscriber> subscribers = subscriberRepository.findAll();
        List<SubscriberDto> subscriberDtos = new ArrayList<>();
        for (int i=0; i< subscribers.size(); i++) {
            Subscriber subscriber = subscribers.get(i);
            SubscriberDto subscriberDto = new SubscriberDto();
            subscriberDto.setId(subscriber.getId());
            subscriberDto.setFirstName(subscriber.getFirstName());
            subscriberDto.setLastName(subscriber.getLastName());
            subscriberDto.setEmail(subscriber.getEmail());
            subscriberDto.setPhoneNumber(subscriber.getPhoneNumber());
            subscriberDtos.add(subscriberDto);
        }
        return subscriberDtos;
    }
}
