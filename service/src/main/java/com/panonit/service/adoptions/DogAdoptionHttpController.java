package com.panonit.service.adoptions;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@Controller
@ResponseBody
class DogAdoptionHttpController {

    private final DogAdoptionService service;

    DogAdoptionHttpController(DogAdoptionService service) {
        this.service = service;
    }

    @GetMapping(path = "/dogs")
    Collection<Dog> dogs() {
        return service.dogs();
    }

    @PostMapping(path = "/dogs/{id}/adoptions")
    void adopt(@PathVariable int id, @RequestParam String owner) {
        service.adopt(id, owner);
    }

    @GetMapping(path = "/assistant")
    String assistant(@RequestParam String question) {
        return service.assistant(question);
    }
}
