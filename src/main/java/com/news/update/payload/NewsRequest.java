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

    @NonNull
    private String hash_id;

    @NonNull
    private  String contentUz;

    @NonNull
    private  String contentRu;


    @NonNull
    private String titleUz;


    @NonNull
    private String titleRu;


    @NonNull
    private List<String> tags;


    @NonNull
    private String category_id;

}