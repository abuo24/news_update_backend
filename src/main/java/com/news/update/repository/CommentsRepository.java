package com.news.update.repository;

import com.news.update.entity.Comments;
import com.news.update.entity.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentsRepository extends JpaRepository< Comments, String> {

    List<Comments> findAllByNewsIdOrderByCreateAtDesc(
            @Param("newsid")
                    String newsid);
    List<Comments> findAllByComments_Id(
            @Param("id")
                    String id);

}
