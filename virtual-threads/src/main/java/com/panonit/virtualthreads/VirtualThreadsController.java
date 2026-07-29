package com.panonit.virtualthreads;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClient;

@Controller
@ResponseBody
class VirtualThreadsController {

    private final RestClient restClient;

    VirtualThreadsController(RestClient restClient) {
        this.restClient = restClient;
    }

    @GetMapping(path = "/delay")
    String delay() {
        String body = restClient.get()
                .uri("https://httpbin.org/delay/5")
                .retrieve()
                .body(String.class);

        System.out.println("Received: " + body);

        return body;
    }
}
