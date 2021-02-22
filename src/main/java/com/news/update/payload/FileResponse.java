package com.news.update.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileResponse {
  private String name;
  private String url;
  private String type;
  private long size;
}
