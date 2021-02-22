package com.news.update.controller;


import com.news.update.entity.Admins;
import com.news.update.entity.Attachment;
import com.news.update.entity.Category;
import com.news.update.model.Result;
import com.news.update.model.ResultSucces;
import com.news.update.payload.AdminRequest;
import com.news.update.payload.NewsRequest;
import com.news.update.payload.ShortNewsRequest;
import com.news.update.repository.AdminsRepository;
import com.news.update.security.JwtTokenProvider;
import com.news.update.service.*;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/api/admin")
public class AdminsController {

    @Autowired
    private AdminsServiceImpl adminService;

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


   @PutMapping("/edit")
    public ResponseEntity editAdmin(@RequestBody AdminRequest adminRequest, HttpServletRequest request) {
        Admins user = adminsRepository.findByUsername(jwtTokenProvider.getUser(jwtTokenProvider.resolveToken(request))).get();

        if (adminService.edit(user.getId(), adminRequest)) {
            return ResponseEntity.ok(new Result(true, "o'zgartirildi"));
        }
        return new ResponseEntity(new Result(false, "o'zgartirilmadi"), HttpStatus.BAD_REQUEST);
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
    public ResponseEntity delete(@PathVariable String id) {
        if (categoryService.delete(id)) {
            return ResponseEntity.ok(new Result(true, "o'chirildi"));
        }
        return new ResponseEntity(new Result(false, "o'chirilmadi"), HttpStatus.BAD_REQUEST);

    }


    @GetMapping("/news/all")
    public ResponseEntity getNews() {
        return ResponseEntity.ok(new ResultSucces(true, newsService.getAll()));
    }

    @GetMapping("/{categoryid}/news")
    public ResponseEntity getNewsRelease(
            @PathVariable String categoryid,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        return ResponseEntity.ok(new ResultSucces(true, newsService.getPages(categoryid, page, size)));
    }

    @PostMapping("/news/add")
    public ResponseEntity createNews(NewsRequest newsRequest) {
        String hashId = attachmentService.save(newsRequest.getFile());
        Attachment attachment = attachmentService.findByHashId(hashId);
        if (newsService.create(hashId, newsRequest)) {
            return ResponseEntity.ok(new Result(true, "saqlandi"));
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
            @RequestParam(defaultValue = "2") int size) {
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

}
