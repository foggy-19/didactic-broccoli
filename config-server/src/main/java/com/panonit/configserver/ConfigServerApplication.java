package com.panonit.configserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;

@EnableConfigServer
@SpringBootApplication
public class ConfigServerApplication {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public static void main(String[] args) {
        SpringApplication.run(ConfigServerApplication.class, args);
    }

    @Bean
    public ApplicationRunner runner(Environment environment) {
        return args -> {
            if (environment instanceof ConfigurableEnvironment env1) {
                env1.getPropertySources().forEach(source -> logger.info(source.toString()));
            }
        };
    }
}
