package com.equity4profit.liveupdateservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiveUpdateServiceApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(LiveUpdateServiceApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(LiveUpdateServiceApplication.class, args);
    }

}
