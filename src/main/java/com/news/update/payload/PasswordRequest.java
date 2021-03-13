package com.news.update.payload;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.news.update.entity.Role;
import com.sun.istack.NotNull;
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
public class PasswordRequest {

    @NotNull
    private String old_password;

    @NotNull
    private String new_password;

}
