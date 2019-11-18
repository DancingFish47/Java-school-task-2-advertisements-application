package com.rychkov.eads.controllers;

import com.rychkov.eads.dto.BookDto;
import com.rychkov.eads.producers.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class StreamController {
    private final TopSellersProducer topSellersProducer;
    private final NewBooksProducer newBooksProducer;
    private final AddBookProducer addBookProducer;
    private final EditBookProducer editBookProducer;
    private final DeleteBookProducer deleteBookProducer;

    @GetMapping(path = "/topSellers", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Publisher<Integer> streamTopSellers() {
        return topSellersProducer.topSellersFlux();
    }

    @PostMapping(path = "/getTopSellers")
    public List<BookDto> getTopSellers() {
        log.info("Sending new 'top sellers books' list");
        return topSellersProducer.getTopSellersList();
    }

    @GetMapping(path = "/newBooks", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Publisher<Integer> streamNewBooks() {
        return newBooksProducer.newBooksFlux();
    }

    @PostMapping(path = "/getNewBooks")
    public List<BookDto> getNewBooks() {
        log.info("Sending new 'new books' list");
        return newBooksProducer.getNewBooksList();
    }

    @GetMapping(path = "/editBook", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Publisher<Integer> streamEditBook() {return editBookProducer.editBookFlux();}

    @PostMapping(path = "/getEditBook")
    public List<BookDto> getEditBook() {
        log.info("Sending new 'edit book' list");
        return editBookProducer.getEditBook();}

    @GetMapping(path = "/addBook", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Publisher<Integer> streamAddBook() {return addBookProducer.addBookFlux();}

    @PostMapping(path = "/getAddBook")
    public List<BookDto> getAddBook() {
        log.info("Sending new 'add book' list");
        return addBookProducer.getAddBook();}

    @GetMapping(path = "/deleteBook", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Publisher<Integer> streamDeleteBook() {return deleteBookProducer.addDeleteFlux();}

    @PostMapping(path = "/getDeleteBook")
    public List<Integer> getDeleteBook() {
        log.info("Sending new 'delete book' list");
        return deleteBookProducer.getDeleteBook();}

}
