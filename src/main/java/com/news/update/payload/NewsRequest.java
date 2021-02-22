package com.news.update.payload;
import com.news.update.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class NewsRequest {

    private MultipartFile file;

    private  String Content;

    private String title;

    private String youtube;

    private Long likesCount;

    private Long viewsCount;

    private String news_id;

    private String category_id;

}