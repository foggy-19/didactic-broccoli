package com.panonit.virtualthreads;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
class VirtualThreadsConfiguration {

    @Bean
    RestClient restClient() {
        return RestClient.builder().build();
    }
}
