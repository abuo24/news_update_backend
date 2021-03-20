package com.news.update.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
public class Tags implements Serializable {

    @Id
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @GeneratedValue(generator = "uuid")
    private String id;

    @Column(nullable = false)
    private String tagUz;

    @Column(nullable = false)
    private String tagRu;

}
