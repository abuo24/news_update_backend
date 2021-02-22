package com.news.update.payload;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.news.update.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AdminRequest {

    private String fullname;

    private String old_password;

    private String new_password;

    private String username;

    private String social;

    private String phone;
}
