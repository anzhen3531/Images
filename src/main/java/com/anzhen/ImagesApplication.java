package com.anzhen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author AnZhen
 */
@SpringBootApplication
@EnableScheduling
@EnableSwagger2
public class ImagesApplication {
  public static void main(String[] args) {
    SpringApplication.run(ImagesApplication.class, args);
  }
}
