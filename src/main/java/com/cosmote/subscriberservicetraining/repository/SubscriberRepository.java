package com.cosmote.subscriberservicetraining.repository;

import com.cosmote.subscriberservicetraining.domain.Subscriber;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SubscriberRepository extends MongoRepository<Subscriber, String> {

    Subscriber findByEmail(String email);
}
