package com.challenge.opentrends;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class OpentrendsApplication {
    public static final Logger log = LoggerFactory.getLogger(OpentrendsApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(OpentrendsApplication.class, args);
    }

    @Bean
    public RestTemplate rest() {
        return new RestTemplate();
    }

}
