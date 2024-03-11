package com.rabbitmq.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.rabbitmq.dto.User;

@Service
public class RabbitMQJsonProducer {

	@Value("${rabbitmq.exchange.name}")
	private String exchange;
	
	@Value("${rabbitmq.routing.json.key}")
	private String routingJsonkey;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQJsonProducer.class);
	
	private RabbitTemplate rabbitTemplate;

	public RabbitMQJsonProducer(RabbitTemplate rabbitTemplate) {
		this.rabbitTemplate = rabbitTemplate;
	}
	
	public void sendJsonMessage(User user) {
		LOGGER.info(String.format("Json Message Sent -> %s", user.toString()));
		rabbitTemplate.convertAndSend(exchange, routingJsonkey, user);
	}	
}
