package com.rabbitmq.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import com.rabbitmq.dto.User;

@Service
public class RabbitMQJsonConsumer {

	private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQJsonConsumer.class);
	
	@RabbitListener(queues = {"${rabbitmq.queue.json.name}"})
	public void consumeJsonMessage(User user) {
		LOGGER.info(String.format("Received the Json Message -> %s", user.toString()));
	}
}
