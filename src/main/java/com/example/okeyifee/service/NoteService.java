package com.example.okeyifee.service;

import com.example.okeyifee.models.Note;

import java.sql.Date;

public interface NoteService{

    Note findAllByTitle(String title);
    Note findAllByCreatedAt(Date date);
    Note findAllByContentContaining(String matcher);
    Note findAllByTitleContains(String matcher);
    Note findNoteByCreatedAt(Date date);
    Note findANoteByTitleContains(String matcher);
    Note findNoteByTitle(String title);
    void saveNote (Note note);
    boolean deleteNoteById(Long id);
    void editNoteById(Long id);
}
