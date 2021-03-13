package com.news.update.repository;

import com.news.update.entity.Messages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Messages, String> {

    List<Messages> findAllByOrderByCreateAtDesc();
}
