package com.panonit.service.adoptions;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.amqp.dsl.Amqp;
import org.springframework.integration.amqp.dsl.AmqpOutboundChannelAdapterSpec;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.dsl.DirectChannelSpec;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.MessageChannelSpec;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.messaging.MessageChannel;

@Configuration
public class IntegrationConfiguration {

    static final String ADOPTIONS_CHANNEL_NAME = "outboundAdoptionsMessageChannel";

    private static final String QUEUE = "adoptions.queue";
    private static final String EXCHANGE = "adoptions.exchange";
    private static final String ROUTING_KEY = "adoptions.created";

    @Bean
    Queue adoptionsQueue() {
        return QueueBuilder.durable(QUEUE).build();
    }

    @Bean
    Exchange adoptionsExchange() {
        return ExchangeBuilder.directExchange(EXCHANGE).build();
    }

    @Bean
    Binding adoptionsBinding(Queue adoptionsQueue, Exchange adoptionsExchange) {

        return BindingBuilder.bind(adoptionsQueue).to(adoptionsExchange).with(ROUTING_KEY).noargs();
    }

    @Bean(ADOPTIONS_CHANNEL_NAME)
    MessageChannelSpec<DirectChannelSpec, DirectChannel> outboundAdoptionsMessageChannelSpec() {
        return MessageChannels.direct();
    }

    @Bean
    IntegrationFlow outboundAdoptionsFlow(@Qualifier(ADOPTIONS_CHANNEL_NAME) MessageChannel messageChannel, AmqpTemplate template) {
        AmqpOutboundChannelAdapterSpec spec = Amqp.outboundAdapter(template)
                .exchangeName(EXCHANGE)
                .routingKey(ROUTING_KEY);

        return IntegrationFlow.from(messageChannel).handle(spec).get();
    }
}
