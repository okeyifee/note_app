package com.example.okeyifee.service.impl;

import com.example.okeyifee.dto.NoteDto;
import com.example.okeyifee.models.Note;
import com.example.okeyifee.repository.NoteRepository;
import com.example.okeyifee.service.NoteService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Service
public class NoteServiceImpl implements NoteService{

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    NoteRepository noteRepository;
    ModelMapper mapper;

    @Autowired
    public NoteServiceImpl(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }


    @Override
    public List<Note>  findAllByTitle(String title) {
        return noteRepository.findAllByTitle(title);
    }

    @Override
    public List<Note>  findAllByCreatedAt(LocalDateTime date) {
        return noteRepository.findAllByCreatedAt(date);
    }

    @Override
    public List<Note>  findAllByContentContaining(String matcher) {
        return noteRepository.findAllByContentContaining(matcher);
    }

    @Override
    public List<Note> findAllByTitleContains(String matcher) {
        return noteRepository.findAllByTitleContains(matcher);
    }

    @Override
    public Note findNoteByCreatedAt(LocalDateTime date) {
        return noteRepository.findNoteByCreatedAt(date);
    }

    @Override
    public Note findNoteByTitleContains(String matcher) {
        return noteRepository.findNoteByTitleContains(matcher);
    }



    @Override
    public List<NoteDto> findNoteUsingTitle(String title) {
        List<Note> answer = noteRepository.findNoteByTitle(title);
//        NoteDto retrievedNote = mapper.map(answer, NoteDto.class);
        List<NoteDto> retrievedNote = new ArrayList<>();

        answer.forEach((Note a) -> {
            NoteDto n = new NoteDto();
            n.setTitle(a.getTitle());
            n.setCreatedAt(a.getCreatedAt().format(formatter));
            n.setContent(a.getContent());
            retrievedNote.add(n);
    });
        return retrievedNote;
    }

    @Override
    public boolean save(Note note) {
        note.setDeleted(false);
        noteRepository.save(note);
        System.out.println(note.getTitle());
        System.out.println(note.getContent());
        return true;
    }

    @Override
    public boolean deleteNoteById(Long id) {
        Note temp = noteRepository.findNoteById(id);
        boolean isDeleted;
        if (temp != null){
            temp.setDeleted(true);
            noteRepository.save(temp);
            isDeleted = true;
        } else {
            isDeleted = false;
        }
        return isDeleted;
    }

    @Override
    public void editNoteById(Long id) {
        Note temp = noteRepository.findNoteById(id);

    }

    @Override
    public Note findNoteById(Long id) {
        return null;
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

}
