package com.projeto;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CurrencyProducer {

    private final RabbitTemplate rabbitTemplate;
    private final String exchange;
    private final String routingKey;

    public CurrencyProducer(RabbitTemplate rabbitTemplate,
                            @Value("${exchange.name}") String exchange,
                            @Value("${routing-key.name}") String routingKey) {
        this.rabbitTemplate = rabbitTemplate;
        this.exchange = exchange;
        this.routingKey = routingKey;
    }

    public void sendConversionRequest(String message) {
        rabbitTemplate.convertAndSend(exchange, routingKey, message);
    }
}
