package com.example.okeyifee.service.impl;

import com.example.okeyifee.models.Note;
import com.example.okeyifee.repository.NoteRepository;
import com.example.okeyifee.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;

@Service
public class NoteServiceImpl implements NoteService{

    NoteRepository noteRepository;

    @Autowired
    public NoteServiceImpl(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }


    @Override
    public Note findAllByTitle(String title) {
        return null;
    }

    @Override
    public Note findAllByCreatedAt(Date date) {
        return null;
    }

    @Override
    public Note findAllByContentContaining(String matcher) {
        return null;
    }

    @Override
    public Note findAllByTitleContains(String matcher) {
        return null;
    }

    @Override
    public Note findNoteByCreatedAt(Date date) {
        return null;
    }

    @Override
    public Note findANoteByTitleContains(String matcher) {
        return null;
    }

    @Override
    public Note findNoteByTitle(String title) {
        return null;
    }

    @Override
    public void saveNote(Note note) {

    }

    @Override
    public boolean deleteNoteById(Long id) {
        return false;
    }

    @Override
    public void editNoteById(Long id) {

    }
}
