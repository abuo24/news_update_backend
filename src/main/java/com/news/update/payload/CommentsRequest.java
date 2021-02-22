package com.news.update.payload;

import com.news.update.entity.Comments;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentsRequest {

    private String message;

    private String author;

    private String authorMail;

    private String comments_id;
}
