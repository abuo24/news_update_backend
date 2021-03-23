package com.news.update.payload;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class VideoRequest {

    @NotNull
    private  String link;

    @NotNull
    private String titleUz;

    @NotNull
    private String titleRu;


}