package com.news.update.repository;

import com.news.update.entity.Admins;
import com.news.update.entity.Tags;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TagsRepository extends JpaRepository<Tags, String> {


}
