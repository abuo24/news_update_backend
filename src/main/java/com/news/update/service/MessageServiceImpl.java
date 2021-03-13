package com.news.update.service;

import com.news.update.entity.Messages;
import com.news.update.entity.ShortNews;
import com.news.update.payload.ShortNewsRequest;
import com.news.update.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageRepository messageRepository;


    @Override
    public List<Messages> getAll() {
        try {
            List<Messages> newsList = messageRepository.findAllByOrderByCreateAtDesc();
            return newsList;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public boolean create(Messages messages) {
        try {

            Messages messages1 = new Messages();
            messages1.setFirstName(messages.getFirstName());
            messages1.setLastName(messages.getLastName());
            messages1.setMessage(messages.getMessage());
            if (messageRepository.save(messages1) != null) {
                return true;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

}
