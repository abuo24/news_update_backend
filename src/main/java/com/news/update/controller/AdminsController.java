package com.news.update.controller;


import com.news.update.entity.*;
import com.news.update.model.Result;
import com.news.update.model.ResultSucces;
import com.news.update.payload.*;
import com.news.update.repository.AdminsRepository;
import com.news.update.repository.NewsRepository;
import com.news.update.security.JwtTokenProvider;
import com.news.update.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@CrossOrigin
@Controller
@RequestMapping("/api/admin")
public class AdminsController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private AdminsServiceImpl adminService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Autowired
    private CategoryService categoryService;

    @Autowired
    private NewsService newsService;

    @Autowired
    private ShortNewsServise shortNewsServise;

    @Autowired
    private AttachmentService attachmentService;

    @Autowired
    private AdminsRepository adminsRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private NewsRepository newsRepository;


    @Autowired
    private SocialServiceImpl socialService;


    @PutMapping("/edit")
    public ResponseEntity editAdmin(@RequestBody AdminRequest adminRequest, HttpServletRequest request) {
        try {
            Admins user = adminsRepository.findByUsername(jwtTokenProvider.getUser(jwtTokenProvider.resolveToken(request)));

            Admins admins1 = adminService.edit(user.getId(), adminRequest);
            Map<Object, Object> map = new HashMap<>();
            String token = "";
            if (user.getUsername().equals(admins1.getUsername())) {
                token = jwtTokenProvider.resolveToken(request);
            } else {
                token = jwtTokenProvider.createToken(admins1.getUsername(), admins1.getRoles());
            }
            map.put("succes", true);
            map.put("username", admins1.getUsername());
            map.put("token", token);

            return ResponseEntity.ok(map);
        } catch (
                Exception e) {
            return new ResponseEntity(new Result(false, e.getMessage()), BAD_REQUEST);
        }
    }

    @PutMapping("/reset")
    public ResponseEntity editPassword(@RequestBody PasswordRequest adminRequest, HttpServletRequest request) {
        try {
            Admins user = adminsRepository.findByUsername(jwtTokenProvider.getUser(jwtTokenProvider.resolveToken(request)));

            String pass = passwordEncoder.encode(adminRequest.getOld_password());
//            user.getPassword();
            if (passwordEncoder.matches(adminRequest.getOld_password(), user.getPassword())) {
                Admins admins1 = adminService.editPassword(user.getId(), adminRequest);
            } else {
                return new ResponseEntity(new Result(false, "Password xato"), BAD_REQUEST);
            }
            Map<Object, Object> map = new HashMap<>();
            map.put("succes", true);
            map.put("username", user.getUsername());
            return ResponseEntity.ok(map);
        } catch (
                Exception e) {
            return new ResponseEntity(new Result(false, e.getMessage()), BAD_REQUEST);
        }
    }

    @GetMapping("/categories")
    public ResponseEntity getCategoriesByPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size, HttpServletRequest httpServletRequest) {
        return ResponseEntity.ok(categoryService.getPages(page, size));
    }

    @GetMapping("/categories/all")
    public ResponseEntity getCategories() {
        return ResponseEntity.ok(categoryService.getAll());
    }

    @PostMapping("/categori/add")
    public ResponseEntity create(@RequestBody Category category) {
        if (categoryService.create(category)) {
            return ResponseEntity.ok(new Result(true, "saqlandi"));
        }
        return new ResponseEntity(new Result(false, "saqlanmadi"), HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/categori/{id}")
    public ResponseEntity editCategory(@PathVariable String id, @RequestBody Category category) {
        if (categoryService.edit(id, category)) {
            return ResponseEntity.ok(new Result(true, "saqlandi"));
        }
        return new ResponseEntity(new Result(false, "saqlanmadi"), HttpStatus.BAD_REQUEST);

    }

    @DeleteMapping("/categori/{id}")
    public ResponseEntity delete(@PathVariable String id, @RequestParam String category) {
        if (categoryService.delete(id, category)) {
            return ResponseEntity.ok(new Result(true, "o'chirildi"));
        }
        return new ResponseEntity(new Result(false, "o'chirilmadi"), HttpStatus.BAD_REQUEST);

    }

    @GetMapping("/news/all")
    public ResponseEntity getNews() {
        return ResponseEntity.ok(new ResultSucces(true, newsService.getAll()));
    }

    @GetMapping("/news/rel")
    public ResponseEntity getNewsByRelese(@RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(new ResultSucces(true, newsService.getPagesAll(page, size)));
    }

    @GetMapping("/news/likes")
    public ResponseEntity getNewsMostPopular() {
        return ResponseEntity.ok(new ResultSucces(true,
                newsService.getAllByPopularLikes()
        ));
    }

    @GetMapping("/news/views")
    public ResponseEntity getNewsMostViews() {
        return ResponseEntity.ok(new ResultSucces(true,
                newsService.getAllPostsByPopular()
        ));
    }

    @GetMapping("/news/{id}")
    public ResponseEntity getNewsbyId(@PathVariable String id) {
        NewsResponse news = newsService.getOne(id);
        if (news == null) {
            return ResponseEntity.ok(new Result(false, "post topilmadi"));
        }
        return ResponseEntity.ok(new ResultSucces(true, news));
    }

    @GetMapping("/{categoryid}/news")
    public ResponseEntity getNewsRelease(
            @PathVariable String categoryid,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(new ResultSucces(true, newsService.getPages(categoryid, page, size)));
    }

    @PostMapping("/news/upload")
    public ResponseEntity createFile(@RequestParam("file") MultipartFile multipartFile) {
        System.out.println(multipartFile);
        String hashId = attachmentService.save(multipartFile);
        if (hashId != null) {
            return ResponseEntity.ok(new ResultSucces(true, hashId));
        }
        return new ResponseEntity(new Result(false, "saqlanmadi"), BAD_REQUEST);
    }

    @PostMapping("/news/add")
    public ResponseEntity createNews(NewsRequest newsRequest) {
        System.out.println(newsRequest);
        if (newsRequest.getHash_id() != null) {
            if (newsService.create(newsRequest.getHash_id(), newsRequest)) {
                return ResponseEntity.ok(new Result(true, "saqlandi"));
            }
        }
        return new ResponseEntity(new Result(false, "saqlanmadi"), HttpStatus.BAD_REQUEST);
    }


    @PutMapping("/news/{id}")
    public ResponseEntity editNews(@PathVariable String id, NewsRequest newsRequest) {
        if (newsService.edit(id, newsRequest)) {
            return ResponseEntity.ok(new Result(true, "o'zgartirildi"));
        }
        return new ResponseEntity(new Result(false, "o'zgartirilmadi"), HttpStatus.BAD_REQUEST);
    }


    @PutMapping("/news/views/{id}")
    public ResponseEntity editViews(@PathVariable String id) {
        if (newsService.editViews(id)) {
            return ResponseEntity.ok(new Result(true, "o'zgartirildi"));
        }
        return new ResponseEntity(new Result(false, "o'zgartirilmadi"), HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/news/dislike/{id}")
    public ResponseEntity editDecLikes(@PathVariable String id) {
        if (newsService.decrementLikes(id)) {
            return ResponseEntity.ok(new Result(true, "o'zgartirildi"));
        }
        return new ResponseEntity(new Result(false, "o'zgartirilmadi"), HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/news/like/{id}")
    public ResponseEntity editIncLikes(@PathVariable String id) {
        if (newsService.incrementLikes(id)) {
            return ResponseEntity.ok(new Result(true, "o'zgartirildi"));
        }
        return new ResponseEntity(new Result(false, "o'zgartirilmadi"), HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/news/{id}")
    public ResponseEntity deleteNews(@PathVariable String id) {
        if (newsService.delete(id)) {
            return ResponseEntity.ok(new Result(true, "o'chirildi"));
        }
        return new ResponseEntity(new Result(false, "o'chirilmadi"), HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/{categoryid}/shortnews")
    public ResponseEntity getShortNewsRelease(
            @PathVariable String categoryid,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(new ResultSucces(true, shortNewsServise.getPages(categoryid, page, size)));
    }

    @GetMapping("/shortnews")
    public ResponseEntity getShortNews() {
        return new ResponseEntity(new ResultSucces(true, shortNewsServise.getAll()), HttpStatus.OK);
    }

    @PostMapping("/shortnews/add")
    public ResponseEntity createShortNews(@RequestBody ShortNewsRequest shortNewsRequest) {
        if (shortNewsServise.create(shortNewsRequest)) {
            return ResponseEntity.ok(new Result(true, "saqlandi"));
        }
        return new ResponseEntity(new Result(false, "saqlanmadi"), HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/shortnews/{id}")
    public ResponseEntity editShortNews(@PathVariable String id, @RequestBody ShortNewsRequest shortNewsRequest) {
        if (shortNewsServise.edit(id, shortNewsRequest)) {
            return ResponseEntity.ok(new Result(true, "o'zgartirildi"));
        }
        return new ResponseEntity(new Result(true, "o'zgartirilmadi"), HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/shortnews/{id}")
    public ResponseEntity deleteShortNews(@PathVariable String id) {
        return ResponseEntity.ok(shortNewsServise.delete(id));
    }

    @GetMapping("/summa")
    public ResponseEntity getSummaAllNewsLikes() {
        Map<String, Object> getSumma = new HashMap<>();
        getSumma.put("likes", newsRepository.getSumma());
        getSumma.put("views", newsRepository.getSummaViews());
        getSumma.put("comments", newsRepository.getSummaComments());
        return ResponseEntity.ok(new ResultSucces(true, getSumma));
    }

    @PutMapping("/social/edit")
    public ResponseEntity setEditSocial(@RequestBody Social social) {
        if (socialService.edit(social)) {
            return ResponseEntity.ok(new Result(true, "o'zgartirildi"));
        }
        return new ResponseEntity(new Result(true, "o'zgartirilmadi"), HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/social/one")
    public ResponseEntity getSocials() {
        Social social = socialService.getOne();
        return ResponseEntity.ok(new ResultSucces(true, social));
    }

}
