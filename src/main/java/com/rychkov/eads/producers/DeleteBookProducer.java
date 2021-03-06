package com.rychkov.eads.producers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rychkov.eads.dto.BookDto;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j
@Component
public class DeleteBookProducer {
    private ObjectMapper objectMapper = new ObjectMapper();
    private HashMap<Integer, Integer> hashMap = new HashMap<>();

    @Value("${subscription.delay}")
    private int delay;

    public Flux<Integer> addDeleteFlux() {
        log.info("Delete book hash = " + hashMap.hashCode());
        return Flux.just(hashMap.hashCode()).delaySubscription(Duration.ofSeconds(delay));
    }

    @RabbitListener(queues = "${jsa.rabbitmq.deleteBookQueue}")
    @SneakyThrows
    public void listenNewBooks(String msg) {
        log.info("New 'Delete book' message received by listener");

        Integer bookId = objectMapper.readValue(msg, Integer.class);
        hashMap.put(hashMap.size(), bookId);
    }

    public List<Integer> getDeleteBook() {
        List<Integer> bookIds = new ArrayList<>();
        for (int i = 0; i < hashMap.size(); i++) {
            bookIds.add(hashMap.get(i));
        }
        return bookIds;
    }

    @Scheduled(cron = "0 0/59 * * * *")
    private void clearHash(){
        this.hashMap.clear();
    }
}


