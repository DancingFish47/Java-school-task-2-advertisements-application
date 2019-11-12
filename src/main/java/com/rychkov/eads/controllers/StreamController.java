package com.rychkov.eads.controllers;

import com.rychkov.eads.dto.BookDto;
import com.rychkov.eads.producers.NewBooksProducer;
import com.rychkov.eads.producers.TopSellersProducer;
import lombok.RequiredArgsConstructor;
import org.reactivestreams.Publisher;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class StreamController {
    private final TopSellersProducer topSellersProducer;
    private final NewBooksProducer newBooksProducer;

    @GetMapping(path = "/topSellers",
            produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Publisher<Integer> streamTopSellers() {
        return topSellersProducer.topSellersFlux();
    }

    @PostMapping(path = "/getTopSellers")
    public List<BookDto> getTopSellers() {
        return topSellersProducer.getTopSellersList();
    }

    @GetMapping(path = "/newBooks",
            produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Publisher<Integer> streamNewBooks() {
        return newBooksProducer.newBooksFlux();
    }

    @PostMapping(path = "/getNewBooks")
    public List<BookDto> getNewBooks() {
        return newBooksProducer.getNewBooksList();
    }
    /*
    @GetMapping(path = "/editBook",
            produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Publisher<String> streamEditBook() {

    }

    @GetMapping(path = "/deleteBook",
            produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Publisher<String> streamDeleteBook() {

    }*/


}
