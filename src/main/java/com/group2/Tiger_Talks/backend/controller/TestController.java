package com.group2.Tiger_Talks.backend.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.group2.Tiger_Talks.backend.model.Utils.CROSS_ORIGIN_HOST_NAME;

@RestController
public class TestController {

    @CrossOrigin(origins = CROSS_ORIGIN_HOST_NAME)
    @GetMapping("/api/hello")
    public String test() {
        return "Hello World!";
    }
}