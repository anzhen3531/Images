package com.anzhen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author AnZhen
 */
@SpringBootApplication
@EnableScheduling
public class ImagesApplication {
    public static void main(String[] args) {
        SpringApplication.run(ImagesApplication.class, args);
    }
}
