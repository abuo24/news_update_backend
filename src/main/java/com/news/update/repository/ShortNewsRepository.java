package com.news.update.repository;

import com.news.update.entity.News;
import com.news.update.entity.ShortNews;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ShortNewsRepository extends JpaRepository<ShortNews, String> {
    Page<ShortNews> findAllByCategoryId(
            @Param("categoryid")
                    String categoryid,
            Pageable pageable);
}
