package com.news.update.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table
public class News implements Serializable {
    @Id
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @GeneratedValue(generator = "uuid")
    private String id;
    @Column(nullable = false,length = 10000)

    private String contentUz;
    @Column(nullable = false,length = 10000)
    private String contentRu;

    @Column(nullable = false)
    private String titleUz;

    @Column(nullable = false)
    private String titleRu;

    @OneToOne
    private Attachment headAttachment;

    private Long likesCount;

    private Long viewsCount;

    @ManyToOne(fetch = FetchType.LAZY)
    private Category category;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<Tags> tags;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd HH:mm:ss",timezone = "Asia/Tashkent")
    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private Date createAt;
}