package com.news.update.repository;

import com.news.update.entity.Admins;
import com.news.update.entity.News;
import com.news.update.entity.Tags;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TagsRepository extends JpaRepository<Tags, String> {

//    List<Tags> findTagsByNewsId(@Param("news_id") String news_id);

}
