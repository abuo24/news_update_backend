package com.news.update.service;

import com.news.update.entity.Category;
import com.news.update.entity.News;
import com.news.update.entity.ShortNews;
import com.news.update.model.Result;
import com.news.update.model.ResultSucces;
import com.news.update.repository.CategoryRepository;
import com.news.update.repository.NewsRepository;
import com.news.update.repository.ShortNewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

@Service
public class CategoryServiceImpl implements CategoryService {


    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private NewsRepository newsRepository;
    @Autowired
    private ShortNewsRepository shortNewsRepository;

    @Override
    public List<Category> getAll() {
        try {
            List<Category> categories = categoryRepository.findAll();
            return categories;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public boolean create(Category category) {
        try {
            if (categoryRepository.save(category) != null) {
                return true;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }


    @Override
    public Map getPages(int page, int size) {
        try {
            List<Category> tutorials = new ArrayList<Category>();
            Pageable paging = PageRequest.of(page, size, Sort.by("name"));

            Page<Category> pageTuts = categoryRepository.findAll(paging);
            tutorials = pageTuts.getContent();

            Map<String, Object> response = new HashMap<>();
            response.put("category", tutorials);
            response.put("currentPage", pageTuts.getNumber());
            response.put("totalItems", pageTuts.getTotalElements());
            response.put("totalPages", pageTuts.getTotalPages());

            return response;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public boolean edit(String id, Category category) {
        try {
            Optional<Category> category1 = categoryRepository.findById(id);
            if (category1 != null) {
                category.setId(id);
                category.setCreateAt(category1.get().getCreateAt());
                if (categoryRepository.save(category) != null) {
                    return true;
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean delete(String id, String category) {
        try {
            if (categoryRepository.findById(id) != null) {
                Category category1 = categoryRepository.findById(category).get();
                List<News> newsList = newsRepository.findAllByCategoryId(id);
                newsList.forEach(item -> {
                    item.setCategory(categoryRepository.findById(category).get());
                    newsRepository.save(item);
                });
                List<ShortNews> shortNews = shortNewsRepository.findAllByCategoryId(id);
                shortNews.forEach(item -> {
                    item.setCategory(categoryRepository.findById(category).get());
                    shortNewsRepository.save(item);
                });
                categoryRepository.deleteById(id);
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

}
