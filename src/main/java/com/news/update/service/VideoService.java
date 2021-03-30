package com.news.update.service;

import com.news.update.entity.VideoNews;
import com.news.update.payload.NewsRequest;
import com.news.update.payload.NewsResponse;
import com.news.update.payload.VideoRequest;

import java.util.List;
import java.util.Map;

public interface VideoService {

    public VideoNews getOne(String id);

    public List<VideoNews> getAll();

    public Map getPages(int page, int size);

    public boolean create(VideoRequest newsRequest);

    public boolean edit(String id, VideoRequest newsRequest);

    public boolean delete(String id);
//
//    public Map getPages(int page, int size);

}