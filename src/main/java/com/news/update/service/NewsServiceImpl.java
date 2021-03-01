package com.news.update.service;

import com.news.update.entity.Category;
import com.news.update.entity.Comments;
import com.news.update.entity.News;
import com.news.update.entity.Tags;
import com.news.update.model.Result;
import com.news.update.model.ResultSucces;
import com.news.update.payload.NewsRequest;
import com.news.update.payload.NewsResponse;
import com.news.update.payload.PostRequestLikesAndViews;
import com.news.update.repository.CategoryRepository;
import com.news.update.repository.CommentsRepository;
import com.news.update.repository.NewsRepository;
import com.news.update.repository.TagsRepository;
import javassist.runtime.Desc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.*;

@Service
public class NewsServiceImpl implements NewsService {

    @Autowired
    private NewsRepository newsRepository;
    @Autowired
    private TagsRepository tagsRepository;
    @Autowired
    private CommentsRepository commentsRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private AttachmentService attachmentService;


    @Override
    public NewsResponse getOne(String id) {
        News news = newsRepository.findById(id).get();
        List<Comments> comments = commentsRepository.findAllByNewsIdOrderByCreateAtDesc(news.getId());
        NewsResponse newsResponse = new NewsResponse(news.getId(), news.getContent(), news.getTitle(), news.getHeadAttachment(), news.getYoutube(), news.getLikesCount(), news.getViewsCount(), news.getCategory(), news.getTags(), comments, news.getCreateAt());
        if (news == null) {
            return null;
        }
        return newsResponse;
    }

    @Override
    public List<NewsResponse> getAll() {
        try {
            List<News> newsList = newsRepository.findAllByOrderByCreateAtDesc();
            NewsResponse newsResponse = null;
            List<Comments> comments = null;
            List<NewsResponse> newsResponses = new ArrayList<>();
            for (int i = 0; i < newsList.size(); i++) {
                comments = new ArrayList<>();
                newsResponse = new NewsResponse();
                comments = commentsRepository.findAllByNewsIdOrderByCreateAtDesc(newsList.get(i).getId());
                newsResponse = new NewsResponse(newsList.get(i).getId(), newsList.get(i).getContent(), newsList.get(i).getTitle(), newsList.get(i).getHeadAttachment(), newsList.get(i).getYoutube(), newsList.get(i).getLikesCount(), newsList.get(i).getViewsCount(), newsList.get(i).getCategory(), newsList.get(i).getTags(), comments, newsList.get(i).getCreateAt());
                newsResponses.add(newsResponse);
            }
            return newsResponses;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public List<NewsResponse> getAllPostsByPopular() {
        try {
            List<News> newsList = newsRepository.findAllByOrderByViewsCountDesc();
            NewsResponse newsResponse = null;
            List<Comments> comments = null;
            List<NewsResponse> newsResponses = new ArrayList<>();
            for (int i = 0; i < newsList.size(); i++) {
                comments = new ArrayList<>();
                newsResponse = new NewsResponse();
                comments = commentsRepository.findAllByNewsIdOrderByCreateAtDesc(newsList.get(i).getId());
                newsResponse = new NewsResponse(newsList.get(i).getId(), newsList.get(i).getContent(), newsList.get(i).getTitle(), newsList.get(i).getHeadAttachment(), newsList.get(i).getYoutube(), newsList.get(i).getLikesCount(), newsList.get(i).getViewsCount(), newsList.get(i).getCategory(), newsList.get(i).getTags(), comments, newsList.get(i).getCreateAt());
                newsResponses.add(newsResponse);
            }
            return newsResponses;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public boolean create(String hashId, NewsRequest newsRequest) {
        try {
            News news = new News();
            news.setCategory(categoryRepository.getOne(newsRequest.getCategory_id()));
            news.setContent(newsRequest.getContent());
            news.setTitle(newsRequest.getTitle());
            news.setTags(tagsRepository.findAllById(newsRequest.getTags()));
            if (attachmentService.findByHashId(hashId) != null) {
                news.setHeadAttachment(attachmentService.findByHashId(hashId));
            }
            ;
            if (newsRepository.save(news) != null) {
                return true;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
        return false;
    }

    @Override
    public boolean edit(String id, NewsRequest newsRequest) {
        try {
            Optional<News> newsService1 = newsRepository.findById(id);
            if (newsService1.get() != null) {
                News news = new News();
                news.setTitle(newsRequest.getTitle());
                news.setContent(newsRequest.getContent());
                news.setCategory(categoryRepository.getOne(newsRequest.getCategory_id()));
                news.setLikesCount(newsRequest.getLikesCount());
                news.setViewsCount(newsRequest.getViewsCount());
                news.setId(id);
                news.setTags(tagsRepository.findAllById(newsRequest.getTags()));
                news.setCreateAt(newsService1.get().getCreateAt());
                if (newsRequest.getFile() != null && !newsRequest.getFile().equals(newsRepository.findById(id).get().getHeadAttachment())) {
                    String hashId = attachmentService.save(newsRequest.getFile());
                    news.setHeadAttachment(attachmentService.findByHashId(hashId));
                }
                ;
                if (newsRepository.save(news) != null) {
                    return true;
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
        return false;
    }

    @Override
    public boolean editViews(String id) {
        try {
            News newsService1 = newsRepository.findById(id).get();
            newsService1.setCategory(categoryRepository.getOne(newsService1.getCategory().getId()));
            newsService1.setLikesCount(newsService1.getLikesCount());
            newsService1.setViewsCount(newsService1.getViewsCount()==null?1:newsService1.getViewsCount()+1);
            newsService1.setId(id);
            newsService1.setCreateAt(newsService1.getCreateAt());
            if (newsRepository.save(newsService1) != null) {
                return true;
            }
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
        return false;
    }

    @Override
    public boolean delete(String id) {
        try {
            if (newsRepository.findById(id).get() != null) {
                if (newsRepository.findById(id).get().getHeadAttachment() != null) {
                    attachmentService.delete(newsRepository.findById(id).get().getHeadAttachment().getHashId());
                }
                List<Comments> comments = commentsRepository.findAllByNewsIdOrderByCreateAtDesc(id);
                if (comments != null) {
                    commentsRepository.deleteAll(comments);
                }
                newsRepository.deleteById(id);
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    @Override
    public Map getPages(String categoryid, int page, int size) {
        try {
            List<News> tutorials = new ArrayList<>();
            Pageable paging = PageRequest.of(page, size);
            Page<News> pageTuts = newsRepository.findAllByCategoryIdOrderByCreateAtDesc(categoryid, paging);
            tutorials = pageTuts.getContent();
            System.out.println(tutorials);
            Map<String, Object> response = new HashMap<>();
            response.put("news", tutorials);
            response.put("currentPage", pageTuts.getNumber());
            response.put("totalItems", pageTuts.getTotalElements());
            response.put("totalPages", pageTuts.getTotalPages());
            return response;
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

}
