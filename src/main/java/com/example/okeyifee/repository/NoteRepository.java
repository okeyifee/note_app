package com.example.okeyifee.repository;


import com.example.okeyifee.models.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.Date;

@RequestMapping
public interface NoteRepository extends JpaRepository<Note, Long>{

    Note findAllByTitle(String title);
    Note findAllByCreatedAt(Date date);
    Note findAllByContentContaining(String matcher);
    Note findAllByTitleContains(String matcher);
    Note findNoteByCreatedAt(Date date);
    Note findANoteByTitleContains(String matcher);
    Note findNoteByTitle(String title);

}
