package com.news.update.controller;

import com.news.update.model.Result;
import com.news.update.model.ResultSucces;
import com.news.update.payload.ShortNewsRequest;
import com.news.update.payload.VideoRequest;
import com.news.update.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@CrossOrigin
@RequestMapping("/api/videonews")
public class VideoController {

    @Autowired
    private VideoService videoService;

    @GetMapping("")
    public ResponseEntity getShortNewsRelease(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(new ResultSucces(true, videoService.getPages(page, size)));
    }

    @GetMapping("/all")
    public ResponseEntity getShortNews(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "8") int size) {
        return new ResponseEntity(new ResultSucces(true, videoService.getPages(page,size)), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity createShortNews(@RequestBody VideoRequest videoRequest) {
        if (videoService.create(videoRequest)) {
            return ResponseEntity.ok(new Result(true, "saqlandi"));
        }
        return new ResponseEntity(new Result(false, "saqlanmadi"), HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/{id}")
    public ResponseEntity editShortNews(@PathVariable String id, @RequestBody VideoRequest videoRequest) {
        if (videoService.edit(id, videoRequest)) {
            return ResponseEntity.ok(new Result(true, "o'zgartirildi"));
        }
        return new ResponseEntity(new Result(true, "o'zgartirilmadi"), HttpStatus.BAD_REQUEST);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity deleteNews(@PathVariable String id) {
        if (videoService.delete(id)) {
            return ResponseEntity.ok(new Result(true, "o'chirildi"));
        }
        return new ResponseEntity(new Result(false, "o'chirilmadi"), HttpStatus.BAD_REQUEST);
    }

}