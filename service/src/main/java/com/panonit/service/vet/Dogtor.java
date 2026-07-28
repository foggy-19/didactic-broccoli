package com.panonit.service.vet;

import com.panonit.service.adoptions.DogAdoptionEvent;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Service;

@Service
class Dogtor {

    @ApplicationModuleListener
    void checkup(DogAdoptionEvent event) throws InterruptedException {
        Thread.sleep(5000);
        System.out.println("checking up on " + event.dogId());
    }
}
