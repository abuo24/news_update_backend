package com.news.update.service;

import com.news.update.entity.ShortNews;
import com.news.update.payload.ShortNewsRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface ShortNewsServise {

    public boolean create(ShortNewsRequest shortNewsRequest);
    public boolean edit(String id, ShortNewsRequest shortNewsRequest);
    public boolean delete(String id);
    public List<ShortNews> getAll();
    public Map getPages(String categoryid,int page, int size);
    public Map getAllPages(int page,int size);
    public List<Map> getHeadShortNews();
}
