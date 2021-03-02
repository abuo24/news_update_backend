package com.news.update.service;

import com.news.update.entity.Messages;

import java.util.List;

public interface MessageService {
    public List<Messages> getAll();
    public boolean create(Messages messages);
}
