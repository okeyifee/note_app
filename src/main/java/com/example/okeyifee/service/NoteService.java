package com.example.okeyifee.service;

import com.example.okeyifee.dto.NoteDTO;
import com.example.okeyifee.payload.ApiResponse;
import org.springframework.http.ResponseEntity;

public interface NoteService{

    ResponseEntity<ApiResponse> findAllNotes();
    ResponseEntity<ApiResponse> findAllByCreatedAt(NoteDTO noteDTO);
    ResponseEntity<ApiResponse> findAllByContentContaining(NoteDTO noteDTO);
    ResponseEntity<ApiResponse> findAllByTitleContains(NoteDTO noteDTO);
    ResponseEntity<ApiResponse> save(NoteDTO noteDTO);
    ResponseEntity<ApiResponse> deleteNoteById(NoteDTO noteDTO);
    ResponseEntity<ApiResponse> editNoteById(NoteDTO noteDTO);
    ResponseEntity<ApiResponse> findNoteById(NoteDTO noteDTO);
    ResponseEntity<ApiResponse> findNoteUsingTitle(NoteDTO noteDTO);
    ResponseEntity<ApiResponse> deleteNoteByTitle(NoteDTO noteDto);
}
