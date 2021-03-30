package com.news.update.repository;

import com.news.update.entity.Category;
import com.news.update.entity.Social;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SocialRepository extends JpaRepository<Social, String> {
    //    boolean findAllById(String id);
    void deleteById(String id);

//        Page<Category> findAll(Pageable pageable);
//    List<News> findAllByOrderByIdAsc();
//    Page<Category> findByPublished(boolean published, Pageable pageable);
//    Page<Category> findByTitleContaining (Pageable pageable);

}
