package com.rychkov.eads.controllers;

import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
@Slf4j
public class MainPageController {
    @GetMapping("/")
    public String showMainPage() {
        log.info("Opening main page");
        return "mainpage";
    }

}
