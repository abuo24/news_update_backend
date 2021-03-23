package com.news.update.payload;
import com.news.update.entity.Category;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class NewsRequest {

    @NotNull
    private String hash_id;

    @NotNull
    private  String contentUz;

    @NotNull
    private  String contentRu;

    @NotNull
    private String titleUz;

    @NotNull
    private String titleRu;

    @NotNull
    private List<String> tags;

    @NotNull
    private String category_id;

}