package com.example.okeyifee.repository;

import com.example.okeyifee.models.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@RequestMapping
public interface NoteRepository extends JpaRepository<Note, Long>{

    Note save(Note note);

    @Query(value="SELECT * FROM notes u WHERE deleted = false AND created_at = ?1", nativeQuery = true)
    List<Note>  findAllByCreatedAt(Timestamp date);

    @Query(value="SELECT * FROM notes u WHERE deleted = false AND content = ?1", nativeQuery = true)
    List<Note>  findAllByContentContaining(String matcher);

    @Query(value="SELECT * FROM notes u WHERE deleted = false AND title = ?1", nativeQuery = true)
    List<Note>  findAllByTitleContains(String matcher);

    @Query(value="SELECT * FROM notes u WHERE deleted = false AND title = ?1", nativeQuery = true)
    Note findNoteByTitleContains(String matcher);

    @Query(value="SELECT * FROM notes u WHERE deleted = false AND id = ?1", nativeQuery = true)
    Note findNoteById(Long id);

    @Query(value="SELECT * FROM notes u WHERE deleted = false AND title = ?1", nativeQuery = true)
    List<Note> findNoteByTitle(String Title);

    @Query(value="SELECT * FROM notes u WHERE deleted = false", nativeQuery = true)
    List<Note> findAllNotes();
}
