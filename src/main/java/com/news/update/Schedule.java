package com.news.update;

import com.news.update.entity.Attachment;
import com.news.update.entity.News;
import com.news.update.repository.AttachmentRepository;
import com.news.update.repository.NewsRepository;
import com.news.update.service.AttachmentService;
import com.news.update.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Schedule {
    @Autowired
    private AttachmentRepository attachmentRepository;
    @Autowired
    private AttachmentService attachmentService;

    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private NewsService newsService;

    @Scheduled(cron = "0 0 * * * ?")
    public void scheduleTaskUsingCronExpression() {
        System.out.println("TEST 1\n");
        List<Attachment> getAll = attachmentRepository.findAll();
        getAll.forEach(item -> {
            System.out.println("item 1 \n+"+item);

                    List<News> newsList = newsRepository.getAllByHeadAttachmentId(item.getId());
                    if (newsList.isEmpty()) {
                        System.out.println("test " +newsList);

                        attachmentService.delete(item.getHashId());
                    }
                }
        );
    }
}
