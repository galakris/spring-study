package com.example.springstudy.profile;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;

@Profile("lidl")
public class Lidl implements Shop{

    @Override
    @EventListener(ApplicationReadyEvent.class)
    public void introduceYourself() {
        System.out.println("Lidl");
    }
}
