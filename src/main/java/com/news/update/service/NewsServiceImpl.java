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

import javax.swing.text.html.Option;
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
        try {

            Optional<News> news = newsRepository.findById(id);
            if (news.get() != null) {
                List<Comments> comments = commentsRepository.findAllByNewsIdOrderByCreateAtDesc(news.get().getId());
                NewsResponse newsResponse = new NewsResponse(news.get().getId(), news.get().getContentUz(), news.get().getContentRu(), news.get().getTitleUz(), news.get().getTitleRu(), news.get().getHeadAttachment(), news.get().getLikesCount(), news.get().getViewsCount(), news.get().getCategory(), news.get().getTags(), comments, news.get().getCreateAt());
                return newsResponse;
            }
        } catch (Exception e) {

        }
        return null;
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
                newsResponse = new NewsResponse(newsList.get(i).getId(), newsList.get(i).getContentUz(), newsList.get(i).getContentRu(), newsList.get(i).getTitleUz(), newsList.get(i).getTitleRu(), newsList.get(i).getHeadAttachment(), newsList.get(i).getLikesCount(), newsList.get(i).getViewsCount(), newsList.get(i).getCategory(), newsList.get(i).getTags(), comments, newsList.get(i).getCreateAt());
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
                newsResponse = new NewsResponse(newsList.get(i).getId(), newsList.get(i).getContentUz(), newsList.get(i).getContentRu(), newsList.get(i).getTitleUz(), newsList.get(i).getTitleRu(), newsList.get(i).getHeadAttachment(), newsList.get(i).getLikesCount(), newsList.get(i).getViewsCount(), newsList.get(i).getCategory(), newsList.get(i).getTags(), comments, newsList.get(i).getCreateAt());
                newsResponses.add(newsResponse);
            }
            return newsResponses;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public List<NewsResponse> getAllByPopularLikes() {
        try {
            List<News> newsList = newsRepository.findAllByOrderByLikesCountDesc();
            NewsResponse newsResponse = null;
            List<Comments> comments = null;
            List<NewsResponse> newsResponses = new ArrayList<>();
            for (int i = 0; i < newsList.size(); i++) {
                comments = new ArrayList<>();
                newsResponse = new NewsResponse();
                comments = commentsRepository.findAllByNewsIdOrderByCreateAtDesc(newsList.get(i).getId());
                newsResponse = new NewsResponse(newsList.get(i).getId(), newsList.get(i).getContentUz(), newsList.get(i).getContentRu(), newsList.get(i).getTitleUz(), newsList.get(i).getTitleRu(), newsList.get(i).getHeadAttachment(), newsList.get(i).getLikesCount(), newsList.get(i).getViewsCount(), newsList.get(i).getCategory(), newsList.get(i).getTags(), comments, newsList.get(i).getCreateAt());
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
            news.setContentUz(newsRequest.getContentUz().replaceAll("[\"]","'"));
            news.setContentRu(newsRequest.getContentRu().replaceAll("[\"]","'"));
            news.setTitleUz(newsRequest.getTitleUz());
            news.setTitleRu(newsRequest.getTitleRu());
            news.setTags(tagsRepository.findAllById(newsRequest.getTags()));
            if (attachmentService.findByHashId(hashId) != null) {
                news.setHeadAttachment(attachmentService.findByHashId(hashId));
            }
            System.out.println(news);
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
                news.setTitleUz(newsRequest.getTitleUz());
                news.setTitleRu(newsRequest.getTitleRu());
                news.setContentUz(newsRequest.getContentUz());
                news.setContentRu(newsRequest.getContentRu());
                news.setCategory(categoryRepository.getOne(newsRequest.getCategory_id()));
                news.setLikesCount(newsService1.get().getLikesCount());
                news.setViewsCount(newsService1.get().getViewsCount());
                news.setId(id);
                news.setTags(tagsRepository.findAllById(newsRequest.getTags()));
                news.setCreateAt(newsService1.get().getCreateAt());
                news.setHeadAttachment(attachmentService.findByHashId(newsRequest.getHash_id()));
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
            newsService1.setViewsCount(newsService1.getViewsCount() == null ? 1 : newsService1.getViewsCount() + 1);
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
    public boolean incrementLikes(String id) {
        try {
            News newsService1 = newsRepository.findById(id).get();
            newsService1.setLikesCount(newsService1.getLikesCount() == null ? 1 : newsService1.getLikesCount() + 1);
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
    public boolean decrementLikes(String id) {
        try {
            News newsService1 = newsRepository.findById(id).get();
            newsService1.setLikesCount(newsService1.getLikesCount() == 0 || newsService1.getLikesCount() == null ? 0 : (newsService1.getLikesCount() - 1));
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
            News news = newsRepository.findById(id).get();
            if (news != null) {
                news.setTags(null);
                System.out.println("TEST 1 - " + news);
                List<Comments> comments = commentsRepository.findAllByNewsIdOrderByCreateAtDesc(id);
                if (comments != null) {
                    commentsRepository.deleteAll(comments);
                }
                System.out.println("TEST 2 - " + news);
                news.setHeadAttachment(null);
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
            NewsResponse newsResponse = null;
            List<Comments> comments = null;
            List<NewsResponse> newsResponses = new ArrayList<>();
            for (int i = 0; i < tutorials.size(); i++) {
                comments = new ArrayList<>();
                newsResponse = new NewsResponse();
                comments = commentsRepository.findAllByNewsIdOrderByCreateAtDesc(tutorials.get(i).getId());
                newsResponse = new NewsResponse(tutorials.get(i).getId(), tutorials.get(i).getContentUz(), tutorials.get(i).getContentRu(), tutorials.get(i).getTitleUz(), tutorials.get(i).getTitleRu(), tutorials.get(i).getHeadAttachment(), tutorials.get(i).getLikesCount(), tutorials.get(i).getViewsCount(), tutorials.get(i).getCategory(), tutorials.get(i).getTags(), comments, tutorials.get(i).getCreateAt());
                newsResponses.add(newsResponse);
            }

            Map<String, Object> response = new HashMap<>();
            response.put("news", newsResponses);
            response.put("currentPage", pageTuts.getNumber());
            response.put("totalItems", pageTuts.getTotalElements());
            response.put("totalPages", pageTuts.getTotalPages());
            return response;
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }
    @Override
    public Map getPagesAll(int page, int size) {
        try {
            List<News> tutorials = new ArrayList<>();
            Pageable paging = PageRequest.of(page, size);
            Page<News> pageTuts = newsRepository.findAllByOrderByCreateAtDesc(paging);
            tutorials = pageTuts.getContent();
            NewsResponse newsResponse = null;
            List<Comments> comments = null;
            List<NewsResponse> newsResponses = new ArrayList<>();
            for (int i = 0; i < tutorials.size(); i++) {
                comments = new ArrayList<>();
                newsResponse = new NewsResponse();
                comments = commentsRepository.findAllByNewsIdOrderByCreateAtDesc(tutorials.get(i).getId());
                newsResponse = new NewsResponse(tutorials.get(i).getId(), tutorials.get(i).getContentUz(), tutorials.get(i).getContentRu(), tutorials.get(i).getTitleUz(), tutorials.get(i).getTitleRu(), tutorials.get(i).getHeadAttachment(), tutorials.get(i).getLikesCount(), tutorials.get(i).getViewsCount(), tutorials.get(i).getCategory(), tutorials.get(i).getTags(), comments, tutorials.get(i).getCreateAt());
                newsResponses.add(newsResponse);
            }

            Map<String, Object> response = new HashMap<>();
            response.put("news", newsResponses);
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
