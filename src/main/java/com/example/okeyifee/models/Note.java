package com.example.okeyifee.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "notes")
public class Note extends BaseModel implements Serializable{

    static final long serialVersionUID = 1L;

    private String title;

    public  String subtitle;

    @NotBlank
    @Column(columnDefinition = "TEXT")
    private String content;

    @NotBlank
    public  String category;

    @Column(nullable = false)
    boolean deleted;

}
