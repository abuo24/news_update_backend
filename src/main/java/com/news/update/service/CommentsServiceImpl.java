package com.news.update.service;

import com.news.update.entity.Comments;
import com.news.update.entity.News;
import com.news.update.model.Result;
import com.news.update.model.ResultSucces;
import com.news.update.payload.CommentsRequest;
import com.news.update.payload.NewsRequest;
import com.news.update.repository.CommentsRepository;
import com.news.update.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CommentsServiceImpl implements CommentsService {

    @Autowired
    private CommentsRepository commentsRepository;

    @Autowired
    private NewsRepository newsRepository;

    @Override
    public boolean create(String id, CommentsRequest commentsRequest) {
        try {
            Comments comments = new Comments();
            comments.setMessage(commentsRequest.getMessage());
            comments.setAuthor(commentsRequest.getAuthor());
            comments.setAuthorMail(commentsRequest.getAuthorMail());
            if (newsRepository.findById(id).get() != null) {
                comments.setNews(newsRepository.findById(id).get());
                if (commentsRequest.getComments_id() != null && commentsRepository.findById(commentsRequest.getComments_id()).get() != null) {
                    comments.setComments(commentsRepository.findById(commentsRequest.getComments_id()).get());
                }
                Comments comments1 = commentsRepository.save(comments);
                if (comments1 != null) {
                    return true;
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage() + "/n" + e);
        }
        return false;
    }


    @Override
    public boolean edit(String id, CommentsRequest commentsRequest) {
        try {
            Optional<Comments> comments = commentsRepository.findById(id);
            if (comments != null) {
                Comments comments1 = new Comments();
                comments1.setMessage(commentsRequest.getMessage());
                comments1.setAuthorMail(commentsRequest.getAuthorMail());
                comments1.setAuthor(commentsRequest.getAuthor());
                comments1.setComments(commentsRepository.getOne(commentsRequest.getComments_id()));
                if (commentsRepository.save(comments1) != null) {
                    return true;
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean delete(String id) {
        try {
            Optional<Comments> comments = commentsRepository.findById(id);

            if (comments.get()!=null) {
                List<Comments> commentsList = commentsRepository.findAllByComments_Id(id);
                if (!commentsList.isEmpty()) {
                    for (Comments c : commentsList) {
                        c.setComments(null);
                        commentsRepository.save(c);
                    }
                }
                comments.get().setComments(null);

                commentsRepository.deleteById(id);
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
    public Map getAll(String newsid) {
        try {
            List<Comments> tutorials = new ArrayList<>();

            List<Comments> pageTuts = commentsRepository.findAllByNewsIdOrderByCreateAtDesc(newsid);
            System.out.println(tutorials);

            Map<String, Object> response = new HashMap<>();
            response.put("comments", pageTuts);

            return response;
        } catch (Exception e) {
            System.out.println(e);
        }

        return null;
    }

}
