package com.news.update.payload;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PostRequestLikesAndViews {

    private  Long likes;

    private Long views;

}



