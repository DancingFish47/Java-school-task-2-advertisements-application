package com.rychkov.eads.services;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

@Component
public class Receiver {
    @RabbitListener(queues = "${jsa.rabbitmq.queue1}")
    public void recievedMessage1(String msg) {
        System.out.println("Recieved Message: " + msg);
    }
    @RabbitListener(queues = "${jsa.rabbitmq.queue2}")
    public void recievedMessage2(String msg) {
        System.out.println("Recieved Message: " + msg);
    }
    @RabbitListener(queues = "${jsa.rabbitmq.queue3}")
    public void recievedMessage3(String msg) {
        System.out.println("Recieved Message: " + msg);
    }
}
