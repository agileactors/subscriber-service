package com.cosmote.ms.subscriberservice.repository;

import com.cosmote.ms.subscriberservice.entities.Subscriber;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface SubscriberRepository extends MongoRepository<Subscriber, String> {

    Subscriber findByEmail(String email);
}
