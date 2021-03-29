package com.news.update;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@PropertySource("classpath:application.properties")
public class NewsUpdateApplication {
    public static void main(String[] args) {
        SpringApplication.run(NewsUpdateApplication.class, args);
    }
}
