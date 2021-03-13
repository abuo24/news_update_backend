package com.news.update.service;

import com.news.update.entity.Admins;
import com.news.update.payload.AdminRequest;
import com.news.update.payload.PasswordRequest;
import org.springframework.http.ResponseEntity;

public interface AdminService {
    public boolean save(Admins admin);
//    public boolean edit(String id, AdminRequest admin);
    public Admins edit(String id, AdminRequest admin);
    public Admins editPassword(String id, PasswordRequest request);
}
