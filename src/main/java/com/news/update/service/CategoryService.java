package com.news.update.service;

import com.news.update.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface CategoryService {
    public List<Category> getAll();
    public boolean create(Category category);
    public boolean edit(String id, Category category);
    public boolean delete(String id, String category);
    public Map getPages(int page, int size);
}
