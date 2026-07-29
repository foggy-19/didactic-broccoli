package com.panonit.service.adoptions;

import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.Collection;

@Controller
public class DogAdoptionsGraphqlController {

    private final DogAdoptionService service;

    public DogAdoptionsGraphqlController(DogAdoptionService service) {
        this.service = service;
    }

    @QueryMapping
    Collection<Dog> dogs() {
        return service.dogs();
    }
}
