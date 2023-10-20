package com.cosmote.ms.subscriberservice.controller;

import com.cosmote.ms.logging.LogInit;
import com.cosmote.ms.subscriberservice.entities.Subscriber;
import com.cosmote.ms.subscriberservice.service.SubscriberService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class SubscriberController {

    private SubscriberService subscriberService;

    @Autowired
    public SubscriberController(SubscriberService subscriberService) {
        this.subscriberService = subscriberService;
    }

    @LogInit
    @RequestMapping(path = "/subscribers", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public String createSubscriber(@RequestBody Subscriber subscriber) {
        return subscriberService.createSubscriber(subscriber);
    }

    @LogInit
    @RequestMapping(value = "/subscribers", method = RequestMethod.GET)
    public List<Subscriber> getSubscribers() {
        return subscriberService.getSubscribers();
    }

    @LogInit
    @RequestMapping(value = "/subscribers/{subscriberId}", method = RequestMethod.GET)
    public Subscriber getSubscriberById(@PathVariable String subscriberId) {
        return subscriberService.getSubscriberById(subscriberId);
    }

    @LogInit
    @RequestMapping(value = "/subscribers/{subscriberId}", method = RequestMethod.PUT)
    public void updateSubscriber(@PathVariable String subscriberId, @RequestBody Subscriber subscriber) {
        subscriberService.updateSubscriber(subscriberId, subscriber);
    }

    @LogInit
    @RequestMapping(value = "/subscribers/{subscriberId}", method = RequestMethod.DELETE)
    public void deleteSubscriber(@PathVariable String subscriberId) {
        subscriberService.deleteSubscriber(subscriberId);
    }
}
