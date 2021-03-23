package com.news.update.service;

import com.news.update.entity.Comments;
import com.news.update.entity.News;
import com.news.update.entity.VideoNews;
import com.news.update.payload.NewsRequest;
import com.news.update.payload.NewsResponse;
import com.news.update.payload.VideoRequest;
import com.news.update.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class VideoServiceImpl implements VideoService {

    @Autowired
    private NewsRepository newsRepository;
    @Autowired
    private VideoRepository videoRepository;
    @Autowired
    private CommentsRepository commentsRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private AttachmentService attachmentService;


    @Override
    public VideoNews getOne(String id) {
        VideoNews news = videoRepository.findById(id).get();
        if (news == null) {
            return null;
        }
        return news;
    }

    @Override
    public List<VideoNews> getAll() {
        try {
            List<VideoNews> newsList = videoRepository.findAllByOrderByCreateAtDesc();
            return newsList;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public boolean create(VideoRequest videoRequest) {
        try {
            VideoNews news = new VideoNews();
//            news.setCategory(categoryRepository.getOne(videoRequest.getCategory_id()));
            news.setLink(videoRequest.getLink());
            news.setTitleRu(videoRequest.getTitleRu());
            news.setTitleUz(videoRequest.getTitleUz());
            if (videoRepository.save(news) != null) {
                return true;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
        return false;
    }

    @Override
    public boolean edit(String id, VideoRequest videoRequest) {
        try {
            Optional<VideoNews> news = videoRepository.findById(id);
            if (news.get() != null) {
                VideoNews videoNews = new VideoNews();
                videoNews.setId(id);
                videoNews.setTitleRu(videoRequest.getTitleRu());
                videoNews.setTitleUz(videoRequest.getTitleUz());
                videoNews.setLink(videoRequest.getLink());
                if (videoRepository.save(videoNews) != null) {
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
    public boolean delete(String id) {
        try {
            VideoNews news = videoRepository.findById(id).get();
            if (news != null) {
                videoRepository.deleteById(id);
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
    public Map getPages(int page, int size) {
        try {
            List<VideoNews> tutorials = new ArrayList<>();
            Pageable paging = PageRequest.of(page, size);
            Page<VideoNews> pageTuts = videoRepository.findAllByOrderByCreateAtDesc(paging);
            tutorials = pageTuts.getContent();
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
