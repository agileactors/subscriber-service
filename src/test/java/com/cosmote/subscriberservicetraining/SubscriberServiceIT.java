package com.cosmote.subscriberservicetraining;

import com.cosmote.subscriberservicetraining.dto.SubscriberDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SubscriberServiceIT {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void saveSubscriber() {

        SubscriberDto subscriberDto = new SubscriberDto();
        subscriberDto.setFirstName("Kostas");
        subscriberDto.setLastName("Sidis");
        subscriberDto.setEmail("test123@test.com");
        subscriberDto.setPhoneNumber("697123456");

        HttpEntity<SubscriberDto> httpEntity = new HttpEntity<>(subscriberDto);
        ResponseEntity<SubscriberDto> responseEntity = testRestTemplate.exchange("/subscriber", HttpMethod.POST, httpEntity, SubscriberDto.class);
        Assertions.assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
    }
}
