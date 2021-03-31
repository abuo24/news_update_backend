package com.news.update.repository;

import com.news.update.entity.Comments;
import com.news.update.entity.News;
import com.news.update.entity.Tags;
import com.news.update.payload.NewsResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface NewsRepository extends JpaRepository<News, String> {
    boolean findAllById(String id);

    List<News> findAllByOrderByViewsCountDesc();

    List<News> findAllByCreateAtLessThanAndCreateAtGreaterThanAndViewsCountIsNotNullOrderByViewsCountDesc(Date date, Date days);



    List<News> findAllByOrderByLikesCountDesc();

    List<News> findAllByOrderByCreateAtDesc();
//    findAllByOrderByIdAsc

    List<News> findAllByTagsId(String tags_id);

    boolean getById(String id);

    Page<News> findAllByCategoryIdOrderByCreateAtDesc(
            @Param("categoryid")
                    String categoryid,
            Pageable pageable);

    Page<News> findAllByOrderByCreateAtDesc(
            Pageable pageable);

    List<News> findAllByCategoryId(@Param("categoryid") String categoryid);

//    List<News> findAllOrderByViewsCountDesc();

    @Query(value = "select sum(News.likes_count) from News where News.likes_count is not null",
            nativeQuery = true
    )
    Long getSumma();

    @Query(value = "select sum(News.views_count) from News where News.views_count is not null",
            nativeQuery = true
    )
    Long getSummaViews();

    @Query(value = "select count(comments) from public.Comments",
            nativeQuery = true)
    Long getSummaComments();

    List<News> getAllByHeadAttachmentId(String headAttachment_id);

}
