package com.hackeriahn;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.StopWatch;

import java.util.stream.IntStream;

@SpringBootApplication
@EnableScheduling
@Slf4j
public class JazzSenderApplication {

    private final RabbitTemplate rabbitTemplate;

    public JazzSenderApplication(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    private final static String queueName = "jazz";

    @Bean
    Queue queue() {
        return new Queue(queueName, false);
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange(queueName + "-exchange");
    }

    @Bean
    Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(queueName);
    }

    public static void main(String[] args) {
        SpringApplication.run(JazzSenderApplication.class, args);
    }

//    @Scheduled(fixedDelay = 1000 * 10)
    public void sendMessage() {
        IntStream.range(1, 101)
//                .parallel()
                .forEach(
                        value -> {
                            log.info("Sending message : {}", value);
                            rabbitTemplate.convertAndSend(JazzSenderApplication.queueName,
                                    "Jazz JackRabbit : " + value);
                        }
                );
    }

}
