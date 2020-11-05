package com.example.okeyifee.repository;


import com.example.okeyifee.models.Note;
import com.example.okeyifee.dto.NoteDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

@RequestMapping
public interface NoteRepository extends JpaRepository<Note, Long>{

    List<Note> findAllByTitle(String title);
    List<Note>  findAllByCreatedAt(LocalDateTime date);
    List<Note>  findAllByContentContaining(String matcher);
    List<Note>  findAllByTitleContains(String matcher);
    Note findNoteByCreatedAt(LocalDateTime date);
    Note findNoteByTitleContains(String matcher);
    List<Note> findNoteByTitle(String title);
    Note findNoteById(Long id);

}
