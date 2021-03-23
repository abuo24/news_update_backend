package com.news.update.repository;

import com.news.update.entity.News;
import com.news.update.entity.VideoNews;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VideoRepository extends JpaRepository<VideoNews, String> {
    boolean findAllById(String id);

    List<VideoNews> findAll();
//    List<News> findAllByOrderByViewsCountDesc();

//    List<News> findAllByOrderByLikesCountDesc();

    List<VideoNews> findAllByOrderByCreateAtDesc();

    boolean getById(String id);

    Page<VideoNews> findAllByOrderByCreateAtDesc(Pageable pageable);

//    List<News> findAllByCategoryId(@Param("categoryid") String categoryid);

//    List<News> findAllOrderByViewsCountDesc();

//

//    List<News> getAllByHeadAttachmentId(String headAttachment_id);

}
