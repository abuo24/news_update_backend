package com.news.update.service;

import com.news.update.entity.Admins;
import com.news.update.model.Result;
import com.news.update.model.ResultSucces;
import com.news.update.payload.AdminRequest;
import com.news.update.payload.PasswordRequest;
import com.news.update.repository.AdminsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdminsServiceImpl implements AdminService {
    @Autowired
    private AdminsRepository adminRepository;


    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public boolean save(Admins admin) {
        try {
            Admins admin1 = adminRepository.save(admin);

            if (admin1 != null) {
                return true;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }

    @Override
    public Admins edit(String id, AdminRequest admin) {

        try {
            Admins admins = adminRepository.findById(id).get();
            admins.setUsername(admin.getUsername());
            admins.setFullname(admin.getFullname());
            admins.setSocial(admin.getSocial());
            admins.setPhone(admin.getPhone());
            return adminRepository.save(admins);
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    @Override
    public Admins editPassword(String id, PasswordRequest request) {

        try {
            Admins admins = adminRepository.findById(id).get();
            if (passwordEncoder.matches(request.getOld_password(),admins.getPassword()))
            {
                admins.setPassword(passwordEncoder.encode(request.getNew_password()));
            }
            return adminRepository.save(admins);
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }
}
