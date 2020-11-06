package com.example.okeyifee.service;

import com.example.okeyifee.dto.NoteDto;
import com.example.okeyifee.models.Note;

import java.util.List;
import java.time.LocalDateTime;

public interface NoteService{

    List<Note> findAllByTitle(String title);
    List<Note>  findAllByCreatedAt(LocalDateTime date);
    List<Note> findAllByContentContaining(String matcher);
    List<Note>  findAllByTitleContains(String matcher);
    Note findNoteByCreatedAt(LocalDateTime date);
    Note findNoteByTitleContains(String matcher);
    boolean save (Note note);
    boolean deleteNoteById(Long id);
    void editNoteById(Long id);
    Note findNoteById(Long id);
    List<NoteDto> findNoteUsingTitle(String title);
}
