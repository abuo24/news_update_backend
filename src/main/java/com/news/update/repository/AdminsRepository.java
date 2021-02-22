package com.news.update.repository;

import com.news.update.entity.Admins;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminsRepository extends JpaRepository<Admins, String> {
    Optional<Admins> findByUsername(String username);
}
