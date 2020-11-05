package com.example.okeyifee.dto;

import lombok.*;

import javax.validation.constraints.NotNull;

@Data
@Getter
@Setter
public class NoteDto{

    @NotNull
    private String title;

    @NotNull
    private String content;

    @NotNull
    private String createdAt;

}
