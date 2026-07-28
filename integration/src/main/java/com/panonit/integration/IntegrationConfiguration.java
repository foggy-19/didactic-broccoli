package com.panonit.integration;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.integration.amqp.dsl.Amqp;
import org.springframework.integration.core.GenericHandler;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.file.dsl.Files;
import org.springframework.integration.json.JsonToObjectTransformer;
import org.springframework.integration.json.ObjectToJsonTransformer;

import java.io.IOException;

@Configuration
public class IntegrationConfiguration {

    private static final String QUEUE = "adoptions.queue";

    @Bean
    Queue adoptionsQueue() {
        return QueueBuilder.durable(QUEUE).build();
    }

    @Bean
    IntegrationFlow inboundAdoptionsFlow(ConnectionFactory connectionFactory, @Value("file://${user.home}/Desktop/work/outbound") Resource resource) throws IOException {
        return IntegrationFlow.from(Amqp.inboundAdapter(connectionFactory, QUEUE))
                .transform(new JsonToObjectTransformer(Dog.class))
                .handle((GenericHandler<Dog>) (payload, headers) -> {
                            System.out.println("Dog received: " + payload);
                            return payload;
                        }
                )
                .transform(new ObjectToJsonTransformer())
                .handle(Files.outboundAdapter(resource.getFile()).autoCreateDirectory(true))
                .get();
    }
}
