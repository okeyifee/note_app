package com.example.okeyifee.service.impl;

import com.example.okeyifee.dto.NoteDTO;
import com.example.okeyifee.models.Note;
import com.example.okeyifee.payload.ApiResponse;
import com.example.okeyifee.repository.NoteRepository;
import com.example.okeyifee.service.NoteService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static com.example.okeyifee.utils.BuildResponse.buildResponse;

@Service
public class NoteServiceImpl implements NoteService{

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    NoteRepository noteRepository;
    ModelMapper mapper;
    Note new_Note = new Note();
    NoteDTO newNote = new NoteDTO();
    List<NoteDTO> retrievedNote = new ArrayList<>();
    ApiResponse<List<NoteDTO>> response = new ApiResponse<>();

    @Autowired
    public NoteServiceImpl(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    @Override
    public ResponseEntity<ApiResponse> findAllNotes() {

        List<Note> answer = noteRepository.findAllNotes();
        if (answer.size() > 0){
            answer.forEach((Note a) -> {
                newNote.setTitle(a.getTitle());
                newNote.setCreatedAt(String.format(String.valueOf(a.getCreatedAt()), formatter));
                newNote.setContent(a.getContent());
                newNote.setCategory(a.getCategory());
                newNote.setSubtitle(a.getSubtitle());
                retrievedNote.add(newNote);
            });
            response.setData(retrievedNote);
            response.setMessage("Note successfully retrieved");
            response.setStatus(HttpStatus.OK);
        } else {
            response.setMessage("No Note Found For User");
            response.setStatus(HttpStatus.NOT_FOUND);
        }
        return buildResponse(response);
    }

    @Override
    public ResponseEntity<ApiResponse> findAllByCreatedAt(NoteDTO noteDTO) {
        Timestamp createdAt = Timestamp.valueOf(noteDTO.getCreatedAt());
        List<Note> answer = noteRepository.findAllByCreatedAt(createdAt);
        if (answer.size() > 0){
            answer.forEach((Note a) -> {
                newNote.setTitle(a.getTitle());
                newNote.setCreatedAt(String.format(String.valueOf(a.getCreatedAt()), formatter));
                newNote.setContent(a.getContent());
                newNote.setCategory(a.getCategory());
                newNote.setSubtitle(a.getSubtitle());
                retrievedNote.add(newNote);
            });
            response.setData(retrievedNote);
            response.setMessage("Note successfully retrieved");
            response.setStatus(HttpStatus.OK);
        } else {
            response.setMessage("No Note with Creation Date ' " + createdAt + "' Found For User");
            response.setStatus(HttpStatus.NOT_FOUND);
        }
        return buildResponse(response);
    }

    @Override
    public ResponseEntity<ApiResponse> findAllByContentContaining(NoteDTO noteDTO) {
        String content = noteDTO.getContent();
        List<Note> answer = noteRepository.findAllNotes();

        if (answer.size() > 0){
            answer.forEach((Note a) -> {
                if (a.getContent().contains(content)){
                    newNote.setTitle(a.getTitle());
                    newNote.setCreatedAt(String.format(String.valueOf(a.getCreatedAt()), formatter));
                    newNote.setContent(a.getContent());
                    newNote.setCategory(a.getCategory());
                    newNote.setSubtitle(a.getSubtitle());
                    retrievedNote.add(newNote);
                }

            });
            response.setData(retrievedNote);
            response.setMessage("Note successfully retrieved");
            response.setStatus(HttpStatus.OK);
        } else {
            response.setMessage("No Note with content containing ' " + content + "' Found For User");
            response.setStatus(HttpStatus.NOT_FOUND);
        }
        return buildResponse(response);
    }

    @Override
    public ResponseEntity<ApiResponse> findAllByTitleContains(NoteDTO noteDTO) {
        String title = noteDTO.getTitle();
        List<Note> answer = noteRepository.findAllNotes();

        if (answer.size() > 0){
            answer.forEach((Note a) -> {
                if (a.getTitle().contains(title)){
                    NoteDTO n = new NoteDTO();
                    n.setTitle(a.getTitle());
                    n.setCreatedAt(String.format(String.valueOf(a.getCreatedAt()), formatter));
                    n.setContent(a.getContent());
                    n.setCategory(a.getCategory());
                    n.setSubtitle(a.getSubtitle());
                    retrievedNote.add(n);
                }

            });
            response.setData(retrievedNote);
            response.setMessage("Note successfully retrieved");
            response.setStatus(HttpStatus.OK);
        } else {
            response.setMessage("No Note with title containing ' " + title + "' Found For User");
            response.setStatus(HttpStatus.NOT_FOUND);
        }
        return buildResponse(response);
    }

    @Override
    public ResponseEntity<ApiResponse> save(NoteDTO noteDTO) {

        new_Note.setTitle(noteDTO.getTitle());
        new_Note.setContent(noteDTO.getContent());
        new_Note.setCategory(noteDTO.getCategory());
        new_Note.setDeleted(false);
        new_Note.setSubtitle(noteDTO.getSubtitle());
        new_Note.setUpdatedAt(null);
        noteRepository.save(new_Note);
        ApiResponse<String> response = new ApiResponse<>(HttpStatus.CREATED);
        response.setMessage("Note Registration Successful");
        return buildResponse(response);
    }

    @Override
    public ResponseEntity<ApiResponse> deleteNoteById(NoteDTO noteDTO) {
        Long id = noteDTO.getId();
        Note answer = noteRepository.findNoteById(id);

        new_Note.setTitle(answer.getTitle());
        new_Note.setId(answer.getId());
        new_Note.setContent(answer.getContent());
        new_Note.setCategory(answer.getCategory());
        new_Note.setDeleted(true);
        new_Note.setSubtitle(answer.getSubtitle());
        noteRepository.save(new_Note);
        response.setMessage("Note Deleted");
        return buildResponse(response);
    }

    @Override
    public ResponseEntity<ApiResponse> editNoteById(NoteDTO noteDTO) {
        Long id = noteDTO.getId();
        Note answer = noteRepository.findNoteById(id);

        new_Note.setId(answer.getId());
        new_Note.setTitle(answer.getTitle());
        new_Note.setContent(answer.getContent());
        new_Note.setCategory(answer.getCategory());
        new_Note.setDeleted(false);
        new_Note.setSubtitle(answer.getSubtitle());
        new_Note.setCreatedAt(answer.getCreatedAt());
        new_Note.setUpdatedAt(Timestamp.valueOf((LocalDateTime.now())));
        noteRepository.save(new_Note);
        response.setMessage("Note Successfully Edited");
        return buildResponse(response);
    }

    @Override
    public ResponseEntity<ApiResponse> findNoteById(NoteDTO noteDTO) {
        String title = noteDTO.getTitle();
        List<Note> answer = noteRepository.findNoteByTitle(title);

        if (answer.size() > 0){
            answer.forEach((Note a) -> {
                NoteDTO n = new NoteDTO();
                n.setTitle(a.getTitle());
                n.setCreatedAt(String.format(String.valueOf(a.getCreatedAt()), formatter));
                n.setContent(a.getContent());
                n.setSubtitle(a.getSubtitle());
                n.setCategory(a.getCategory());
                retrievedNote.add(n);
            });
            response.setData(retrievedNote);
            response.setMessage("Note successfully retrieved");
            response.setStatus(HttpStatus.OK);
        } else {
            response.setMessage("Not Found");
            response.setStatus(HttpStatus.NOT_FOUND);
        }
        return buildResponse(response);
    }

    @Override
    public ResponseEntity<ApiResponse> findNoteUsingTitle(NoteDTO noteDTO) {
        String title = noteDTO.getTitle();
        List<Note> answer = noteRepository.findNoteByTitle(title);

        if (answer.size() > 0){
            answer.forEach((Note a) -> {
                newNote.setTitle(a.getTitle());
                newNote.setCreatedAt(String.format(String.valueOf(a.getCreatedAt()), formatter));
                newNote.setContent(a.getContent());
                newNote.setCategory(a.getCategory());
                newNote.setSubtitle(a.getSubtitle());
                retrievedNote.add(newNote);
            });
            response.setData(retrievedNote);
            response.setMessage("Note successfully retrieved");
            response.setStatus(HttpStatus.OK);
        } else {
            response.setMessage("No Note with Title' " + title + "' Found For User");
            response.setStatus(HttpStatus.NOT_FOUND);
        }
        return buildResponse(response);
    }

    @Override
    public ResponseEntity<ApiResponse> deleteNoteByTitle(NoteDTO noteDTO) {
        String title = noteDTO.getTitle();
        List<Note> answer = noteRepository.findNoteByTitle(title);

        if (answer.size() > 0){
            answer.forEach((Note a) -> {
                new_Note.setTitle(a.getTitle());
                new_Note.setCreatedAt(a.getCreatedAt());
                new_Note.setContent(a.getContent());
                new_Note.setCategory(a.getCategory());
                new_Note.setSubtitle(a.getSubtitle());
                new_Note.setId(a.getId());
                new_Note.setDeleted(true);
                noteRepository.save(new_Note);
            });
            response.setMessage("Note Deleted");
            response.setStatus(HttpStatus.OK);
        } else {
            response.setMessage("No Note with Title' " + title + "' Found For User");
            response.setStatus(HttpStatus.NOT_FOUND);
        }
        return buildResponse(response);
    }
}

