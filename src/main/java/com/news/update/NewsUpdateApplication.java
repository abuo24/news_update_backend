package com.news.update;

import com.news.update.entity.Admins;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NewsUpdateApplication {

    Admins admin = new Admins();


    public static void main(String[] args) {
        SpringApplication.run(NewsUpdateApplication.class, args);
    }

}
