package com.rychkov.eads.producers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.rychkov.eads.dto.BookDto;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;

@Slf4j
@Component
public class NewBooksProducer {
    private ObjectMapper objectMapper = new ObjectMapper();
    private HashMap<Integer, List<BookDto>> hashMap = new HashMap<>();

    @Value("${subscription.delay}")
    private int delay;

    public Flux<Integer> newBooksFlux() {
        log.info("New books list hash = " + hashMap.hashCode());

        return Flux.just(hashMap.hashCode()).delaySubscription(Duration.ofSeconds(delay));
    }

    @RabbitListener(queues = "${jsa.rabbitmq.queue2}")
    @SneakyThrows
    public void listenNewBooks(String msg) {
        log.info("New 'New books' message received");

        CollectionType typeReference = TypeFactory.defaultInstance().constructCollectionType(List.class, BookDto.class);
        List<BookDto> bookDtos = objectMapper.readValue(msg, typeReference);
        hashMap.put(0, bookDtos);
    }

    public List<BookDto> getNewBooksList() {
        return hashMap.get(0);
    }
}
