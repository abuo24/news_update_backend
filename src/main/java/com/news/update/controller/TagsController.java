package com.news.update.controller;

import com.news.update.entity.Messages;
import com.news.update.entity.Tags;
import com.news.update.model.Result;
import com.news.update.model.ResultSucces;
import com.news.update.service.MessageService;
import com.news.update.service.TagsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@CrossOrigin
@Controller
@RequestMapping("/api/tags")
public class TagsController {

    @Autowired
    private TagsService tagsService;

    @PostMapping("/add")
    public ResponseEntity create(@RequestBody Tags tags) {
        if (tagsService.create(tags)) {
            return ResponseEntity.ok(new Result(true, "saqlandi"));
        }
        return new ResponseEntity(new Result(false, "saqlanmadi"), HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{tagsid}")
    public ResponseEntity deleteComment(@PathVariable String tagsid){

        return tagsService.delete(tagsid)?ResponseEntity.ok(new Result(true, "o'chirildi"))
                : (new ResponseEntity(new Result(false, "o'chirilmadi"), BAD_REQUEST));
    }


    @GetMapping("/all")
    public ResponseEntity getAll() {

        if (!tagsService.getAll().isEmpty()) {
            return ResponseEntity.ok(new ResultSucces(true, tagsService.getAll()));
        }
        return new ResponseEntity(new Result(false, "saqlanmadi"), HttpStatus.BAD_REQUEST);
    }
}
