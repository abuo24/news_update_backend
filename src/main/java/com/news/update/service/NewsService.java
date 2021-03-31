package com.news.update.service;

import com.news.update.entity.News;
import com.news.update.payload.NewsRequest;
import com.news.update.payload.NewsResponse;
import com.news.update.payload.PostRequestLikesAndViews;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface NewsService {
    public NewsResponse getOne(String id);

    public List<NewsResponse> getAll();

    public Map getPagesAll( int page, int size);

    public List<NewsResponse> getAllPostsByPopular();
    public List<NewsResponse> getAllPostsByPopularByLatest();
    public boolean create(String hashId, NewsRequest newsRequest);

    public boolean edit(String id, NewsRequest newsRequest);


    public boolean editViews(String id);

    public boolean delete(String id);

    public boolean decrementLikes(String id);

    public boolean incrementLikes(String id);

    public Map getPages(String categoryid, int page, int size);

    //    public List<NewsResponse> getAllPostsByPopular();
//    public List<NewsResponse> getAllPostsByPopular();
    public List<NewsResponse> getAllByPopularLikes();

}