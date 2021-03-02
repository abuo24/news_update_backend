package com.news.update.controller;

import com.news.update.entity.Messages;
import com.news.update.model.Result;
import com.news.update.model.ResultSucces;
import com.news.update.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@CrossOrigin
@Controller
@RequestMapping("/api/message")
public class MessagesController {

    @Autowired
    private MessageService messageService;

    @PostMapping("/add")
    public ResponseEntity create(@RequestBody Messages messages){

        messageService.create(messages);
        if (messageService.create(messages)) {
            return ResponseEntity.ok(new Result(true, "saqlandi"));
        }
        return new ResponseEntity(new Result(false, "saqlanmadi"), HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/all")
    public ResponseEntity getAll(){

        if (!messageService.getAll().isEmpty()) {
            return ResponseEntity.ok(new ResultSucces(true,messageService.getAll()));
        }
        return new ResponseEntity(new Result(false, "saqlanmadi"), HttpStatus.BAD_REQUEST);
    }
}
