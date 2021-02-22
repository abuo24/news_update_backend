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
    @Column(nullable = false)
    private String Content;
    @Column(nullable = false)
    private String title;

    @OneToOne
    private Attachment headAttachment;

    private String youtube;

    private Long likesCount;

    private Long viewsCount;

    @ManyToOne(fetch = FetchType.LAZY)
    private Category category;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private Date createAt;
}