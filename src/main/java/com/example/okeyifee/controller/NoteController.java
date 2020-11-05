package com.example.okeyifee.controller;

import com.example.okeyifee.dto.NoteDto;
import com.example.okeyifee.models.Note;
import com.example.okeyifee.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value = "/notes")
public class NoteController{

    private NoteService noteService;


    @Autowired
    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @PostMapping("/create")
    public void createNote(@RequestBody Note note) {
        noteService.save(note);
        System.out.println();
    }
//
//    @PostMapping
//    ResponseEntity<Person> create(@RequestBody @Validated Person person) {

    @GetMapping("/retrieve")
    public List<NoteDto> retrieveNote(@RequestBody NoteDto notedto) {
        List<NoteDto> answer = noteService.findNoteUsingTitle(notedto.getTitle());
        return answer;
    }
}

