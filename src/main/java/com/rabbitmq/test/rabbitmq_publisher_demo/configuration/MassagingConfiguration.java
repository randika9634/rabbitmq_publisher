package com.rabbitmq.test.rabbitmq_publisher_demo.configuration;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
@Configuration
public class MassagingConfiguration {
    @Autowired
    Environment environment;
    @Configuration
    public class MessagingConfig {



        @Bean
        public Queue queue() {
            return new Queue(environment.getProperty("rabbitmq.queue"));
        }

        @Bean
        public TopicExchange exchange() {
            return new TopicExchange(environment.getProperty("rabbitmq.exchange"));
        }

        @Bean
        public Binding binding(Queue queue, TopicExchange exchange) {
            return BindingBuilder.bind(queue).to(exchange).with(environment.getProperty("rabbitmq.routingkey"));
        }

        @Bean
        public MessageConverter converter() {
            return new Jackson2JsonMessageConverter();
        }

        @Bean
        public AmqpTemplate template(ConnectionFactory connectionFactory) {
            final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
            rabbitTemplate.setMessageConverter(converter());
            return rabbitTemplate;
        }
    }
}
