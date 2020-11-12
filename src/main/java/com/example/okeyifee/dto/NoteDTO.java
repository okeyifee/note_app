package com.example.okeyifee.dto;

import lombok.*;

@Data
@Getter
@Setter
public class NoteDTO{

    private Long id;

    private String title;

    private String content;

    public  String category;

    private String createdAt;

    private boolean deleted;

    public  String subtitle;
}
