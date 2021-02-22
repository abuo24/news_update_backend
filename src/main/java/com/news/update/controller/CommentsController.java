package com.news.update.controller;

import com.news.update.model.Result;
import com.news.update.model.ResultSucces;
import com.news.update.payload.CommentsRequest;
import com.news.update.service.CommentsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Controller
@RequestMapping("/api/comments")
public class CommentsController {

    @Autowired
    private CommentsServiceImpl commentsServiceImpl;

    @PostMapping("/{newsid}")
    public ResponseEntity createComment(@PathVariable String newsid, @RequestBody CommentsRequest commentsRequest){

        return commentsServiceImpl.create(newsid,commentsRequest)?ResponseEntity.ok(new Result(true, "saqlandi"))
                : (new ResponseEntity(new Result(false, "saqlanmadi"), BAD_REQUEST));
    }

    @DeleteMapping("/{commentid}")
    public ResponseEntity deleteComment(@PathVariable String commentid){

        return commentsServiceImpl.delete(commentid)?ResponseEntity.ok(new Result(true, "o'chirildi"))
                : (new ResponseEntity(new Result(false, "o'chirilmadi"), BAD_REQUEST));
    }

    @GetMapping("/{newsid}/all")
    public ResponseEntity getComments(@PathVariable String newsid){
        return ResponseEntity.ok(new ResultSucces(true,commentsServiceImpl.getAll(newsid)));
    }
}
