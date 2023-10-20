package com.cosmote.subscriberservicetraining;

import com.cosmote.subscriberservicetraining.repository.SubscriberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SubscriberServiceTrainingApplication {

	@Autowired
	private SubscriberRepository subscriberRepository;

	public static void main(String[] args) {
		SpringApplication.run(SubscriberServiceTrainingApplication.class, args);
	}
}
