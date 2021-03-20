package com.news.update.payload;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ShortNewsRequest {
    private String titleUz;
    private String titleRu;
    private String category_id;
}
