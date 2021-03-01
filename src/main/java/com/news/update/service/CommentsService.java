package com.news.update.service;


import com.news.update.payload.CommentsRequest;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface CommentsService {

    public boolean create(String id, CommentsRequest commentsRequest);

    public boolean edit(String id, CommentsRequest commentsRequest);

    public boolean delete(String id);

    public Map getAll(String newsid);

}

