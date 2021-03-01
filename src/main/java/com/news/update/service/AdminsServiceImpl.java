package com.news.update.service;

import com.news.update.entity.Admins;
import com.news.update.model.Result;
import com.news.update.model.ResultSucces;
import com.news.update.payload.AdminRequest;
import com.news.update.repository.AdminsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AdminsServiceImpl implements AdminService {
    @Autowired
    private AdminsRepository adminRepository;


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
    public boolean edit(String id, AdminRequest admin) {

        try {
            Admins admins = adminRepository.findById(id).get();
            admins.setUsername(admin.getUsername());
            admins.setFullname(admin.getFullname());
            admins.setSocial(admin.getSocial());
            admins.setPhone(admin.getPhone());
            if (admin.getOld_password() != null && admin.getNew_password() != null) {
                admins.setPassword(admin.getNew_password());
            }
            Admins admin1 = adminRepository.save(admins);
            if (admin1 != null) {
                return true;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }
}
