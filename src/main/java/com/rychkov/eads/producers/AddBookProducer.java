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

@Component
@Slf4j
public class AddBookProducer {
    private ObjectMapper objectMapper = new ObjectMapper();
    private HashMap<Integer, BookDto> hashMap = new HashMap<>();

    @Value("${subscription.delay}")
    private int delay;

    public Flux<Integer> addBookFlux() {
        log.info("Add book hash = " + hashMap.hashCode());
        return Flux.just(hashMap.hashCode()).delaySubscription(Duration.ofSeconds(delay));
    }

    @RabbitListener(queues = "${jsa.rabbitmq.addBookQueue}")
    @SneakyThrows
    public void listenNewBooks(String msg) {
        log.info("New 'Add book' message received by listener");

        BookDto bookDtos = objectMapper.readValue(msg, BookDto.class);
        hashMap.put(hashMap.size(), bookDtos);
    }

    public List<BookDto> getAddBook() {
        List<BookDto> bookDtos = new ArrayList<>();
        for (int i = 0; i < hashMap.size(); i++) {
            bookDtos.add(hashMap.get(i));
        }
        return bookDtos;
    }

    @Scheduled(cron = "0 0/59 * * * *")
    private void clearHash(){
        this.hashMap.clear();
    }
}
