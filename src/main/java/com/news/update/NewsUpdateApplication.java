package com.news.update;

import com.news.update.entity.Admins;
import com.news.update.entity.Attachment;
import com.news.update.entity.News;
import com.news.update.repository.AttachmentRepository;
import com.news.update.repository.NewsRepository;
import com.news.update.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@SpringBootApplication
@EnableScheduling
//@ComponentScan(basePackages = "com.news.update")
@PropertySource("classpath:application.properties")
public class NewsUpdateApplication {


    public static void main(String[] args) {

        SpringApplication.run(NewsUpdateApplication.class, args);
    }

}
