package com.panonit.service.adoptions;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
@Transactional
class DogAdoptionService {

    private final DogRepository repository;
    private final ChatClient singularity;
    private final ApplicationEventPublisher applicationEventPublisher;

    public DogAdoptionService(DogRepository repository, ChatClient singularity, ApplicationEventPublisher applicationEventPublisher) {
        this.repository = repository;
        this.singularity = singularity;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    void adopt(int id, String owner) {
        repository.findById(id).ifPresent(dog -> {
            var updated = repository.save(new Dog(dog.id(), dog.name(), owner, dog.description()));
            System.out.printf("updated [%s]\n", updated);
            applicationEventPublisher.publishEvent(new DogAdoptionEvent(updated.id()));
        });
    }

    Collection<Dog> dogs() {
        return repository.findAll();
    }

    String assistant(String question) {
        return singularity.prompt(question).call().content();
    }
}

