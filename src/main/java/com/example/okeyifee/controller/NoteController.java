package com.example.okeyifee.controller;

import com.example.okeyifee.dto.NoteDTO;
import com.example.okeyifee.payload.ApiResponse;
import com.example.okeyifee.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/notes")
public class NoteController{

    private NoteService noteService;

    @Autowired
    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createNote(@Valid @RequestBody NoteDTO noteDto) {
        return noteService.save(noteDto);
    }

    @GetMapping("/retrieve/title")
    public ResponseEntity<ApiResponse> retrieveNoteByTitle(@Valid @RequestBody NoteDTO noteDto) {
        return noteService.findNoteUsingTitle(noteDto);
    }

    @GetMapping("/retrieve_All")
    public ResponseEntity<ApiResponse> retrieveAllNotes() { /////////////
        return noteService.findAllNotes();
    }

    @GetMapping("/retrieve/id")
    public ResponseEntity<ApiResponse> retrieveNoteById(@Valid @RequestBody NoteDTO noteDto) {
        return noteService.findNoteById(noteDto);
    }

    @GetMapping("/edit/{id}")
    public ResponseEntity<ApiResponse> editNoteById(@Valid @RequestBody NoteDTO noteDto) {
        return noteService.editNoteById(noteDto);
    }

    @GetMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteNoteById(@Valid @RequestBody NoteDTO noteDto) {
        return noteService.deleteNoteById(noteDto);
    }

    @GetMapping("/delete/title")
    public ResponseEntity<ApiResponse> deleteNoteByTitle(@Valid @RequestBody NoteDTO noteDto) {
        return noteService.deleteNoteByTitle(noteDto);
    }

    @GetMapping("/retrieve/creation-date")
    public ResponseEntity<ApiResponse> findNoteByCreatedAt(@Valid @RequestBody NoteDTO noteDto) {
        return noteService.findAllByCreatedAt(noteDto);
    }

    @GetMapping("/retrieve/content")
    public ResponseEntity<ApiResponse> findNoteByContentContaining(@Valid @RequestBody NoteDTO noteDto) {
        return noteService.findAllByContentContaining(noteDto);
    }

    @GetMapping("retrieve/title-contains")
    public ResponseEntity<ApiResponse> findNoteByTitleContains(@Valid @RequestBody NoteDTO noteDto) {
        return noteService.findAllByTitleContains(noteDto);
    }
}





