package com.rabbitmq.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Service
public class RabbitMQProducer {

	@Value("${rabbitmq.exchange.name}")
	private String exchange;
	
	@Value("${rabbitmq.routing.key}")
	private String routingkey;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQProducer.class);
	
	private RabbitTemplate rabbitTemplate;

	
	public RabbitMQProducer(RabbitTemplate rabbitTemplate) {
		this.rabbitTemplate = rabbitTemplate;
	}
	
	@PostMapping("/post/{message}")
	public void sendMessage(@PathVariable(value = "message") String message) {
		LOGGER.info(String.format("Message sent -> %s",message));
		//rabbitTemplate.convertAndSend(exchange, routingkey, message);
		
		MessageProperties messageProperties = new MessageProperties();
		messageProperties.setHeader("fname", message);
		
		MessageConverter messageConverter = new SimpleMessageConverter();
		
		Message message1 = messageConverter.toMessage(message, messageProperties);
		rabbitTemplate.send(exchange,"", message1);
		
	}
	
}
