package com.news.update.service;

import com.news.update.entity.ShortNews;
import com.news.update.entity.Social;
import com.news.update.payload.ShortNewsRequest;
import com.news.update.repository.SocialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SocialServiceImpl {


    @Autowired
    private SocialRepository socialRepository;

    public Social getOne() {
        try {
            Social social = socialRepository.findAll().get(0);
            return social;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public boolean create(Social social) {
        try {
            Social social1 = new Social();
            social1.setFacebook(social.getFacebook());
            social1.setInstagram(social.getInstagram());
            social1.setTelegram(social.getTelegram());
            social1.setTwitter(social.getTwitter());
            social1.setYoutube(social.getYoutube());

            if (socialRepository.save(social1) != null) {
                return true;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
    public boolean edit(Social social) {
        try {
            Social social1 = socialRepository.findAll().get(0);
            if (social1 != null) {
                social1.setFacebook(social.getFacebook());
                social1.setInstagram(social.getInstagram());
                social1.setTelegram(social.getTelegram());
                social1.setTwitter(social.getTwitter());
                social1.setYoutube(social.getYoutube());

                if (socialRepository.save(social1) != null) {
                    return true;
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

}
