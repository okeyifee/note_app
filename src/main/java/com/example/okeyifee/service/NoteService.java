package com.example.okeyifee.service;

import com.example.okeyifee.dto.NoteDTO;
import com.example.okeyifee.payload.ApiResponse;
import org.springframework.http.ResponseEntity;

public interface NoteService{

    ResponseEntity<ApiResponse> findAllNotes();
    ResponseEntity<ApiResponse> serial(NoteDTO noteDTO);
    ResponseEntity<ApiResponse> findAllByCreatedAt(NoteDTO noteDTO);
    ResponseEntity<ApiResponse> findAllByContentContaining(NoteDTO noteDTO);
    ResponseEntity<ApiResponse> findAllByTitleContains(NoteDTO noteDTO);
    ResponseEntity<ApiResponse> save(NoteDTO noteDTO);
    ResponseEntity<ApiResponse> deleteNoteById(Long id);
    ResponseEntity<ApiResponse> editNoteById(Long id, NoteDTO noteDTO);
    ResponseEntity<ApiResponse> findNoteById(Long id);
    ResponseEntity<ApiResponse> findNoteUsingTitle(NoteDTO noteDTO);
    ResponseEntity<ApiResponse> deleteNoteByTitle(NoteDTO noteDto);
}
