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

@NoArgsConstructor
@AllArgsConstructor
@Data
@Table
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
public class Comments implements Serializable {

    @Id
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @GeneratedValue(generator = "uuid")
    private String id;

    @Column(nullable = false)
    private String message;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private String authorMail;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinTable(name = "reply_comment",
            joinColumns = @JoinColumn(name = "comment_id"),
            inverseJoinColumns = @JoinColumn(name = "reply_comment_id"))
    private Comments comments;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinTable(name = "news_comment",
            joinColumns = @JoinColumn(name = "comment_id"),
            inverseJoinColumns = @JoinColumn(name = "news_id"))
    private News news;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private Date createAt;
}
