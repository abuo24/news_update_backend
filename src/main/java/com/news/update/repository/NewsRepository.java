package com.news.update.repository;

import com.news.update.entity.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsRepository extends JpaRepository<News, String> {
    boolean findAllById(String id);

    boolean getById(String id);


    //    @Query(nativeQuery = true,
//            value = "select * from public.News n where n.category_id = ':categoryid';"
//            , countQuery = "SELECT count(*) FROM News n WHERE n.category_id = ':categoryid';")
    Page<News> findAllByCategoryId(
            @Param("categoryid")
            String categoryid,
            Pageable pageable);

}
