package com.cosmote.ms.subscriberservice.service;

import com.cosmote.ms.subscriberservice.entities.Subscriber;
import com.cosmote.ms.subscriberservice.exception.EmailAlreadyExistsException;
import com.cosmote.ms.subscriberservice.exception.EmptyFieldsException;
import com.cosmote.ms.subscriberservice.exception.SubscriberNotFoundException;
import com.cosmote.ms.subscriberservice.repository.SubscriberRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubscriberServiceImpl implements SubscriberService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SubscriberServiceImpl.class);

    private SubscriberRepository subscriberRepository;

    @Autowired
    public SubscriberServiceImpl(SubscriberRepository subscriberRepository) {
        this.subscriberRepository = subscriberRepository;
    }

    @Override
    public String createSubscriber(Subscriber subscriber) {
        validateSubscriber(subscriber);
        verifySubscriberDoesNotExist(subscriber);
        String id = subscriberRepository.save(subscriber).getId();
        LOGGER.info("Created subscriber {}", id);
        return id;
    }

    @Override
    public List<Subscriber> getSubscribers() {
        List<Subscriber> subscribers = subscriberRepository.findAll();
        LOGGER.info("Retrieved {} subscribers", subscribers.size());
        return subscribers;
    }

    @Override
    public Subscriber getSubscriberById(String id) {
        Subscriber subscriber = subscriberRepository.findById(id).orElseThrow(SubscriberNotFoundException::new);
        LOGGER.info("Retrieved subscriber {}", subscriber);
        return subscriber;
    }

    @Override
    public void updateSubscriber(String id, Subscriber subscriber) {
        Subscriber existingSubscriber = getSubscriberById(id);
        updateSubscriberFields(existingSubscriber, subscriber);
        subscriberRepository.save(existingSubscriber);
        LOGGER.info("Updated subscriber {}", existingSubscriber);
    }

    @Override
    public void deleteSubscriber(String id) {
        getSubscriberById(id);
        subscriberRepository.deleteById(id);
        LOGGER.info("Deleted subscriber {}", id);
    }

    private void validateSubscriber(Subscriber subscriber) {
        if (subscriber.getEmail() == null || subscriber.getFirstName() == null || subscriber.getLastName() == null
                || subscriber.getPhoneNumber() == null) {
            throw new EmptyFieldsException();
        }
    }

    private void verifySubscriberDoesNotExist(Subscriber subscriber) {
        if (subscriberRepository.findByEmail(subscriber.getEmail()) != null) {
            throw new EmailAlreadyExistsException();
        }
    }

    private void updateSubscriberFields(Subscriber existingSubscriber, Subscriber updatingSubscriber) {
        if (updatingSubscriber.getEmail() != null) {
            existingSubscriber.setEmail(updatingSubscriber.getEmail());
        }
        if (updatingSubscriber.getFirstName() != null) {
            existingSubscriber.setFirstName(updatingSubscriber.getFirstName());
        }
        if (updatingSubscriber.getLastName() != null) {
            existingSubscriber.setLastName(updatingSubscriber.getLastName());
        }
        if (updatingSubscriber.getPhoneNumber() != null) {
            existingSubscriber.setPhoneNumber(updatingSubscriber.getPhoneNumber());
        }
    }
}
