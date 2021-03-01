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

import java.util.List;

@Repository
public interface NewsRepository extends JpaRepository<News, String> {
    boolean findAllById(String id);

//    List<News> findAll(Sort sort);
    List<News> findAllByOrderByViewsCountDesc();
    List<News> findAllByOrderByCreateAtDesc();
//    findAllByOrderByIdAsc

    boolean getById(String id);

    Page<News> findAllByCategoryIdOrderByCreateAtDesc(
            @Param("categoryid")
                    String categoryid,
            Pageable pageable);

//    @Query(value = "select * from public.News where views_count IS Not Null Order by views_count desc")
//    default List<News> findAllByViewsCountOrderByViewsCountDescNative() {
//
//    }


}
