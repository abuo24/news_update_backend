package com.news.update.payload;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.news.update.entity.Attachment;
import com.news.update.entity.Category;
import com.news.update.entity.Comments;
import com.news.update.entity.Tags;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class NewsResponse {

    private String id;

    private  String contentUz;
    private  String contentRu;

    private String titleUz;
    private String titleRu;

    private Attachment headAttachment;


    private Long likesCount;

    private Long viewsCount;

    private Category category;

    private List<Tags> tags;

    private List<Comments> comments;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd HH:mm:ss",timezone = "Asia/Tashkent")
    private Date createAt;

}