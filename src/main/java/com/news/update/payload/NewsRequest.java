package com.news.update.payload;
import com.news.update.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class NewsRequest {

    private String hash_id;

    private  String contentUz;

    private  String contentRu;

    private String titleUz;

    private String titleRu;

    private List<String> tags;

    private String category_id;
}