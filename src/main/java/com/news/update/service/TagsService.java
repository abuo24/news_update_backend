package com.news.update.service;

import com.news.update.entity.Tags;

import java.util.List;

public interface TagsService {
    public Tags getOne(String id);
    public List<Tags> getAll();
    public boolean delete(String id);
    public List<Tags> getAllByTagsId(List<String> list);
}
