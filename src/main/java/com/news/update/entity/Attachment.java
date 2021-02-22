package com.news.update.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Attachment implements Serializable {

    @Id
    @GenericGenerator(name="uuid", strategy = "uuid2")
    @GeneratedValue(generator = "uuid")
    private String id;

    private String name;

    private String extension;

    private Long fileSize;

    private String hashId;

    private String contentType;

    private String uploadPath;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private Date createAt;

}
