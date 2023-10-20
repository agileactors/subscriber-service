package com.cosmote.subscriberservicetraining.controller;

import com.cosmote.subscriberservicetraining.dto.SubscriberDto;
import com.cosmote.subscriberservicetraining.service.SubscriberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SubscriberController {
    @Autowired
    private SubscriberService subscriberService;

    @PostMapping(path = "/subscriber")
    public SubscriberDto saveSubscriber(@RequestBody SubscriberDto subscriberDto) {
        return subscriberService.saveSubscriber(subscriberDto);
    }

    @GetMapping(path = "/subscriber")
    public SubscriberDto getSubscriberById(@RequestParam String id) {
        return subscriberService.getSubscriberDto(id);
    }

    @GetMapping(path = "/subscribers")
    public List<SubscriberDto> getAllSubscribers() {
        return subscriberService.getAllSubscribers();
    }
}
