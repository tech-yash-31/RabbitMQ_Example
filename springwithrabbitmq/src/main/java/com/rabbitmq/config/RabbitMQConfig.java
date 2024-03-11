package com.rabbitmq.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.rabbitmq.consumer.RabbitMQJsonConsumer;

import java.util.HashMap;
import java.util.Map;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.HeadersExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;

@Configuration
public class RabbitMQConfig {

	@Value("${rabbitmq.queue.name}")
	private String queue;
	
	@Value("${rabbitmq.queue.json.name}")
	private String jsonQueue;

	@Value("${rabbitmq.exchange.name}")
	private String exchange;
	
	@Value("${rabbitmq.routing.key}")
	private String routingkey;
	
	@Value("${rabbitmq.routing.json.key}")
	private String routingJsonkey;
	
	//spring bean for rabbitmq queue
	@Bean
	public Queue queue(){
		return new Queue(queue);
	}
	
	@Bean
	public Queue jsonQueue(){
		return new Queue(jsonQueue);
	}
	
//	@Bean
//	public TopicExchange exchange() {
//		return new TopicExchange(exchange);
//	}


	@Bean
	public HeadersExchange exchange() {
		return new HeadersExchange(exchange);
	}
	

//	public DirectExchange directExchange() {
//		return new DirectExchange(exchange);
//	}
	
	@Bean
	public Binding binding() {
		return BindingBuilder
				.bind(queue())
				.to(exchange())
				.where("fname")
				.matches("yash");
	}
	
	@Bean
	public Binding jsonBinding() {
		return BindingBuilder
				.bind(jsonQueue())
				.to(exchange())
				.where("fname")
				.matches("harsh");
	}
	
	
//	public void binding() {
//		Map<String, Object> firstarg = new HashMap<>();
//		firstarg.put("x-match","any");
//		firstarg.put("h1","Header 1");
//	}
	
//	public void jsonBinding() {
//		Map<String, Object> secarg = new HashMap<>();
//		secarg.put("x-match","all");
//		secarg.put("h1","Header 1");
//		secarg.put("h2","Header 2");
//	}
	
	@Bean
	public MessageConverter converter() {
		return new Jackson2JsonMessageConverter();
	}
	
	@Bean
	public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory) {
		RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
		rabbitTemplate.setMessageConverter(converter());
		return rabbitTemplate;
	}
	
}