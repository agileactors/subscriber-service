package com.cosmote.ms.subscriberservice.service;

import com.cosmote.ms.subscriberservice.entities.Subscriber;
import com.cosmote.ms.subscriberservice.exception.EmailAlreadyExistsException;
import com.cosmote.ms.subscriberservice.exception.EmptyFieldsException;
import com.cosmote.ms.subscriberservice.exception.SubscriberNotFoundException;

import java.util.List;

public interface SubscriberService {
    /**
     * Create a new subscriber.
     *
     * @param subscriber
     *         subscriber to create
     * @return ID of subscriber created
     * @throws EmptyFieldsException
     *         if any required subscriber field is {@code null}
     * @throws EmailAlreadyExistsException
     *         if a subscriber with same email already exists
     */
    String createSubscriber(Subscriber subscriber);

    /**
     * Get all existing subscribers.
     *
     * @return a list of all existing subscribers
     */
    List<Subscriber> getSubscribers();

    /**
     * Get an existing subscriber by ID.
     *
     * @param id
     *         ID to search for
     * @return existing subscriber matching the ID specified
     * @throws SubscriberNotFoundException
     *         if no such subscriber exists
     */
    Subscriber getSubscriberById(String id);

    /**
     * Update an existing subscriber.
     *
     * @param id
     *         ID of subscriber to update
     * @param subscriber
     *         new subscriber status
     * @throws SubscriberNotFoundException
     *         if no such subscriber exists
     */
    void updateSubscriber(String id, Subscriber subscriber);

    /**
     * Delete an existing subscriber.
     *
     * @param id
     *         ID of subscriber to delete
     * @throws SubscriberNotFoundException
     *         if no such subscriber exists
     */
    void deleteSubscriber(String id);
}
