package com.example.okeyifee.service.impl;

import com.example.okeyifee.dto.NoteDTO;
import com.example.okeyifee.models.Note;
import com.example.okeyifee.payload.ApiResponse;
import com.example.okeyifee.repository.NoteRepository;
import com.example.okeyifee.service.NoteService;
import com.example.okeyifee.utils.Serializer;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
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

    @Autowired
    public NoteServiceImpl(NoteRepository noteRepository, ModelMapper mapper) {
        this.noteRepository = noteRepository;
        this.mapper = mapper;
    }

    @Override
    public ResponseEntity<ApiResponse> findAllNotes() {

        List<NoteDTO> retrievedNote = new ArrayList<>();
        ApiResponse<List<NoteDTO>> response = new ApiResponse<>();
        List<Note> answer = noteRepository.findAllNotes();
        if (answer.size() > 0){
            answer.forEach((Note a) -> {
                NoteDTO newNote = mapper.map(a, NoteDTO.class);
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
    public ResponseEntity<ApiResponse> serial(NoteDTO noteDTO)  {

        List<Note> s = new ArrayList<>();
        Note answer = noteRepository.findNoteById(noteDTO.getId());
        s.add(answer);
//        List<Note> answer2 = noteRepository.findNoteByTitle(noteDTO.getTitle());
//        List<Note> answer3 = noteRepository.findAllNotes();
//        System.out.println("retrieved1: " + s);
//        System.out.println("retrieved2: " + answer2);
//        System.out.println("retrieved3: " + answer3);
        ApiResponse<List<Note>> response = new ApiResponse<>();

        try {
            Serializer.serialize(answer, "my_notes_id.data");
//            Serializer.serialize(answer2, "my_notes_title.data");
//            Serializer.serialize(answer3, "my_notes_all.data");
//            answer.add(answer1);
//            answer.add(answer2);
//            answer.add(answer3);
//            response.setData(s);
            response.setMessage("success");
            response.setStatus(HttpStatus.OK);

        } catch (IOException e) {
            e.printStackTrace();
            response.setStatus(HttpStatus.NOT_FOUND);
            response.setMessage("Not found");

        }
        return buildResponse(response);
    }

    @Override
    public ResponseEntity<ApiResponse> findAllByCreatedAt(NoteDTO noteDTO) {
        Timestamp createdAt = Timestamp.valueOf(noteDTO.getCreatedAt());
        List<NoteDTO> retrievedNote = new ArrayList<>();
        ApiResponse<List<NoteDTO>> response = new ApiResponse<>();
        List<Note> answer = noteRepository.findAllNotes();
        if (answer.size() > 0){
            answer.forEach((Note a) -> {
                String date = a.getCreatedAt().toString();
                System.out.println(date);
                System.out.println(createdAt.toString().substring(0,10));
                if (date.contains(createdAt.toString().substring(0,10))){
                    NoteDTO newNote = mapper.map(a, NoteDTO.class);
                    retrievedNote.add(newNote);
                } else {

                }
            });
            if (retrievedNote.size() > 0){
                response.setData(retrievedNote);
                response.setMessage("Note successfully retrieved");
                response.setStatus(HttpStatus.OK);
            } else {
                response.setMessage("No Note with Creation Date ' " + createdAt + "' Found For User");
                response.setStatus(HttpStatus.NOT_FOUND);
            }
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
        List<NoteDTO> retrievedNote = new ArrayList<>();
        ApiResponse<List<NoteDTO>> response = new ApiResponse<>();

        System.out.println(answer);

        if (answer.size() > 0) {
            answer.forEach((Note a) -> {
                if (a.getContent().contains(content)){
                    NoteDTO newNote = mapper.map(a, NoteDTO.class);
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
        List<NoteDTO> retrievedNote = new ArrayList<>();
        ApiResponse<List<NoteDTO>> response = new ApiResponse<>();
        List<Note> answer = noteRepository.findAllNotes();

        if (answer.size() > 0){
            answer.forEach((Note a) -> {
                if (a.getTitle().contains(title)){
                    NoteDTO newNote = mapper.map(a, NoteDTO.class);
                    retrievedNote.add(newNote);
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
        Note new_Note = mapper.map(noteDTO, Note.class);
        new_Note.setDeleted(false);
        noteRepository.save(new_Note);
        ApiResponse<String> response = new ApiResponse<>(HttpStatus.CREATED);
        response.setMessage("Note Registration Successful");
        return buildResponse(response);
    }

    @Override
    public ResponseEntity<ApiResponse> deleteNoteById(Long id) {
        ApiResponse<List<NoteDTO>> response = new ApiResponse<>();
        Note answer = noteRepository.findNoteById(id);

        if (answer != null) {
            Note new_Note = mapper.map(answer, Note.class);
            new_Note.setDeleted(true);
            new_Note.setDeletedAt(Timestamp.valueOf(LocalDateTime.now()));
            noteRepository.save(new_Note);
            response.setMessage("Note Deleted");
            response.setStatus(HttpStatus.OK);

        } else {
            response.setMessage("NO NOTE FOUND");
            response.setStatus(HttpStatus.NOT_FOUND);
        }
        return buildResponse(response);
    }

    @Override
    public ResponseEntity<ApiResponse> editNoteById(Long id, NoteDTO noteDTO) {
        Note new_Note = new Note();
        System.out.println("NoteDTO: " + noteDTO);
        ApiResponse<List<NoteDTO>> response = new ApiResponse<>();
        Note answer = noteRepository.findNoteById(id);
        new_Note.setTitle(noteDTO.getTitle());
        new_Note.setContent(noteDTO.getContent());
        new_Note.setCategory(noteDTO.getCategory());
        new_Note.setDeleted(false);
        new_Note.setSubtitle(noteDTO.getSubtitle());
        new_Note.setId(answer.getId());
        new_Note.setCreatedAt(answer.getCreatedAt());
        noteRepository.save(new_Note);
        response.setMessage("Note Successfully Edited");
        response.setStatus(HttpStatus.OK);
        return buildResponse(response);
    }

    @Override
    public ResponseEntity<ApiResponse> findNoteById(Long id) {
        List<NoteDTO> retrievedNote = new ArrayList<>();
        ApiResponse<List<NoteDTO>> response = new ApiResponse<>();
        Note answer = noteRepository.findById(id).orElse(null);

        if (answer != null){
            NoteDTO newNote = mapper.map(answer, NoteDTO.class);
            retrievedNote.add(newNote);
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
        NoteDTO newNote = new NoteDTO();
        List<NoteDTO> retrievedNote = new ArrayList<>();
        ApiResponse<List<NoteDTO>> response = new ApiResponse<>();

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
        Note new_Note = new Note();
        ApiResponse<List<NoteDTO>> response = new ApiResponse<>();
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
                new_Note.setDeletedAt(Timestamp.valueOf(LocalDateTime.now()));
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

