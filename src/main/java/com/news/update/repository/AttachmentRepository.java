package com.news.update.repository;

import com.news.update.entity.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttachmentRepository extends JpaRepository< Attachment, String> {
    Attachment findByHashId(String hashId);
}
